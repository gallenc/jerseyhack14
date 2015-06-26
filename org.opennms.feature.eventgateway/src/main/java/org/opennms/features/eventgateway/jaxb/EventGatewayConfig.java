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

@XmlRootElement(name="eventGatewayConfig")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="EventGatewayConfig")
public class EventGatewayConfig {

	// reference of this event gateway instance
	private String reference=null;

	// username for this event gateway
	// password is determined by opennms
	private String username=null;

	// password for this event gateway
	// note that password is for gateway not the opennms user password
	private String password=null;
	
	// alternative password method for generating automatic passwords
	private EventGatewayAuthKeyConfig authKeyConfig=null;

	// List of OpenNMS event ueis which can pass this gateway
	private List<String> ueiList =new ArrayList<String>();

	// static list of name value pairs which can be returned in response to this reference
	private List<NameValuePair> staticReplyValues =new ArrayList<NameValuePair>();

	/**
	 * 
	 * @return reference of this event list
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference reference of this event list
	 */
	@XmlAttribute(required=true)
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return username OpenNMS user who can send events to this event list
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username OpenNMS user who can send events to this event list
	 */
	@XmlAttribute(required=true)
	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	/**
	 * @param password password of OpenNMS user who can send events to this event list
	 * note password is in clear text in config but is intended to be an auth string 
	 * only for the event gateway
	 */
	@XmlAttribute(required=true)
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * configures automatic authorisation key generation and authentication class
	 * @return the authKeyConfig
	 */
	public EventGatewayAuthKeyConfig getAuthKeyConfig() {
		return authKeyConfig;
	}

	/**
	 * configures automatic authorisation key generation and authentication class
	 * If this configuration is present then it takes precedence over the static password
	 * @param authKeyConfig the authKeyConfig to set
	 */
	@XmlElement(name="authKeyConfig")
	public void setAuthKeyConfig(EventGatewayAuthKeyConfig authKeyConfig) {
		this.authKeyConfig = authKeyConfig;
	}
	
	/**
	 * 
	 * @return ueiList List of OpenNMS event ueis which can pass this gateway
	 */
	public List<String> getUeiList() {
		return ueiList;
	}

	/**
	 * 
	 * @param ueiList List of OpenNMS event ueis which can pass this gateway
	 */
	@XmlElementWrapper(name="ueiList",required=true)
	@XmlElement(name="uei")
	public void setUeiList(List<String> ueiList) {
		this.ueiList = ueiList;
	}


	public List<NameValuePair> getStaticReplyValues() {
		return staticReplyValues;
	}

	@XmlElementWrapper(name="staticReplyValues")
	@XmlElement(name="nameValuePair")
	public void setStaticReplyValues(List<NameValuePair> staticReplyValues) {
		this.staticReplyValues = staticReplyValues;
	}

}
