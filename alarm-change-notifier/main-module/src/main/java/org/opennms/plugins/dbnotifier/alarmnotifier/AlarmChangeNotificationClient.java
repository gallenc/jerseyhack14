package org.opennms.plugins.dbnotifier.alarmnotifier;

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
 * @author admin
 *
 */
public class AlarmChangeNotificationClient implements NotificationClient {
	private static final Logger LOG = LoggerFactory.getLogger(AlarmChangeNotificationClient.class);


	EventProxy eventProxy=null;


	public EventProxy getEventProxy() {
		return eventProxy;
	}

	public void setEventProxy(EventProxy eventProxy) {
		this.eventProxy = eventProxy;
	}

	@Override
	public void sendDbNotification(DbNotification dbNotification) {
		// TODO Auto-generated method stub

		EventBuilder eb = new EventBuilder("uei.opennms.org/plugin/AlarmChangeNotificationEvent/Notification", "AlarmChangeNotifier");

		eb.addParam("changedalarm", dbNotification.getPayload());
		
		Event e = eb.getEvent();
		LOG.debug("sending event to opennms. event.tostring():"+e.toString());

		try {
			if ( eventProxy!=null){
				eventProxy.send(eb.getEvent());
			} else {
				LOG.error("OpenNMS event proxy not set - cannot send events to opennms");
			}
		} catch (EventProxyException ex) {
			throw new RuntimeException("event proxy problem sending AlarmChangeNotificationEvent to OpenNMS:", ex);
		}



	}

	@Override
	public void init() {
		LOG.debug("initialising AlarmChangeNotificationClient");
		if (eventProxy==null) LOG.debug ("OpenNMS event proxy not set - cannot send events to opennms");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}



}
