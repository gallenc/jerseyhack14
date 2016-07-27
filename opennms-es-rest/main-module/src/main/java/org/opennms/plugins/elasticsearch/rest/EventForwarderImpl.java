package org.opennms.plugins.elasticsearch.rest;


import org.opennms.netmgt.events.api.EventForwarder;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventForwarderImpl implements EventForwarder {
	private static final Logger LOG = LoggerFactory.getLogger(EventForwarderImpl.class);

	@Override
	public void sendNow(Event event) {
		LOG.debug("Event to send received: " + event.toString());

	}

	@Override
	public void sendNow(Log arg0) {
		// TODO Auto-generated method stub

	}

}
