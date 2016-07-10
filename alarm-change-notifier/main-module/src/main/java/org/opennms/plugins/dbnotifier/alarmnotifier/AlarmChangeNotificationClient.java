package org.opennms.plugins.dbnotifier.alarmnotifier;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.opennms.netmgt.events.api.EventProxy;
import org.opennms.netmgt.events.api.EventProxyException;
import org.opennms.netmgt.model.events.EventBuilder;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.core.network.IPAddress;
import org.opennms.plugins.dbnotifier.DbNotification;
import org.opennms.plugins.dbnotifier.NotificationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sends alarm change events into opennms
 * 
 * @author admin
 *
 */
public class AlarmChangeNotificationClient implements NotificationClient {
	private static final Logger LOG = LoggerFactory
			.getLogger(AlarmChangeNotificationClient.class);

	EventProxy eventProxy = null;

	public EventProxy getEventProxy() {
		return eventProxy;
	}

	public void setEventProxy(EventProxy eventProxy) {
		this.eventProxy = eventProxy;
	}

	@Override
	public void sendDbNotification(DbNotification dbNotification) {
		try{
			String payload = dbNotification.getPayload();

			JSONObject newJsonObject=null;
			JSONObject oldJsonObject=null;

			try {
				JSONParser parser = new JSONParser();
				Object obj;
				obj = parser.parse(payload);
				JSONArray jsonArray = (JSONArray) obj;
				LOG.debug("payload jsonArray.toString():" + jsonArray.toString());
				newJsonObject = (JSONObject) jsonArray.get(0);
				oldJsonObject = (JSONObject) jsonArray.get(1);

			} catch (ParseException e1) {
				throw new RuntimeException("cannot parse notification payload to json object. payload="+ payload, e1);
			}

			if ( newJsonObject.isEmpty() && (! oldJsonObject.isEmpty()) ){
				// received an alarm delete
				if (LOG.isDebugEnabled()) LOG.debug("alarm deleted alarmid="+oldJsonObject.get("alarmid"));
				EventBuilder eb= jsonAlarmToEventBuilder(oldJsonObject, 
						new EventBuilder(
								"uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmDeleted",
								"AlarmChangeNotifier"));

				//copy in all values as json in params
				eb.addParam("oldalarmvalues",oldJsonObject.toString());
				eb.addParam("newalarmvalues",newJsonObject.toString());

				sendEvent(eb.getEvent());

			} else if ( (! newJsonObject.isEmpty()) && oldJsonObject.isEmpty()){
				// received an alarm create
				if (LOG.isDebugEnabled()) LOG.debug("alarm created alarmid="+newJsonObject.get("alarmid"));
				EventBuilder eb= jsonAlarmToEventBuilder(newJsonObject, 
						new EventBuilder(
								"uei.opennms.org/plugin/AlarmChangeNotificationEvent/NewAlarmCreated",
								"AlarmChangeNotifier"));

				//copy in all values as json in params
				eb.addParam("oldalarmvalues",oldJsonObject.toString());
				eb.addParam("newalarmvalues",newJsonObject.toString());

				sendEvent(eb.getEvent());
			} else {
				// received an alarm change notification
				// alarm has changed check for changes and send appropriate notifications
				
				//TODO possibly ignore alarm type 2 alarm changes - only alarm type 1 alarms are real alarms

				// ignore event count changes if only change in alarm
				// TODO need database trigger to also ignore these changes
				JSONObject newobj = new JSONObject(newJsonObject);
				JSONObject oldobj = new JSONObject(oldJsonObject);
				newobj.remove("lasteventtime");
				oldobj.remove("lasteventtime");
				newobj.remove("lasteventid");
				oldobj.remove("lasteventid");
				newobj.remove("counter");
				oldobj.remove("counter");
				
				if (! newobj.toString().equals(oldobj.toString())){
					// changes other than event count

					// severity changed notification
					String oldseverity= (oldJsonObject.get("severity")==null) ? null : oldJsonObject.get("severity").toString();
					String newseverity= (newJsonObject.get("severity")==null) ? null : newJsonObject.get("severity").toString();
					if (newseverity !=null && ! newseverity.equals(oldseverity)){
						if (LOG.isDebugEnabled()) LOG.debug("alarm severity changed alarmid="+oldJsonObject.get("alarmid")
								+" old severity="+oldseverity+" new severity="+newseverity);
						EventBuilder eb= jsonAlarmToEventBuilder(newJsonObject, 
								new EventBuilder(
										"uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmSeverityChanged",
										"AlarmChangeNotifier"));
						eb.addParam("oldseverity",oldseverity);

						//copy in all values as json in params
						eb.addParam("oldalarmvalues",oldJsonObject.toString());
						eb.addParam("newalarmvalues",newJsonObject.toString());

						sendEvent(eb.getEvent());
					}

					// alarm acknowledged / unacknowledged notifications  
					String oldalarmacktime= (oldJsonObject.get("alarmacktime")==null) ? null : oldJsonObject.get("alarmacktime").toString();
					String newalarmacktime= (newJsonObject.get("alarmacktime")==null) ? null : newJsonObject.get("alarmacktime").toString();
					if(oldalarmacktime==null && newalarmacktime !=null) {
						//alarm acknowledged notification
						if (LOG.isDebugEnabled()) LOG.debug("alarm acknowleged alarmid="+newJsonObject.get("alarmid"));

						EventBuilder eb= jsonAlarmToEventBuilder(newJsonObject, 
								new EventBuilder(
										"uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmAcknowledged",
										"AlarmChangeNotifier"));

						//copy in all values as json in params
						eb.addParam("oldalarmvalues",oldJsonObject.toString());
						eb.addParam("newalarmvalues",newJsonObject.toString());

						sendEvent(eb.getEvent());

					} else {
						if(oldalarmacktime!=null && newalarmacktime ==null) {

							//alarm unacknowledged notification
							if (LOG.isDebugEnabled()) LOG.debug("alarm unacknowleged alarmid="+newJsonObject.get("alarmid"));
							//TODO issue unacknowledged doesn't have a user because only user and time in alarm field
							EventBuilder eb= jsonAlarmToEventBuilder(newJsonObject, 
									new EventBuilder(
											"uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmUnAcknowledged",
											"AlarmChangeNotifier"));

							//copy in all values as json in params
							eb.addParam("oldalarmvalues",oldJsonObject.toString());
							eb.addParam("newalarmvalues",newJsonObject.toString());

							sendEvent(eb.getEvent());
						}
					}

					// alarm suppressed / unsuppressed notifications 
					String newsuppresseduntil= (newJsonObject.get("suppresseduntil")==null) ? null : newJsonObject.get("suppresseduntil").toString();
					String oldsuppresseduntil= (oldJsonObject.get("suppresseduntil")==null) ? null : oldJsonObject.get("suppresseduntil").toString();

					if (newsuppresseduntil!=null && ! newsuppresseduntil.equals(oldsuppresseduntil)) {
						//alarm suppressed notification
						if (LOG.isDebugEnabled()) LOG.debug("alarm suppressed alarmid="+newJsonObject.get("alarmid"));

						EventBuilder eb= jsonAlarmToEventBuilder(newJsonObject, 
								new EventBuilder(
										"uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmSuppressed",
										"AlarmChangeNotifier"));

						//copy in all values as json in params
						eb.addParam("oldalarmvalues",oldJsonObject.toString());
						eb.addParam("newalarmvalues",newJsonObject.toString());

						sendEvent(eb.getEvent());

					} else {
						if(oldsuppresseduntil!=null && newsuppresseduntil ==null) {

							//alarm unsuppressed notification
							if (LOG.isDebugEnabled()) LOG.debug("alarm unsuppressed alarmid="+newJsonObject.get("alarmid"));
							//TODO issue unsuppress doesn't have a user because only user and time in alarm field
							EventBuilder eb= jsonAlarmToEventBuilder(newJsonObject, 
									new EventBuilder(
											"uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmUnSuppressed",
											"AlarmChangeNotifier"));

							//copy in all values as json in params
							eb.addParam("oldalarmvalues",oldJsonObject.toString());
							eb.addParam("newalarmvalues",newJsonObject.toString());

							sendEvent(eb.getEvent());
						}
					}

					// TODO alarm sticky note changed

					// send alarm changed event for any other changes not captured above
					newobj.remove("severity");
					oldobj.remove("severity");
					newobj.remove("alarmacktime");
					oldobj.remove("alarmacktime");
					newobj.remove("alarmackuser");
					oldobj.remove("alarmackuser");
					newobj.remove("suppresseduntil");
					oldobj.remove("suppresseduntil");
					newobj.remove("suppresseduser");
					oldobj.remove("suppresseduser");
					if (! newobj.toString().equals(oldobj.toString())) {

						EventBuilder eb= jsonAlarmToEventBuilder(oldJsonObject, 
								new EventBuilder(
										"uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmChanged",
										"AlarmChangeNotifier"));

						//copy in all values as json in params
						eb.addParam("oldalarmvalues",oldJsonObject.toString());
						eb.addParam("newalarmvalues",newJsonObject.toString());

						sendEvent(eb.getEvent());
					}

				}

			}

		} catch (Exception e){
			LOG.error("problem creating opennms alarm change event from database notification", e);
		}

	}

