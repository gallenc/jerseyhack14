package org.opennms.features.eventgateway.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.opennms.features.eventgateway.jaxb.NameValuePair;
import org.opennms.netmgt.xml.event.Event;

@XmlRootElement(name="restEventMessage")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="RestEventMessage")
public class RestEventMessage {
	
	private List<Event> eventList= new ArrayList<Event>();

	public List<Event> getEventList() {
		return eventList;
	}
	
	@XmlElementWrapper(name="eventList")
	@XmlElement(name="event")
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	
	// list of other message parameters to include in event message
    private List<NameValuePair> sentValues = new ArrayList<NameValuePair>();

	@XmlElementWrapper(name="sentValues")
	@XmlElement(name="valuePair")
	public void setReplyValues(List<NameValuePair> sentValues) {
		this.sentValues = sentValues;
	}
	
	public List<org.opennms.features.eventgateway.jaxb.NameValuePair> getReplyValues() {
		return sentValues;
	}

}
