package org.opennms.features.eventgateway.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="eventGatewayConfigurations")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="EventGatewayConfigurations")
public class EventGatewayConfigurations {


	// List of OpenNMS gateway instances to instantiate
	private List<EventGatewayConfig> eventGatewayConfigsList =new ArrayList<EventGatewayConfig>();
	

	/**
	 * 
	 * @return List of OpenNMS gateway configuration instances to instantiate
	 */
	public List<EventGatewayConfig> getEventGatewayConfigsList() {
		return eventGatewayConfigsList;
	}

	

	/**
	 * 
	 * @param eventGatewayConfigsList List of OpenNMS gateway configuration instances to instantiate
	 */
	@XmlElementWrapper(name="eventGatewayConfigsList", required=true)
	@XmlElement(name="eventGatewayConfig")
	public void setEventGatewayConfigsList(List<EventGatewayConfig> eventGatewayConfigsList) {
		this.eventGatewayConfigsList = eventGatewayConfigsList;
	}

}