	private EventBuilder jsonAlarmToEventBuilder(JSONObject jsonObject, EventBuilder eb){
		//copy generic alarm details as paramaters
		String alarmId= (jsonObject.get("alarmid")==null) ? null : jsonObject.get("alarmid").toString();
		String severity= (jsonObject.get("severity")==null) ? null : jsonObject.get("severity").toString();
		String logmsg= (jsonObject.get("logmsg")==null) ? null : jsonObject.get("logmsg").toString();
		String eventuei= (jsonObject.get("eventuei")==null) ? null : jsonObject.get("eventuei").toString();
		String reductionkey= (jsonObject.get("reductionkey")==null) ? null : jsonObject.get("reductionkey").toString();
		String clearkey= (jsonObject.get("clearkey")==null) ? null : jsonObject.get("clearkey").toString();
		String alarmtype= (jsonObject.get("alarmtype")==null) ? null : jsonObject.get("alarmtype").toString();	
		String alarmacktime= (jsonObject.get("alarmacktime")==null) ? null : jsonObject.get("alarmacktime").toString();
		String alarmackuser= (jsonObject.get("alarmackuser")==null) ? null : jsonObject.get("alarmackuser").toString();
		String suppressedtime= (jsonObject.get("suppressedtime")==null) ? null : jsonObject.get("suppressedtime").toString();
		String suppresseduntil= (jsonObject.get("suppresseduntil")==null) ? null : jsonObject.get("suppresseduntil").toString();
		String suppresseduser= (jsonObject.get("suppresseduser")==null) ? null : jsonObject.get("suppresseduser").toString();

		// Node / service identity - copy into event corresponding event fields
		String nodeid= (jsonObject.get("nodeid")==null) ? null : jsonObject.get("nodeid").toString();
		String ipaddr= (jsonObject.get("ipaddr")==null) ? null : jsonObject.get("ipaddr").toString();
		String ifindex= (jsonObject.get("ifindex")==null) ? null : jsonObject.get("ifindex").toString();
		String applicationdn= (jsonObject.get("applicationdn")==null) ? null : jsonObject.get("applicationdn").toString();			
		String serviceid= (jsonObject.get("serviceid")==null) ? null : jsonObject.get("serviceid").toString();
		String systemid= (jsonObject.get("systemid")==null) ? null : jsonObject.get("systemid").toString();


		eb.addParam("alarmid", alarmId );
		eb.addParam("severity", severity );
		eb.addParam("logmsg", logmsg );
		eb.addParam("clearkey", clearkey );
		eb.addParam("alarmtype", alarmtype );
		eb.addParam("alarmacktime", alarmacktime );
		eb.addParam("alarmackuser", alarmackuser );
		eb.addParam("suppressedtime", suppressedtime );
		eb.addParam("suppresseduntil", suppresseduntil );
		eb.addParam("suppresseduser", suppresseduser );
		eb.addParam("eventuei", eventuei );
		eb.addParam("reductionkey", reductionkey );

		if(nodeid!=null) eb.setNodeid(Long.parseLong(nodeid));
		if (ipaddr!= null) try {
			IPAddress ipaddress = new IPAddress(ipaddr);
			eb.setInterface(ipaddress.toInetAddress());
		} catch (Exception e){
			LOG.error("cannot parse json object ipaddr="+ipaddr,e);
		}
		if (ifindex!=null) eb.setIfIndex(Integer.parseInt(ifindex));
		eb.addParam("applicationdn",applicationdn);
		eb.addParam("serviceid",serviceid);
		eb.addParam("systemid",systemid);

		return eb;
	}



	private void sendEvent(Event e){
		LOG.debug("sending event to opennms. event.tostring():" + e.toString());
		try {
			if (eventProxy != null) {
				eventProxy.send(e);
			} else {
				LOG.error("OpenNMS event proxy not set - cannot send events to opennms");
			}
		} catch (EventProxyException ex) {
			throw new RuntimeException(
					"event proxy problem sending AlarmChangeNotificationEvent to OpenNMS:",
					ex);
		}
	}


	@Override
	public void init() {
		LOG.debug("initialising AlarmChangeNotificationClient");
		if (eventProxy == null)
			LOG.debug("OpenNMS event proxy not set - cannot send events to opennms");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
