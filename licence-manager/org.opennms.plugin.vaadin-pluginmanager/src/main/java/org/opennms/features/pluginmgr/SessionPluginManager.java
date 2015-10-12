package org.opennms.features.pluginmgr;

import java.util.Date;
import java.util.SortedMap;

import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;

/**
 * this model is a proxy for the PluginManagerImpl shared by all ui sessions. 
 * It allows some extra variables to be persisted and changed within a session
 * so that a session can choose which karaf instance is being operated on
 * @author admin
 *
 */
public class SessionPluginManager {

	private String karafInstance="localhost"; //default value

	private PluginManager pluginManager=null;

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
	
	public String getKarafUrl(){
		return pluginManager.getKarafInstances().get(karafInstance);
	}
	
	
	public  Date getAvailablePluginsLastUpdated(){
		return pluginManager.getAvailablePluginsLastUpdated();
	}
	
	public  Date getKarafInstanceLastUpdated(){
		return pluginManager.getKarafInstanceLastUpdated(karafInstance);
	}

	/**
	 * @return the pluginManager
	 */
	public PluginManager getPluginManager() {
		return pluginManager;
	}

	public void setPluginManager(PluginManager pluginManager) {
		this.pluginManager=pluginManager;

	}

	/**
	 * reloads the data from the selected karaf instance
	 */
	public void refreshKarafEntry(){
		pluginManager.refreshKarafEntry(karafInstance);
	}
	
	/**
	 * reloads the data from the selected karaf instance
	 */
	public void refreshAvailablePlugins(){
		pluginManager.refreshAvailablePlugins();
	}

	/**
	 * @return the availablePlugins
	 */
	public ProductSpecList getAvailablePlugins() {
		return pluginManager.getAvailablePlugins();
	}



	/**
	 * @return the installedPlugins
	 */
	public ProductSpecList getInstalledPlugins() {
		return pluginManager.getInstalledPlugins(karafInstance);
	}


	/**
	 * @return the installedLicenceList
	 */
	public LicenceList getInstalledLicenceList() {
		return pluginManager.getInstalledLicenceList(karafInstance);

	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return pluginManager.getSystemId(karafInstance);
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		pluginManager.setSystemId(systemId, karafInstance);
	}

	/**
	 * @return the pluginServerPassword
	 */
	public String getPluginServerPassword() {
		return pluginManager.getPluginServerPassword();
	}

	/**
	 * @return the pluginServerUsername
	 */
	public String getPluginServerUsername() {
		return pluginManager.getPluginServerUsername();
	}


	/**
	 * @return the pluginServerUrl
	 */
	public String getPluginServerUrl() {
		return pluginManager.getPluginServerUrl();

	}

	/**
	 * @return the licenceShoppingCartUrl
	 */
	public String getLicenceShoppingCartUrl(){
		return pluginManager.getLicenceShoppingCartUrl();
	}

	/**
	 * Sets basic data for PluginManager and persists all at once
	 * @param pluginServerUsername
	 * @param pluginServerPassword
	 * @param pluginServerUrl
	 * @param licenceShoppingCartUrl
	 */
	public synchronized void setPluginManagerBasicData(String pluginServerUsername, String pluginServerPassword, String pluginServerUrl, String licenceShoppingCartUrl){
		pluginManager.setPluginManagerBasicData(pluginServerUsername, pluginServerPassword, pluginServerUrl, licenceShoppingCartUrl);
	}


	public String generateRandomManifestSystemId(){
		return pluginManager.generateRandomManifestSystemId(karafInstance);

	}

	public void addLicence(String licenceStr){
		pluginManager.addLicence(licenceStr, karafInstance);
	}

	public void removeLicence(String selectedLicenceId) {
		pluginManager.removeLicence(selectedLicenceId, karafInstance);
	}

	public void installPlugin(String selectedProductId) {
		pluginManager.installPlugin(selectedProductId, karafInstance);

	}

	public void unInstallPlugin(String selectedProductId) {
		pluginManager.unInstallPlugin(selectedProductId, karafInstance);

	}
	
	public ProductSpecList getPluginsManifest() {
		return pluginManager.getPluginsManifest(karafInstance);
	}
	
	public void addPluginToManifest(String selectedProductId) {
		pluginManager.addPluginToManifest(selectedProductId, karafInstance);
		
	}
	
	public void removePluginFromManifest(String selectedProductId) {
		pluginManager.removePluginFromManifest( selectedProductId,karafInstance);
	}
	
	
	public void setManifestSystemId(String manifestSystemId){
		pluginManager.setManifestSystemId(manifestSystemId,karafInstance);
	}
	
	public String getManifestSystemId(){
		return pluginManager.getManifestSystemId(karafInstance);
	}
	
	/**
	 * returns list of karaf instances which can be addressed by ui
	 * @return Map of key = karafInstanceName, value = karafInstanceUrl
	 */
	public SortedMap<String,String> getKarafInstances(){
		return pluginManager.getKarafInstances();
	}


}
