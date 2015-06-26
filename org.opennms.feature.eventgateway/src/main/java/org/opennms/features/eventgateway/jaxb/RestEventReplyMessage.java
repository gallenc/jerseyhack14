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

@XmlRootElement(name="restEventReply")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="RestEventReplyMessage")
public class RestEventReplyMessage {
	
	// list of reply parameters to include in reply message
    private List<NameValuePair> replyValues = new ArrayList<NameValuePair>();

	@XmlElementWrapper(name="replyValues")
	@XmlElement(name="valuePair")
	public void setReplyValues(List<NameValuePair> replyValues) {
		this.replyValues = replyValues;
	}
	
	public List<org.opennms.features.eventgateway.jaxb.NameValuePair> getReplyValues() {
		return replyValues;
	}

}