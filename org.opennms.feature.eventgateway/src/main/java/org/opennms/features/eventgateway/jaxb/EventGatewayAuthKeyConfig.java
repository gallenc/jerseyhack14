package org.opennms.features.eventgateway.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="eventGatewayAuthKeyConfig")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="EventGatewayAuthKeyConfig")
public class EventGatewayAuthKeyConfig {

	
	// length of auto generated authentication key
	private int authKeylength;
	
	// time before current authentication key is replaced
	private long maxCurrentAuthKeyAge;
	
	// time to keep old authentication keys before deletion
	private long maxTimeKeepOldAuthKeys;


	/**
	 * @return the maxTimeKeepOldAuthKeys
	 */
	public long getMaxTimeKeepOldAuthKeys() {
		return maxTimeKeepOldAuthKeys;
	}

	/**
	 * @param maxTimeKeepOldAuthKeys the maxTimeKeepOldAuthKeys to set
	 */
	@XmlElement(name="maxTimeKeepOldAuthKeys",required=true)
	public void setMaxTimeKeepOldAuthKeys(long maxTimeKeepOldAuthKeys) {
		this.maxTimeKeepOldAuthKeys = maxTimeKeepOldAuthKeys;
	}

	/**
	 * @return the maxCurrentAuthKeyAge
	 */
	public long getMaxCurrentAuthKeyAge() {
		return maxCurrentAuthKeyAge;
	}

	/**
	 * @param maxCurrentAuthKeyAge the maxCurrentAuthKeyAge to set
	 */
	@XmlElement(name="maxCurrentAuthKeyAge",required=true)
	public void setMaxCurrentAuthKeyAge(long maxCurrentAuthKeyAge) {
		this.maxCurrentAuthKeyAge = maxCurrentAuthKeyAge;
	}

	/**
	 * @return the authKeylength
	 */
	public int getAuthKeylength() {
		return authKeylength;
	}

	/**
	 * @param authKeylength the authKeylength to set
	 */
	@XmlElement(name="authKeylength",required=true)
	public void setAuthKeylength(int authKeylength) {
		this.authKeylength = authKeylength;
	}


}
