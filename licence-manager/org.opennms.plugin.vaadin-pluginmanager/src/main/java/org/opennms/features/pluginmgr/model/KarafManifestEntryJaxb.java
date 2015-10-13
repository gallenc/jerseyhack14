package org.opennms.features.pluginmgr.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;

@XmlRootElement(name="karafManifestData")
@XmlAccessorType(XmlAccessType.NONE)
public class KarafManifestEntryJaxb {
	
	@XmlElement(name="remoteIsAccessible")
	private Boolean remoteIsAccessible=true;
	
	@XmlElement(name="allowUpdateMessages")
	private Boolean allowUpdateMessages=false;
	
	@XmlElement(name="pluginManifest")
	private ProductSpecList pluginManifest=new ProductSpecList();
	
	@XmlElement(name="manifestSystemId")
	private String manifestSystemId=null;
	
	/**
	 * @return the remoteIsAccessible
	 */
	public Boolean getRemoteIsAccessible() {
		return remoteIsAccessible;
	}

	/**
	 * @param remoteIsAccessible the remoteIsAccessible to set
	 */
	public void setRemoteIsAccessible(Boolean remoteIsAccessible) {
		this.remoteIsAccessible = remoteIsAccessible;
	}

	/**
	 * @return the allowUpdateMessages
	 */
	public Boolean getAllowUpdateMessages() {
		return allowUpdateMessages;
	}

	/**
	 * @param allowUpdateMessages the allowUpdateMessages to set
	 */
	public void setAllowUpdateMessages(Boolean allowUpdateMessages) {
		this.allowUpdateMessages = allowUpdateMessages;
	}

	/**
	 * @return the pluginManifest
	 */
	public ProductSpecList getPluginManifest() {
		return pluginManifest;
	}

	/**
	 * @param pluginManifest the pluginManifest to set
	 */
	public void setPluginManifest(ProductSpecList pluginManifest) {
		this.pluginManifest = pluginManifest;
	}

	/**
	 * @return the manifestSystemId
	 */
	public String getManifestSystemId() {
		return manifestSystemId;
	}

	/**
	 * @param manifestSystemId the manifestSystemId to set
	 */
	public void setManifestSystemId(String manifestSystemId) {
		this.manifestSystemId = manifestSystemId;
	}
}
