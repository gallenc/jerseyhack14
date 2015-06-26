
package org.opennms.features.eventgateway.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.opennms.features.eventgateway.AutoAuthKeyGenerator;
import org.opennms.features.eventgateway.ConfigLoader;
import org.opennms.features.eventgateway.ConfigLoader.EventGatewayConfigObject;
import org.opennms.features.eventgateway.jaxb.EventGatewayConfig;
import org.opennms.features.eventgateway.jaxb.EventGatewayErrorMessage;
import org.opennms.features.eventgateway.jaxb.NameValuePair;
import org.opennms.features.eventgateway.jaxb.RestEventMessage;
import org.opennms.features.eventgateway.jaxb.RestEventReplyMessage;
import org.opennms.netmgt.model.events.EventBuilder;

import org.opennms.netmgt.events.api.EventProxy;
import org.opennms.netmgt.events.api.EventProxyException;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Parm;

import com.sun.jersey.core.util.Base64;

@Path("/gw")
public class EventGatewayRest {

	//OpenNMS event proxy
	EventProxy eventProxy=null;

	public EventGatewayRest(){
		super();

		// get OpenNMS event proxy
		eventProxy=ConfigLoader.getEventProxy();
		if (eventProxy == null){
			throw new RuntimeException("ConfigLoader.getEventProxy() cannot be null.");
		}

	}


