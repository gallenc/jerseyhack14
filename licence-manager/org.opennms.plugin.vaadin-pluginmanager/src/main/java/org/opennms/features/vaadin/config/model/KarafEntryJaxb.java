

package org.opennms.features.vaadin.config.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;


@XmlRootElement(name="karafInstanceData")
@XmlAccessorType(XmlAccessType.NONE)
public class KarafEntryJaxb {
	
	@XmlElement(name="karafInstanceLastUpdated")
	private Date karafInstanceLastUpdated = null;

	@XmlElement(name="karafInstanceName")
	private String karafInstanceName = null;
	
	@XmlElement(name="karafInstanceUrl")
	private String karafInstanceUrl = null;

	@XmlElement(name="systemId")
	private String systemId = null;
	
	@XmlElement(name="installedPlugins")
	private ProductSpecList installedPlugins = new ProductSpecList();

	@XmlElement(name="installedLicenceList")
	private LicenceList installedLicenceList = new LicenceList();
	
	/**
	 * @return the karafInstanceLastUpdated
	 */
	public Date getKarafInstanceLastUpdated() {
		return karafInstanceLastUpdated;
	}

	/**
	 * @param karafInstanceLastUpdated the karafInstanceLastUpdated to set
	 */
	public void setKarafInstanceLastUpdated(Date karafInstanceLastUpdated) {
		this.karafInstanceLastUpdated = karafInstanceLastUpdated;
	}
	
	/**
	 * @return the karafInstanceName
	 */
	public String getKarafInstanceName() {
		return karafInstanceName;
	}

	/**
	 * @param karafInstanceName the karafInstanceName to set
	 */
	public void setKarafInstanceName(String karafInstanceName) {
		this.karafInstanceName = karafInstanceName;
	}

	/**
	 * @return the karafInstanceUrl
	 */
	public String getKarafInstanceUrl() {
		return karafInstanceUrl;
	}

	/**
	 * @param karafInstanceUrl the karafInstanceUrl to set
	 */
	public void setKarafInstanceUrl(String karafInstanceUrl) {
		this.karafInstanceUrl = karafInstanceUrl;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the installedPlugins
	 */
	public ProductSpecList getInstalledPlugins() {
		return installedPlugins;
	}

	/**
	 * @param installedPlugins the installedPlugins to set
	 */
	public void setInstalledPlugins(ProductSpecList installedPlugins) {
		this.installedPlugins = installedPlugins;
	}

	/**
	 * @return the installedLicenceList
	 */
	public LicenceList getInstalledLicenceList() {
		return installedLicenceList;
	}

	/**
	 * @param installedLicenceList the installedLicenceList to set
	 */
	public void setInstalledLicenceList(LicenceList installedLicenceList) {
		this.installedLicenceList = installedLicenceList;
	}



}
