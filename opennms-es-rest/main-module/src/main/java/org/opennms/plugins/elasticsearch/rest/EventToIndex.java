package org.opennms.plugins.elasticsearch.rest;

import io.searchbox.core.Index;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.opennms.plugins.elasticsearch.rest.NodeCache;
import org.opennms.netmgt.model.OnmsSeverity;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Parm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventToIndex {
	private static final Logger LOG = LoggerFactory.getLogger(EventToIndex.class);

	// stem of all alarm change notification uei's
	public static final String ALARM_NOTIFICATION_UEI_STEM = "uei.opennms.org/plugin/AlarmChangeNotificationEvent";

	// uei definitions of alarm change events
	public static final String ALARM_DELETED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmDeleted";
	public static final String ALARM_CREATED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/NewAlarmCreated";
	public static final String ALARM_SEVERITY_CHANGED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmSeverityChanged";
	public static final String ALARM_ACKNOWLEDGED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmAcknowledged";
	public static final String ALARM_UNACKNOWLEDGED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmUnAcknowledged";
	public static final String ALARM_SUPPRESSED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmSuppressed";
	public static final String ALARM_UNSUPPRESSED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmUnSuppressed";
	public static final String ALARM_TROUBLETICKET_STATE_CHANGE_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/TroubleTicketStateChange";
	public static final String ALARM_CHANGED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmChanged";

	private static final String OLD_ALARM_VALUES="oldalarmvalues";
	private static final String NEW_ALARM_VALUES="oldalarmvalues";

	private boolean logEventDescription=false;
	private NodeCache cache;

	public boolean isLogEventDescription() {
		return logEventDescription;
	}

	public void setLogEventDescription(boolean logEventDescription) {
		this.logEventDescription = logEventDescription;
	}

	public NodeCache getCache() {
		return cache;
	}

	public void setCache(NodeCache cache) {
		this.cache = cache;
	}


	/** 
	 * this handles the incoming event and deals with it as an alarm change event or a normal event
	 * @param event
	 */
	public void forwardEvent(Event event){

		maybeRefreshCache(event);

		// handling uei definitions of alarm change events

		String uei=null;

		// if alarm change notification then handle change
		if(uei.startsWith(ALARM_NOTIFICATION_UEI_STEM)) {

			if (ALARM_CREATED_EVENT.equals(uei)){

			} else if( ALARM_DELETED_EVENT.equals(uei)){

			} else if (ALARM_SEVERITY_CHANGED_EVENT.equals(uei)){


			} else if (ALARM_ACKNOWLEDGED_EVENT.equals(uei)){


			} else if (ALARM_UNACKNOWLEDGED_EVENT.equals(uei)){


			} else if (ALARM_SUPPRESSED_EVENT.equals(uei)){


			} else if (ALARM_UNSUPPRESSED_EVENT.equals(uei)){


			} else if (ALARM_TROUBLETICKET_STATE_CHANGE_EVENT.equals(uei)){


			} else if (ALARM_CHANGED_EVENT.equals(uei)){


			}


			Index index = populateAlarmIndexBodyFromAlarmChangeEvent( event, "onms-test-alarm-index", "data");
			LOG.debug("alarm index: "+index.toString());



			// else handle all other event types
		} else {

			// only process events persisted to database
			if(event.getDbid()!=null && event.getDbid()!=0) {
				// Send the event to the event forwarder
				Index index = populateEventIndexBodyFromEvent( event, "onms-test-event-index", "data");
				LOG.debug("event index: "+index.toString());
			}



		}






	}

	/**
	 * utility method to populate a Map with the most import event attributes
	 *
	 * @param body the map
	 * @param event the event object
	 */
	public Index populateEventIndexBodyFromEvent( Event event, String indexName, String indexType) {

		Map<String,String> body=new HashMap<String,String>();

		String id=(event.getDbid()==null ? null: Integer.toString(event.getDbid()));

		body.put("id",id);
		body.put("eventuei",event.getUei());

		Calendar cal=Calendar.getInstance();
		cal.setTime(event.getCreationTime()); // javax.xml.bind.DatatypeConverter.parseDateTime("2010-01-01T12:00:00Z");

		body.put("@timestamp", DatatypeConverter.printDateTime(cal));

		body.put("dow", Integer.toString(cal.get(Calendar.DAY_OF_WEEK)));
		body.put("hour",Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
		body.put("dom", Integer.toString(cal.get(Calendar.DAY_OF_MONTH))); 
		body.put("eventsource", event.getSource());
		body.put("ipaddr", event.getInterfaceAddress()!=null ? event.getInterfaceAddress().toString() : null );
		body.put("servicename", event.getService());
		// params are exported as attributes, see below
		body.put("eventseverity_text", event.getSeverity());
		body.put("eventseverity", Integer.toString(OnmsSeverity.get(event.getSeverity()).getId()));

		if(isLogEventDescription()) {
			body.put("eventdescr", event.getDescr());
		}
		body.put("nodeid", Long.toString(event.getNodeid()));
		body.put("host",event.getHost());
		for(Parm parm : event.getParmCollection()) {
			body.put("p_" + parm.getParmName(), parm.getValue().getContent());
		}
		body.put("interface", event.getInterface());
		body.put("logmsg", ( event.getLogmsg()!=null ? event.getLogmsg().getContent() : null ));
		body.put("logmsgdest", ( event.getLogmsg()!=null ? event.getLogmsg().getDest() : null ));


		Index index = new Index.Builder(body).index(indexName)
				.type(indexType).id(id).build();

		return index;
	}

	/**
	 * An alarm change event will have a payload corresponding to the json representation of the
	 * Alarms table row for this alarm id. Both "oldalarmvalues" and "newalarmvalues" params may be populated
	 * The alarm index body will be populated with the "newalarmvalues" but if "newalarmvalues" is null then the
	 * "oldalarmvalues" json string will be used
	 * @param body
	 * @param event
	 */
	public Index populateAlarmIndexBodyFromAlarmChangeEvent(Event event, String indexName, String indexType) {

		Map<String,String> body = new HashMap();

		//get alarm change params from event
		Map<String,String> parmsMap = new HashMap();
		for(Parm parm : event.getParmCollection()) {
			parmsMap.put( parm.getParmName(), parm.getValue().getContent());
		}

		String oldValues=parmsMap.get(OLD_ALARM_VALUES);
		String newValues=parmsMap.get(NEW_ALARM_VALUES);

		String payload=null;
		JSONObject alarmValues=new JSONObject() ;
		if (newValues!=null || oldValues!=null){
			try {
				JSONParser parser = new JSONParser();
				Object obj;

				if (newValues!=null) {
					payload = newValues;
				} else {
					payload = oldValues;
				}
				obj = parser.parse(payload);
				alarmValues = (JSONObject) obj;
				LOG.debug("payload alarmvalues.toString():" + alarmValues.toString());

			} catch (ParseException e1) {
				throw new RuntimeException("cannot parse notification payload to json object. payload="+ payload, e1);
			}

		}

		for (Object x: alarmValues.keySet()){
			String key=(String) x;
			String value = (alarmValues.get(key)==null) ? null : alarmValues.get(key).toString();
			body.put(key, value);
		}

		String id = (alarmValues.get("alarmid")==null) ? "" : alarmValues.get("alarmid").toString();

		Index index = new Index.Builder(body).index(indexName)
				.type(indexType).id(id).build();

		return index;
	}

	private void maybeRefreshCache(Event event) {
		String uei=event.getUei();
		if(uei!=null && uei.startsWith("uei.opennms.org/nodes/")) {
			if (
					uei.endsWith("Added")
					|| uei.endsWith("Deleted")
					|| uei.endsWith("Updated")
					|| uei.endsWith("Changed")
					) {
				cache.refreshEntry(event.getNodeid());
			}
		}
	}


}
