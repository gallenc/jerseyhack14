package org.opennms.plugins.dbnotifier.alarmnotifier;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.opennms.netmgt.events.api.EventProxy;
import org.opennms.netmgt.events.api.EventProxyException;
import org.opennms.netmgt.model.events.EventBuilder;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.plugins.dbnotifier.DatabaseChangeNotifier;
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

			EventBuilder eb=null;

			if ( newJsonObject.isEmpty() && (! oldJsonObject.isEmpty()) ){
				// received an alarm delete
				eb= new EventBuilder(
						"uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmDeleted",
						"AlarmChangeNotifier");

				String alarmId= (oldJsonObject.get("alarmid")==null) ? null : oldJsonObject.get("alarmid").toString();
				eb.addParam("alarmid", alarmId );
				eb.addParam("oldalarmvalues",oldJsonObject.toString());
				eb.addParam("newalarmvalues",newJsonObject.toString());
				sendEvent(eb.getEvent());

			} else if ( (! newJsonObject.isEmpty()) && oldJsonObject.isEmpty()){
				// received an alarm create
				eb= new EventBuilder(
						"uei.opennms.org/plugin/AlarmChangeNotificationEvent/NewAlarmCreated",
						"AlarmChangeNotifier");
				String alarmId= (newJsonObject.get("alarmid")==null) ? null : newJsonObject.get("alarmid").toString();
				eb.addParam("alarmid", alarmId );
				eb.addParam("oldalarmvalues",oldJsonObject.toString());
				eb.addParam("newalarmvalues",newJsonObject.toString());
				sendEvent(eb.getEvent());

			} else {
				String alarmId= (newJsonObject.get("alarmid")==null) ? null : newJsonObject.get("alarmid").toString();
				
				// check for alarm ack
				
				
				
				
				
				// received an alarm change notification
				// to do check for ack notifications and other alarm types
				// to do ignore simple alarm count changes - also in db trigger
				eb= new EventBuilder(
						"uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmChanged",
						"AlarmChangeNotifier");
				
				eb.addParam("alarmid", alarmId );
				eb.addParam("oldalarmvalues",oldJsonObject.toString());
				eb.addParam("newalarmvalues",newJsonObject.toString());
				sendEvent(eb.getEvent());
			}
			
		} catch (Exception e){
			LOG.error("problem creating opennms alarm change event from database notification", e);
		}

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
