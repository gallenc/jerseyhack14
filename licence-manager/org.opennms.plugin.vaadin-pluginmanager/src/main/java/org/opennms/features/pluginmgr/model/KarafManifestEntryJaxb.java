package org.opennms.features.pluginmgr.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;

@XmlRootElement(name="karafManifestData")
@XmlAccessorType(XmlAccessType.NONE)
public class KarafManifestEntryJaxb {
	
	@XmlElement(name="remoteAccessable")
	private Boolean remoteAccessable=true;
	
	@XmlElement(name="isPluginManagerParent")
	private Boolean isPluginManagerParent=false;
	
	@XmlElement(name="pluginManifest")
	private ProductSpecList pluginManifest=new ProductSpecList();
	
	@XmlElement(name="manifestSystemId")
	private String manifestSystemId=null;
	
	/**
	 * @return the remoteAccessable
	 */
	public Boolean getRemoteAccessable() {
		return remoteAccessable;
	}

	/**
	 * @param remoteAccessable the remoteAccessable to set
	 */
	public void setRemoteAccessable(Boolean remoteAccessable) {
		this.remoteAccessable = remoteAccessable;
	}

	/**
	 * @return the isPluginManagerParent
	 */
	public Boolean getIsPluginManagerParent() {
		return isPluginManagerParent;
	}

	/**
	 * @param isPluginManagerParent the isPluginManagerParent to set
	 */
	public void setIsPluginManagerParent(Boolean isPluginManagerParent) {
		this.isPluginManagerParent = isPluginManagerParent;
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