	@POST
	@Path("/{gatewayReference}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response getMessage(@PathParam("gatewayReference") String gatewayReference, @Context HttpServletRequest request, RestEventMessage restEventMessage)
	{
		// configuration reloaded on each event as may change between requests

		HashMap<String,EventGatewayConfig> configMap=null;

		// get event gateway configuration
		EventGatewayConfigObject gatewayConfigObject = ConfigLoader.getGatewayConfigObject();

		if (gatewayConfigObject==null){
			throw new RuntimeException("ConfigLoader.getGatewayConfigObject() cannot be null. Check event gateway configuration file.");
		}

		configMap =  gatewayConfigObject.getConfigMap();

		if (configMap==null){
			throw new RuntimeException("ConfigLoader.getConfigMap cannot be null. Check event gateway configuration file");
		}

		HashMap<String, AutoAuthKeyGenerator> authenticatorMap = gatewayConfigObject.getAuthenticatorMap();

		if (authenticatorMap==null){
			throw new RuntimeException("ConfigLoader.getAuthenticatorMap cannot be null. Check event gateway configuration file");
		}

		/* ********************
		 * Authenticate Message
		 * ********************
		 */

		//Get basic authentication information
		String authHeader = request.getHeader("Authorization");
		if (authHeader==null) {
			//return status 401 Unauthorized
			return Response.status(401).entity(new EventGatewayErrorMessage(401, 0, "Request must contain basic authentication header with encoded username:password 'Authorization: Basic <Base64encodedString>'", "http://en.wikipedia.org/wiki/Basic_access_authentication", null)).build();
		}

		//Get encoded username and password 
		final String encodedUserPassword = authHeader.replaceFirst("Basic" + " ", "");

		//Decode username and password
		String usernameAndPassword;
		usernameAndPassword = new String(Base64.decode(encodedUserPassword));

		//Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		if (tokenizer.countTokens()!=2){
			//return status 401 Unauthorized
			return Response.status(401).entity(new EventGatewayErrorMessage(401, 0, "Request must contain basic authentication header with encoded username:password 'Authorization: Basic <Base64encodedString>'", "http://en.wikipedia.org/wiki/Basic_access_authentication", null)).build();
		}
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		// get configuration based on gateway reference
		EventGatewayConfig config= configMap.get(gatewayReference);
		if (config==null){
			//return status 404 Not Found
			return Response.status(404).entity(new EventGatewayErrorMessage(404, 0, "gateway reference '"+gatewayReference+"' does not exist.", null, null)).build();
		}

		if (config.getUeiList()==null){
			throw new RuntimeException("config.getUeiList() for reference:"+gatewayReference+" cannot be null");
		}


		if (config.getUsername()==null ){
			throw new RuntimeException("config.getUsername() for reference:"+gatewayReference+" cannot be null");
		}

		if (!config.getUsername().equals(username) ){

			EventBuilder eb = new EventBuilder("uei.opennms.org/internal/eventGateway/MessageFromUnauthorizedUser", "EventGateway:"+gatewayReference);
			eb.addParam("gatewayUsername",username);
			eb.addParam("gatewayReference", gatewayReference);
			// add message host and referrer fields
			String hostHeader = request.getHeader("Host");
			eb.addParam("receivedHttpHostHeader", (hostHeader==null) ? "null" : hostHeader);

			String refererHeader=request.getHeader("Referer");
			eb.addParam("receivedHttpRefererHeader", (refererHeader==null) ? "null" : refererHeader);
			try {
				eventProxy.send(eb.getEvent());
			} catch (EventProxyException e) {
				throw new RuntimeException("event proxy problem sending AdditionalValuesReceived event to OpenNMS:", e);
			}

			//return status 401 Unauthorized
			return Response.status(401).entity(new EventGatewayErrorMessage(401, 0, "basic authentication username '"+username+"' is not authorised for gateway reference:"+gatewayReference, null, null)).build();
		}

		// TODO note password should be determined by OpenNMS but not working at present. 
		// this password should be looked at for users who are not configured in OpenNMS
		if (config.getPassword()==null ){
			throw new RuntimeException("config.getPassword() for reference:"+gatewayReference+" cannot be null");
		}

		// test password against autoAuthKeyGenerator and simple password configuration
		
		boolean authenticated=false;

		// test simple password in configuration first
		// if password set to NO_FIXED_PASSWORD_AUTHENTICATION then no simple 
		// authentication possible
		if (! "NO_FIXED_PASSWORD_AUTHENTICATION".equals(config.getPassword())){
			authenticated = config.getPassword().equals(password);
		}
		
		// if not authenticated then try testing password using authenticator
		if (!authenticated) {
			AutoAuthKeyGenerator autoAuthKeyGenerator = authenticatorMap.get(gatewayReference);
			if (autoAuthKeyGenerator !=null ) {
				authenticated =autoAuthKeyGenerator.authenticateAuthKey(password);
			}
		}

		if (! authenticated ){
			EventBuilder eb = new EventBuilder("uei.opennms.org/internal/eventGateway/UnauthorizedUserPassword", "EventGateway:"+gatewayReference);
			eb.addParam("gatewayUsername",username);
			eb.addParam("gatewayReference", gatewayReference);
			// add message host and referrer fields
			String hostHeader = request.getHeader("Host");
			eb.addParam("receivedHttpHostHeader", (hostHeader==null) ? "null" : hostHeader);

			String refererHeader=request.getHeader("Referer");
			eb.addParam("receivedHttpRefererHeader", (refererHeader==null) ? "null" : refererHeader);
			try {
				eventProxy.send(eb.getEvent());
			} catch (EventProxyException e) {
				throw new RuntimeException("event proxy problem sending AdditionalValuesReceived event to OpenNMS:", e);
			}
			//return status 401 Unauthorized
			return Response.status(401).entity(new EventGatewayErrorMessage(401, 0, "basic authentication password for username '"+username+"' is not correct for gateway reference:"+gatewayReference, null, null)).build();
		}








		/* ******************************************************
		 * process received additional values all the additional 
		 * values received in the message are added as params to
		 * an AdditionalValuesReceived message
		 * ******************************************************
		 */
		List<NameValuePair> replyValues = restEventMessage.getReplyValues();
		if (replyValues!=null && ! replyValues.isEmpty()) {
			EventBuilder eb = new EventBuilder("uei.opennms.org/internal/eventGateway/AdditionalValuesReceived", "EventGateway:"+gatewayReference);
			eb.addParam("gatewayReference", gatewayReference);
			for (NameValuePair nameValuePair:replyValues){
				if(nameValuePair.getName()!=null && ! "".equals(nameValuePair.getName())){
					eb.addParam(nameValuePair.getName(), 
							(nameValuePair.getValue()==null) ? "null" : nameValuePair.getValue());
				}
			}
			// add message host and referrer fields
			String hostHeader = request.getHeader("Host");
			eb.addParam("receivedHttpHostHeader", (hostHeader==null) ? "null" : hostHeader);

			String refererHeader=request.getHeader("Referer");
			eb.addParam("receivedHttpRefererHeader", (refererHeader==null) ? "null" : refererHeader);
			try {
				eventProxy.send(eb.getEvent());
			} catch (EventProxyException e) {
				throw new RuntimeException("event proxy problem sending AdditionalValuesReceived event to OpenNMS:", e);
			}
		}


		/* ********************
		 * Process received events
		 * ********************
		 */

		HashSet<String> failedUeisList= new HashSet<String>(); 
		StringBuilder failedUeis= new StringBuilder();
		int eventsrejected=0;
		int eventsaccepted=0;

		List<Event> receivedEventList = restEventMessage.getEventList();
		if (receivedEventList==null){
			throw new RuntimeException("receivedEventList cannot be null.");
		}

		for (Event event: receivedEventList){
			// only process events which are accepted by this gateway
			if(event.getUei()!=null && config.getUeiList().contains(event.getUei())){
				try {
					// only copy the fields which send-event.pl would allow
					// copy xml attribute fields into event builder
					// <UEI> the universal event identifier (URI)
					//@XmlElement(name = "uei")
					//event.getUei();
					EventBuilder eb = new EventBuilder(event.getUei(), "EventGateway:"+gatewayReference);
					//--service, -s     service name 
					//@XmlElement(name = "service")
					if (event.getService()!=null) eb.setService(event.getService());
					//--nodeid, -n      node identifier (numeric)
					//@XmlElement(name = "nodeid")
					if (event.getNodeid()!=null) eb.setNodeid(event.getNodeid());
					//--interface, -i   IP address of the interface
					//@XmlElement(name = "interface")
					//@XmlJavaTypeAdapter(InetAddressXmlAdapter.class)
					if (event.getInterfaceAddress()!=null) eb.setInterface(event.getInterfaceAddress());
					//--ifindex, -f     IfIndex of the interface
					//@XmlElement(name = "ifIndex")
					if (event.getIfIndex()!=null) eb.setIfIndex(event.getIfIndex());
					//--descr, -d       a description for the event browser
					//@XmlElement(name = "descr")
					if(event.getDescr()!=null) eb.setDescription(event.getDescr());
					//--logmsg, -l      a logmsg for the event browser (secure field by default)
					//@XmlElement(name = "logmsg")
					if(event.getLogmsg()!=null) eb.setLogMessage(event.getLogmsg().getContent());
					//--severity, -x    the severity of the event (numeric or name)
					//1 = Indeterminate
					//2 = Cleared (unimplemented at this time)
					//3 = Normal
					//4 = Warning
					//5 = Minor
					//6 = Major
					//7 = Critical
					//@XmlElement(name = "severity")
					if(event.getSeverity()!=null) eb.setSeverity(event.getSeverity());
					//x--uuid, -U         a UUID to pass with the event
					//@XmlAttribute(name = "uuid")
					if(event.getUuid()!=null)  eb.setUuid(event.getUuid());

					//@XmlElement(name = "creation-time")
					//TODO org.opennms.netmgt.EventConstants not available in osgi class path
					//so creation time is set to time received
					//if(event.getCreationTime()!=null) {
					// eb.setCreationTime(EventConstants.parseToDate(event.getCreationTime()));
					//} else {
					//	Date date =new Date();
					//	eb.setCreationTime(date);
					//}
					Date date =new Date();
					eb.setCreationTime(date);

					// --parm, -p         an event parameter (ie:
					// --parm 'url http://www.google.com/')
					if(event.getParmCollection()!=null) {
						for (Parm param:event.getParmCollection()){
							eb.addParam(param);
						}
					}

					/* UNUSED EVENT FIELDS
					//@XmlElement(name = "dbid")
					//event.getDbid();
					//@XmlElement(name = "dist-poller")
					event.getDistPoller();
					//@XmlElement(name = "master-station")
					event.getMasterStation();
					//@XmlElement(name = "mask")
					event.getMask();
					//@XmlElement(name = "source")
					event.getSource();
					//@XmlElement(name = "time")
					event.getTime();
					//@XmlElement(name = "host")
					event.getHost();
                    //@XmlElement(name = "snmphost")
					event.getSnmphost();
                    //@XmlElement(name = "snmp")
					event.getSnmp();
                    //@XmlElementWrapper(name="parms")
					//@XmlElement(name="parm")
					event.getParmCollection();
                    //@XmlElement(name = "pathoutage")
					event.getPathoutage();
                    //@XmlElement(name = "correlation")
					event.getCorrelation();
                    //@XmlElement(name = "operinstruct")
					event.getOperinstruct();
                    //@XmlElement(name = "autoaction")
					event.getAutoaction();
					//@XmlElement(name = "operaction")
					event.getOperaction();
                    //@XmlElement(name = "autoacknowledge")
					event.getAutoacknowledge();
					////@XmlElement(name = "loggroup")
					//private List<String> _loggroupList;
					//@XmlElement(name = "tticket")
					event.getTticket();
					//@XmlElement(name = "forward")
					//private List<Forward> _forwardList;
					//@XmlElement(name = "script")
					//event.getsscriptList;
					//@XmlElement(name = "ifAlias")
					event.getIfAlias();
					//@XmlElement(name = "mouseovertext")
					event.getMouseovertext();
					//@XmlElement(name = "alarm-data")
					event.getAlarmData();
					 */

					// add message host and referrer fields to each generated event
					String hostHeader = request.getHeader("Host");
					eb.addParam("receivedHttpHostHeader", (hostHeader==null) ? "null" : hostHeader);

					String refererHeader=request.getHeader("Referer");
					eb.addParam("receivedHttpRefererHeader", (refererHeader==null) ? "null" : refererHeader);

					eventProxy.send(eb.getEvent());

					eventsaccepted++;
				} catch (EventProxyException e) {
					throw new RuntimeException("event proxy problem sending event to OpenNMS:", e);
				}
			} else {
				//create OpenNMS event gateway rejected event for an event received by the gateway 
				//which this gateway reference is not configured to process
				EventBuilder eb = new EventBuilder("uei.opennms.org/internal/eventGateway/RejectedEventUEI", "EventGateway:"+gatewayReference);
				eb.addParam("gatewayReference", gatewayReference);

				String receivedUEI=(event.getUei()==null) ? "null" : event.getUei();
				eb.addParam("receivedUEI", receivedUEI );

				String hostHeader = request.getHeader("Host");
				eb.addParam("receivedHttpHostHeader", (hostHeader==null) ? "null" : hostHeader);

				String refererHeader=request.getHeader("Referer");
				eb.addParam("receivedHttpRefererHeader", (refererHeader==null) ? "null" : refererHeader);

				eventsrejected++;
				failedUeisList.add(receivedUEI);

				try {
					eventProxy.send(eb.getEvent());
				} catch (EventProxyException e) {
					throw new RuntimeException("event proxy problem sending event to OpenNMS:", e);
				}
			}
		}

		//build reply
		RestEventReplyMessage restEventReplyMessage = new RestEventReplyMessage();

		//add diagnostic / error information
		//reply with number of events accepted from message
		NameValuePair nvpEventsAccepted=new NameValuePair();
		nvpEventsAccepted.setName("Success-eventsAccepted");
		nvpEventsAccepted.setValue(Integer.toString(eventsaccepted));
		restEventReplyMessage.getReplyValues().add(nvpEventsAccepted);

		//reply with error values for event ueis in message rejected by gateway
		if(eventsrejected!=0){
			NameValuePair nvpNumEventsRejected=new NameValuePair();
			nvpNumEventsRejected.setName("Error-eventsRejected");
			nvpNumEventsRejected.setValue(Integer.toString(eventsrejected));
			restEventReplyMessage.getReplyValues().add(nvpNumEventsRejected);

			NameValuePair nvpFailedUeiList=new NameValuePair();
			nvpFailedUeiList.setName("Error-Failed-UEIs");
			Iterator<String> failedUeiIterator = failedUeisList.iterator();
			while(failedUeiIterator.hasNext()){
				failedUeis.append(failedUeiIterator.next());
				if (failedUeiIterator.hasNext()) failedUeis.append(",");
			}
			nvpFailedUeiList.setValue(failedUeis.toString());
			restEventReplyMessage.getReplyValues().add(nvpFailedUeiList);
		}

		// reply with static reply parameters defined in configuration	
		restEventReplyMessage.getReplyValues().addAll(config.getStaticReplyValues());

		// calculate hash code for static values and append to message
		// if the hashcode is not changed then the static values are unchanged
		StringBuilder strbld= new StringBuilder();
		for (NameValuePair staticReplyValue: config.getStaticReplyValues()){
			strbld.append( (staticReplyValue.getName()==null) ? "null" : staticReplyValue.getName());
			strbld.append( (staticReplyValue.getValue()==null) ? "null" : staticReplyValue.getValue());
		}
		int stringhashcode = strbld.toString().hashCode();
		NameValuePair hashNvp=new NameValuePair();
		hashNvp.setName("staticValuesHash");
		hashNvp.setValue(Integer.toHexString(stringhashcode));
		restEventReplyMessage.getReplyValues().add(hashNvp);

		return Response.status(200).entity(restEventReplyMessage).build();
	}

}
