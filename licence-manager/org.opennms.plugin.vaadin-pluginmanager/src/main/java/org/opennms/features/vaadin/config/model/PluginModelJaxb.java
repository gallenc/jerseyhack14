package org.opennms.features.vaadin.config.model;


import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;


@XmlRootElement(name="pluginManagerData")
@XmlAccessorType(XmlAccessType.NONE)
public class PluginModelJaxb {


	@XmlElement(name="pluginServerPassword")
	private String pluginServerPassword = "admin";

	@XmlElement(name="pluginServerUsername")
	private String pluginServerUsername = "admin";

	@XmlElement(name="pluginServerUrl")
	private String pluginServerUrl = "http://localhost:8980";
	
	@XmlElement(name="licenceShoppingCartUrl")
	private String licenceShoppingCartUrl = "http://localhost:8980";
	
	@XmlElementWrapper(name="karafDataMap")
	private SortedMap<String, KarafEntryJaxb> karafDataMap = new TreeMap<String, KarafEntryJaxb>();
	
	@XmlElement(name="availablePlugins")
	private ProductSpecList availablePlugins;
	
	@XmlElement(name="availablePluginsLastUpdated")
	private Date availablePluginsLastUpdated = null;
	
	/**
	 * returns the password of the remote plugin server
	 * @return the pluginServerPassword
	 */
	public String getPluginServerPassword() {
		return pluginServerPassword;
	}

	/**
	 * sets the password of the plugin server
	 * @param pluginServerPassword the pluginServerPassword to set
	 */
	public void setPluginServerPassword(String pluginServerPassword) {
		this.pluginServerPassword = pluginServerPassword;
	}

	/**
	 * gets the username to access the plugin server
	 * @return the pluginServerUsername
	 */
	public String getPluginServerUsername() {
		return pluginServerUsername;
	}

	/**
	 * sets the username of the plugin server
	 * @param pluginServerUsername the pluginServerUsername to set
	 */
	public void setPluginServerUsername(String pluginServerUsername) {
		this.pluginServerUsername = pluginServerUsername;
	}

	/**
	 * gets the url to access the plugin server
	 * @return the pluginServerUrl
	 */
	public String getPluginServerUrl() {
		return pluginServerUrl;
	}

	/**
	 * sets the url of the plugin server
	 * @param pluginServerUrl the pluginServerUrl to set
	 */
	public void setPluginServerUrl(String pluginServerUrl) {
		this.pluginServerUrl = pluginServerUrl;
	}

	/**
	 * @return the licenceShoppingCartUrl
	 */
	public String getLicenceShoppingCartUrl() {
		return licenceShoppingCartUrl;
	}

	/**
	 * @param licenceShoppingCartUrl the licenceShoppingCartUrl to set
	 */
	public void setLicenceShoppingCartUrl(String licenceShoppingCartUrl) {
		this.licenceShoppingCartUrl = licenceShoppingCartUrl;
	}

	/**
	 * @return the karafDataMap
	 */
	public SortedMap<String, KarafEntryJaxb> getKarafDataMap() {
		return karafDataMap;
	}

	/**
	 * @param karafDataMap the karafDataMap to set
	 */
	public void setKarafDataMap(SortedMap<String, KarafEntryJaxb> karafDataMap) {
		this.karafDataMap = karafDataMap;
	}

	/**
	 * @return the availablePlugins
	 */
	public ProductSpecList getAvailablePlugins() {
		return availablePlugins;
	}

	/**
	 * @param availablePlugins the availablePlugins to set
	 */
	public void setAvailablePlugins(ProductSpecList availablePlugins) {
		this.availablePlugins = availablePlugins;
	}

	/**
	 * @return the availablePluginsLastUpdated
	 */
	public Date getAvailablePluginsLastUpdated() {
		return availablePluginsLastUpdated;
	}

	/**
	 * @param availablePluginsLastUpdated the availablePluginsLastUpdated to set
	 */
	public void setAvailablePluginsLastUpdated(Date availablePluginsLastUpdated) {
		this.availablePluginsLastUpdated = availablePluginsLastUpdated;
	}



}
