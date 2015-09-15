package org.opennms.features.vaadin.config.model;

import java.util.Date;
import java.util.SortedMap;

import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;

/**
 * this model is a proxy for the PluginModel shared by all ui sessions. 
 * It allows some extra variables to be persisted and changed within a session
 * so that a session can choose which karaf instance is being operated on
 * @author admin
 *
 */
public class SessionPluginModel {

	private String karafInstance="localhost"; //default value

	private PluginModel pluginModel=null;

	/** 
	 * gets the karaf instance which will be used for other commands
	 * @return
	 */
	public String getKarafInstance() {
		return karafInstance;
	}

	/**
	 * sets the karaf instance which will be used for other commands
	 * @param karafInstance
	 */
	public void setKarafInstance(String karafInstance) {
		this.karafInstance = karafInstance;
	}
	
	
	public  Date getAvailablePluginsLastUpdated(){
		return pluginModel.getAvailablePluginsLastUpdated();
	}
	
	public  Date getKarafInstanceLastUpdated(){
		return pluginModel.getKarafInstanceLastUpdated(karafInstance);
	}

	/**
	 * @return the pluginModel
	 */
	public PluginModel getPluginModel() {
		return pluginModel;
	}

	public void setPluginModel(PluginModel pluginModel) {
		this.pluginModel=pluginModel;

	}

	/**
	 * reloads the data from the selected karaf instance
	 */
	public void refreshKarafEntry(){
		pluginModel.refreshKarafEntry(karafInstance);
	}
	
	/**
	 * reloads the data from the selected karaf instance
	 */
	public void refreshAvailablePlugins(){
		pluginModel.refreshAvailablePlugins();
	}

	/**
	 * @return the availablePlugins
	 */
	public ProductSpecList getAvailablePlugins() {
		return pluginModel.getAvailablePlugins();
	}



	/**
	 * @return the installedPlugins
	 */
	public ProductSpecList getInstalledPlugins() {
		return pluginModel.getInstalledPlugins(karafInstance);
	}


	/**
	 * @return the installedLicenceList
	 */
	public LicenceList getInstalledLicenceList() {
		return pluginModel.getInstalledLicenceList(karafInstance);

	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return pluginModel.getSystemId(karafInstance);
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		pluginModel.setSystemId(systemId, karafInstance);
	}

	/**
	 * @return the pluginServerPassword
	 */
	public String getPluginServerPassword() {
		return pluginModel.getPluginServerPassword();
	}

	/**
	 * @return the pluginServerUsername
	 */
	public String getPluginServerUsername() {
		return pluginModel.getPluginServerUsername();
	}


	/**
	 * @return the pluginServerUrl
	 */
	public String getPluginServerUrl() {
		return pluginModel.getPluginServerUrl();

	}

	/**
	 * @return the licenceShoppingCartUrl
	 */
	public String getLicenceShoppingCartUrl(){
		return pluginModel.getLicenceShoppingCartUrl();
	}

	/**
	 * Sets basic data for PluginModel and persists all at once
	 * @param pluginServerUsername
	 * @param pluginServerPassword
	 * @param pluginServerUrl
	 * @param licenceShoppingCartUrl
	 */
	public synchronized void setPluginModelBasicData(String pluginServerUsername, String pluginServerPassword, String pluginServerUrl, String licenceShoppingCartUrl){
		pluginModel.setPluginModelBasicData(pluginServerUsername, pluginServerPassword, pluginServerUrl, licenceShoppingCartUrl);
	}


	public String generateRandomSystemId(){
		return pluginModel.generateRandomSystemId(karafInstance);

	}

	public void addLicence(String licenceStr){
		pluginModel.addLicence(licenceStr, karafInstance);;
	}


	public void installPlugin(String selectedProductId) {
		pluginModel.installPlugin(selectedProductId, karafInstance);

	}

	public void unInstallPlugin(String selectedProductId) {
		pluginModel.unInstallPlugin(selectedProductId, karafInstance);

	}

	/**
	 * returns list of karaf instances which can be addressed by ui
	 * @return Map of key = karafInstanceName, value = karafInstanceUrl
	 */
	public SortedMap<String,String> getKarafInstances(){
		return pluginModel.getKarafInstances();
	}


}
