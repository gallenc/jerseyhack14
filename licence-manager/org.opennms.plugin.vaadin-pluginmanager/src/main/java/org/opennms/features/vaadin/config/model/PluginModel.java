package org.opennms.features.vaadin.config.model;


import java.io.File;
import java.util.Date;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.opennms.karaf.featuremgr.rest.client.jerseyimpl.FeaturesServiceClientRestJerseyImpl;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;
import org.opennms.karaf.licencemgr.rest.client.jerseyimpl.LicenceManagerClientRestJerseyImpl;
import org.opennms.karaf.licencemgr.rest.client.jerseyimpl.ProductRegisterClientRestJerseyImpl;


public class PluginModel {

	private static String PRODUCT_PUB_BASE_PATH = "/licencemgr/rest/product-pub";
	private static String PRODUCT_REG_BASE_PATH = "/licencemgr/rest/product-reg";

	private static String LICENCE_PUB_BASE_PATH = "/licencemgr/rest/licence-pub"; // not used
	private static String LICENCE_MGR_BASE_PATH = "/licencemgr/rest/licence-mgr";
	
	private static String FEATURE_MGR_BASE_PATH = "/featuremgr";

	private String fileUri="./pluginmodeldata.xml";

	private PluginModelJaxb pluginModelJaxb = new PluginModelJaxb();

	/**
	 * fileUri is the location of the persisted plugin data
	 * @return
	 */
	public String getFileUri() {
		return fileUri;
	}

	/**
	 * fileUri is the location of the persisted plugin data
	 * @param fileUri
	 */
	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}


	/**
	 * returns the password of the remote plugin server
	 * @return the pluginServerPassword
	 */
	public synchronized  String getPluginServerPassword() {
		return pluginModelJaxb.getPluginServerPassword();
	}

	/**
	 * 
	 * @param pluginServerPassword
	 */
	public void setPluginServerPassword(String pluginServerPassword){
		pluginModelJaxb.setPluginServerPassword(pluginServerPassword);
	}

	/**
	 * gets the username to access the plugin server
	 * @return the pluginServerUsername
	 */
	public synchronized String getPluginServerUsername() {
		return pluginModelJaxb.getPluginServerUsername();
	}

	/**
	 * 
	 * @param pluginServerUsername
	 */
	public void setPluginServerUsername(String pluginServerUsername){
		pluginModelJaxb.setPluginServerUsername(pluginServerUsername);
	}

	/**
	 * gets the url to access the plugin server
	 * @return the pluginServerUrl
	 */
	public synchronized String getPluginServerUrl() {
		return pluginModelJaxb.getPluginServerUrl();
	}

	/**
	 * 
	 * @param pluginServerUrl
	 */
	public void setPluginServerUrl(String pluginServerUrl){
		pluginModelJaxb.setPluginServerUrl(pluginServerUrl);
	}

	/**
	 * @return the licenceShoppingCartUrl
	 */
	public synchronized String getLicenceShoppingCartUrl() {
		return pluginModelJaxb.getLicenceShoppingCartUrl();
	}

	/**
	 * 
	 * @param licenceShoppingCartUrl
	 */
	public void setLicenceShoppingCartUrl(String licenceShoppingCartUrl){
		pluginModelJaxb.setLicenceShoppingCartUrl(licenceShoppingCartUrl);
	}


	/**
	 * Sets basic data for PluginModel and persists all at once
	 * @param pluginServerUsername
	 * @param pluginServerPassword
	 * @param pluginServerUrl
	 * @param licenceShoppingCartUrl
	 */
	public synchronized void setPluginModelBasicData(String pluginServerUsername, String pluginServerPassword, String pluginServerUrl, String licenceShoppingCartUrl){
		pluginModelJaxb.setPluginServerUsername(pluginServerUsername);
		pluginModelJaxb.setPluginServerPassword(pluginServerPassword);
		pluginModelJaxb.setPluginServerUrl(pluginServerUrl);
		pluginModelJaxb.setLicenceShoppingCartUrl(licenceShoppingCartUrl);
		persist();
	}


	/**
	 * returns list of karaf instances which can be addressed by ui
	 * @return Map of key = karafInstanceName, value = karafInstanceUrl
	 */
	public synchronized SortedMap<String,String> getKarafInstances(){

		//TODO GET DATA FROM OPENNMS

		SortedMap<String,String> karafInstances= new TreeMap<String,String>();

		karafInstances.put("localhost", "http://localhost:8980/opennms");

		return karafInstances;

	}

	/**
	 * 
	 */
	public synchronized void refreshAvailablePlugins() {

		ProductRegisterClientRestJerseyImpl productRegisterClient = new ProductRegisterClientRestJerseyImpl();
		productRegisterClient.setBaseUrl(this.getPluginServerUrl());
		productRegisterClient.setUserName(this.getPluginServerUsername());
		productRegisterClient.setPassword(this.getPluginServerPassword());

		productRegisterClient.setBasePath(PRODUCT_PUB_BASE_PATH);

		ProductSpecList availablePlugins;
		try {
			availablePlugins = productRegisterClient.getList();
			pluginModelJaxb.setAvailablePlugins(availablePlugins);
			pluginModelJaxb.setAvailablePluginsLastUpdated(new Date());
			persist();
		} catch (Exception e) {
			throw new RuntimeException("problem refreshing available plugins for"
					+ " plugin server Url="+this.getPluginServerUrl()
					+ ": ", e);
		}

	}



	/**
	 * returns list of available plugins obtained from the plugin server
	 * @return the availablePlugins
	 */
	public synchronized ProductSpecList getAvailablePlugins() {

		if (pluginModelJaxb.getAvailablePlugins()==null 
				|| pluginModelJaxb.getAvailablePlugins().getProductSpecList()==null 
				|| pluginModelJaxb.getAvailablePlugins().getProductSpecList().size()==0) refreshAvailablePlugins();

		return pluginModelJaxb.getAvailablePlugins();
	}

	/**
	 * refreshes complete KarafEntryJaxb from remote karaf server
	 * throws exception if cannot refresh entry
	 */
	public synchronized  KarafEntryJaxb refreshKarafEntry(String karafInstance){
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");

		SortedMap<String, String> karafInstances = getKarafInstances();
		if (! karafInstances.containsKey(karafInstance)) throw new RuntimeException("opennms does not know karafInstance="+karafInstance);
		String karafInstanceUrl=karafInstances.get(karafInstance);

		KarafEntryJaxb karafEntryJaxb= null;

		try{
			karafEntryJaxb= new KarafEntryJaxb();

			karafEntryJaxb.setKarafInstanceName(karafInstance);
			karafEntryJaxb.setKarafInstanceUrl(karafInstanceUrl);

			ProductRegisterClientRestJerseyImpl productRegisterClient = new ProductRegisterClientRestJerseyImpl();
			productRegisterClient.setBaseUrl(karafInstanceUrl);
			productRegisterClient.setUserName(this.getPluginServerUsername()); //TODO NEED LOCAL PASSWORD / NAME
			productRegisterClient.setPassword(this.getPluginServerPassword()); //TODO NEED LOCAL PASSWORD / NAME

			// getting karaf installed plugins
			productRegisterClient.setBasePath(PRODUCT_REG_BASE_PATH);

			ProductSpecList installedPlugins;
			try {
				installedPlugins = productRegisterClient.getList();
				karafEntryJaxb.setInstalledPlugins(installedPlugins);
			} catch (Exception e) {
				throw new RuntimeException("problem refreshing installed plugins for "
						+ "karafInstance="+karafInstance
						+ " karafInstanceUrl="+karafInstanceUrl
						+ ": ", e);
			}

			// getting karaf installed licences and system id
			LicenceManagerClientRestJerseyImpl licenceManagerClient = new LicenceManagerClientRestJerseyImpl();
			licenceManagerClient.setBaseUrl(karafInstanceUrl);
			licenceManagerClient.setUserName(this.getPluginServerUsername()); //TODO NEED LOCAL PASSWORD / NAME
			licenceManagerClient.setPassword(this.getPluginServerPassword()); //TODO NEED LOCAL PASSWORD / NAME

			licenceManagerClient.setBasePath(LICENCE_MGR_BASE_PATH);
			LicenceList installedLicenceList;
			try {
				installedLicenceList = licenceManagerClient.getLicenceMap();
				karafEntryJaxb.setInstalledLicenceList(installedLicenceList);
				String systemId = licenceManagerClient.getSystemId();
				karafEntryJaxb.setSystemId(systemId);
			} catch (Exception e) {
				throw new RuntimeException("problem refreshing installed licences for "
						+ "karafInstance="+karafInstance
						+ " karafInstanceUrl="+karafInstanceUrl
						+ ": ", e);
			}

			karafEntryJaxb.setKarafInstanceLastUpdated(new Date());

			pluginModelJaxb.getKarafDataMap().put(karafInstance, karafEntryJaxb);

			persist();

		} catch (Exception e){
			throw new RuntimeException("problem updating data from karaf Instance"+karafInstance, e);
		}

		return karafEntryJaxb;
	}

	/**
	 * returns the time the karaf instance date was last updated from the instance
	 * returns null if never updated
	 * @param karafInstance
	 * @return
	 */
	public synchronized Date getKarafInstanceLastUpdated(String karafInstance){
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");
		if (! pluginModelJaxb.getKarafDataMap().containsKey(karafInstance)){
			refreshKarafEntry(karafInstance);
		} 
		KarafEntryJaxb karafEntry = pluginModelJaxb.getKarafDataMap().get(karafInstance);
		return karafEntry.getKarafInstanceLastUpdated();
	}

	/**
	 * returns the time when the available plugins were last updated.
	 * null if never updated
	 * @return
	 */
	public synchronized Date getAvailablePluginsLastUpdated() {
		return pluginModelJaxb.getAvailablePluginsLastUpdated();
	}

	/**
	 * returns the plugins installed in the given karaf instance
	 * @return the installedPlugins
	 */
	public synchronized ProductSpecList getInstalledPlugins(String karafInstance) {
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");
		if (! pluginModelJaxb.getKarafDataMap().containsKey(karafInstance)){
			refreshKarafEntry(karafInstance);
		} 
		KarafEntryJaxb karafEntry = pluginModelJaxb.getKarafDataMap().get(karafInstance);
		return karafEntry.getInstalledPlugins();
	}


	/**
	 * returns the licenses installed in the karaf instance
	 * @return the installedLicenceList
	 */
	public synchronized LicenceList getInstalledLicenceList(String karafInstance) {
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");
		if (! pluginModelJaxb.getKarafDataMap().containsKey(karafInstance)){
			refreshKarafEntry(karafInstance);
		} 
		KarafEntryJaxb karafEntry = pluginModelJaxb.getKarafDataMap().get(karafInstance);
		return karafEntry.getInstalledLicenceList();
	}


	/**
	 * returns the system id of the karaf instance
	 * @return the systemId
	 */
	public synchronized String getSystemId(String karafInstance) {
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");
		if (! pluginModelJaxb.getKarafDataMap().containsKey(karafInstance)){
			refreshKarafEntry(karafInstance);
		} 
		KarafEntryJaxb karafEntry = pluginModelJaxb.getKarafDataMap().get(karafInstance);
		return karafEntry.getSystemId();
	}

	/**
	 * sets the system id of the karaf instance
	 * @param systemId the systemId to set
	 */
	public synchronized void setSystemId(String systemId, String karafInstance) {
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");
		if(systemId==null) throw new RuntimeException("systemId cannot be null");

		SortedMap<String, String> karafInstances = getKarafInstances();
		if (! karafInstances.containsKey(karafInstance)) throw new RuntimeException("opennms does not know karafInstance="+karafInstance);
		String karafInstanceUrl=karafInstances.get(karafInstance);

		// setting systemId for karaf instance
		LicenceManagerClientRestJerseyImpl licenceManagerClient = new LicenceManagerClientRestJerseyImpl();
		licenceManagerClient.setBaseUrl(karafInstanceUrl);
		licenceManagerClient.setUserName(this.getPluginServerUsername()); //TODO NEED LOCAL PASSWORD / NAME
		licenceManagerClient.setPassword(this.getPluginServerPassword()); //TODO NEED LOCAL PASSWORD / NAME
		licenceManagerClient.setBasePath(LICENCE_MGR_BASE_PATH);

		try {
			licenceManagerClient.setSystemId(systemId);
			refreshKarafEntry(karafInstance);
		} catch (Exception e) {
			throw new RuntimeException("problem updating systemId for "
					+ "karafInstance="+karafInstance
					+ " karafInstanceUrl="+karafInstanceUrl
					+ ": ", e);
		}

	}

	/**
	 * generates and installs a random system id for the karaf instance
	 * @param karafInstance
	 * @return new system id
	 */
	public synchronized String generateRandomSystemId(String karafInstance){
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");

		SortedMap<String, String> karafInstances = getKarafInstances();
		if (! karafInstances.containsKey(karafInstance)) throw new RuntimeException("opennms does not know karafInstance="+karafInstance);
		String karafInstanceUrl=karafInstances.get(karafInstance);

		// setting systemId for karaf instance
		LicenceManagerClientRestJerseyImpl licenceManagerClient = new LicenceManagerClientRestJerseyImpl();
		licenceManagerClient.setBaseUrl(karafInstanceUrl);
		licenceManagerClient.setUserName(this.getPluginServerUsername()); //TODO NEED LOCAL PASSWORD / NAME
		licenceManagerClient.setPassword(this.getPluginServerPassword()); //TODO NEED LOCAL PASSWORD / NAME
		licenceManagerClient.setBasePath(LICENCE_MGR_BASE_PATH);

		String systemId=null;
		try {
			systemId = licenceManagerClient.makeSystemInstance();
			refreshKarafEntry(karafInstance);
			return systemId;
		} catch (Exception e) {
			throw new RuntimeException("problem generating new random systemId for "
					+ "karafInstance="+karafInstance
					+ " karafInstanceUrl="+karafInstanceUrl
					+ ": ", e);
		}
	}

	/**
	 * adds a licence to the karaf instance
	 * @param licenceStr
	 * @param karafInstance
	 */
	public synchronized void addLicence(String licenceStr,String karafInstance){
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");
		if(licenceStr==null) throw new RuntimeException("licenceStr cannot be null");

		SortedMap<String, String> karafInstances = getKarafInstances();
		if (! karafInstances.containsKey(karafInstance)) throw new RuntimeException("opennms does not know karafInstance="+karafInstance);
		String karafInstanceUrl=karafInstances.get(karafInstance);

		// setting systemId for karaf instance
		LicenceManagerClientRestJerseyImpl licenceManagerClient = new LicenceManagerClientRestJerseyImpl();
		licenceManagerClient.setBaseUrl(karafInstanceUrl);
		licenceManagerClient.setUserName(this.getPluginServerUsername()); //TODO NEED LOCAL PASSWORD / NAME
		licenceManagerClient.setPassword(this.getPluginServerPassword()); //TODO NEED LOCAL PASSWORD / NAME
		licenceManagerClient.setBasePath(LICENCE_MGR_BASE_PATH);

		try {
			licenceManagerClient.addLicence(licenceStr);
			refreshKarafEntry(karafInstance);
		} catch (Exception e) {
			throw new RuntimeException("problem updating licence String for "
					+ "karafInstance="+karafInstance
					+ " karafInstanceUrl="+karafInstanceUrl
					+ ": ", e);
		}

	}

	/**
	 * 
	 * @param selectedLicenceId
	 * @param karafInstance
	 */
	public void removeLicence(String selectedLicenceId, String karafInstance) {
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");
		if(selectedLicenceId==null) throw new RuntimeException("selectedLicenceId cannot be null");

		SortedMap<String, String> karafInstances = getKarafInstances();
		if (! karafInstances.containsKey(karafInstance)) throw new RuntimeException("opennms does not know karafInstance="+karafInstance);
		String karafInstanceUrl=karafInstances.get(karafInstance);

		// setting systemId for karaf instance
		LicenceManagerClientRestJerseyImpl licenceManagerClient = new LicenceManagerClientRestJerseyImpl();
		licenceManagerClient.setBaseUrl(karafInstanceUrl);
		licenceManagerClient.setUserName(this.getPluginServerUsername()); //TODO NEED LOCAL PASSWORD / NAME
		licenceManagerClient.setPassword(this.getPluginServerPassword()); //TODO NEED LOCAL PASSWORD / NAME
		licenceManagerClient.setBasePath(LICENCE_MGR_BASE_PATH);

		try {
			licenceManagerClient.removeLicence(selectedLicenceId);
			refreshKarafEntry(karafInstance);
		} catch (Exception e) {
			throw new RuntimeException("problem updating licence String for "
					+ "karafInstance="+karafInstance
					+ " karafInstanceUrl="+karafInstanceUrl
					+ ": ", e);
		}

	}

	/**
	 * installs a plugin for the product id in the selected karaf instance
	 * @param selectedProductId
	 * @param karafInstance
	 */
	public synchronized void installPlugin(String selectedProductId,String karafInstance) {
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");
		if(selectedProductId==null) throw new RuntimeException("selectedProductId cannot be null");
		
		SortedMap<String, String> karafInstances = getKarafInstances();
		if (! karafInstances.containsKey(karafInstance)) throw new RuntimeException("opennms does not know karafInstance="+karafInstance);
		String karafInstanceUrl=karafInstances.get(karafInstance);

		ProductMetadata productMetadata=null;
		for (ProductMetadata pMetadata : pluginModelJaxb.getAvailablePlugins().getProductSpecList()){
			if (selectedProductId.equals(pMetadata.getProductId())) {
				productMetadata=pMetadata;
				break;
			}
		}
		if(productMetadata==null) throw new RuntimeException("cannot install unknown available productId="+selectedProductId);
		if(productMetadata.getFeatureRepository()==null) throw new RuntimeException("feature repository cannot be null for productId="+selectedProductId);

		FeaturesServiceClientRestJerseyImpl featuresServiceClient = new FeaturesServiceClientRestJerseyImpl(); 
		featuresServiceClient.setBaseUrl(karafInstanceUrl);
		featuresServiceClient.setUserName(this.getPluginServerUsername()); //TODO NEED LOCAL PASSWORD / NAME
		featuresServiceClient.setPassword(this.getPluginServerPassword()); //TODO NEED LOCAL PASSWORD / NAME
		featuresServiceClient.setBasePath(FEATURE_MGR_BASE_PATH);
		
		try {
			// add feature repository
			featuresServiceClient.featuresAddRepository(productMetadata.getFeatureRepository());
			// a feature id looks like name/version.
			String version=null;
			String name=null;
			if(selectedProductId.contains("/")) {
				int i = selectedProductId.indexOf('/');
				version = selectedProductId.substring(i+1);
				name =  selectedProductId.substring(0, selectedProductId.indexOf('/'));
			}
			//add feature
			featuresServiceClient.featuresInstall(name, version);
			refreshKarafEntry(karafInstance);
		} catch (Exception e) {
			throw new RuntimeException("problem installing product "+selectedProductId
					+ " for karafInstance="+karafInstance
					+ " karafInstanceUrl="+karafInstanceUrl
					+ ": ", e);
		}

	}

	/**
	 * uninstalls a plugin for the product id in the selected karaf instance
	 * @param selectedProductId
	 * @param karafInstance
	 */
	public synchronized void unInstallPlugin(String selectedProductId,String karafInstance) {
		if(karafInstance==null) throw new RuntimeException("karafInstance cannot be null");
		if(selectedProductId==null) throw new RuntimeException("selectedProductId cannot be null");
		
		SortedMap<String, String> karafInstances = getKarafInstances();
		if (! karafInstances.containsKey(karafInstance)) throw new RuntimeException("opennms does not know karafInstance="+karafInstance);
		String karafInstanceUrl=karafInstances.get(karafInstance);

		ProductMetadata productMetadata=null;
		for (ProductMetadata pMetadata : pluginModelJaxb.getAvailablePlugins().getProductSpecList()){
			if (selectedProductId.equals(pMetadata.getProductId())) {
				productMetadata=pMetadata;
				break;
			}
		}
		if(productMetadata==null) throw new RuntimeException("cannot install unknown available productId="+selectedProductId);
		if(productMetadata.getFeatureRepository()==null) throw new RuntimeException("feature repository cannot be null for productId="+selectedProductId);

		FeaturesServiceClientRestJerseyImpl featuresServiceClient = new FeaturesServiceClientRestJerseyImpl(); 
		featuresServiceClient.setBaseUrl(karafInstanceUrl);
		featuresServiceClient.setUserName(this.getPluginServerUsername()); //TODO NEED LOCAL PASSWORD / NAME
		featuresServiceClient.setPassword(this.getPluginServerPassword()); //TODO NEED LOCAL PASSWORD / NAME
		featuresServiceClient.setBasePath(FEATURE_MGR_BASE_PATH);
		
		try {
			String version=null;
			String name=null;
			if(selectedProductId.contains("/")) {
				int i = selectedProductId.indexOf('/');
				version = selectedProductId.substring(i+1);
				name =  selectedProductId.substring(0, selectedProductId.indexOf('/'));
			}
			//add feature
			featuresServiceClient.featuresUninstall(name, version);
			refreshKarafEntry(karafInstance);
		} catch (Exception e) {
			throw new RuntimeException("problem installing product "+selectedProductId
					+ " for karafInstance="+karafInstance
					+ " karafInstanceUrl="+karafInstanceUrl
					+ ": ", e);
		}

	}

	/**
	 * persists the plugin data to the file indicated by fileUri
	 */
	public synchronized void persist(){
		if (fileUri==null) throw new RuntimeException("persist failed - fileUri must be set for plugin manager");

		try {

			File pluginModelFile = new File(fileUri);
			JAXBContext jaxbContext = JAXBContext.newInstance(PluginModelJaxb.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(pluginModelJaxb, pluginModelFile);

		} catch (JAXBException e) {
			throw new RuntimeException("Problem persisting Plugin Manager Data",e);
		}
	}

	/**
	 * blueprint init-method
	 * loads the persisted plugin data to the file indicated by fileUri
	 */
	public synchronized void load(){
		if (fileUri==null) throw new RuntimeException("load failed - fileUri must be set for plugin manager");
		System.out.println("Plugin Manager Starting");

		//TODO CREATE ROLLING FILE TO AVOID CORRUPTED FILE
		try {

			File pluginManagerFile = new File(fileUri);

			if (pluginManagerFile.exists()) {
				JAXBContext jaxbContext = JAXBContext.newInstance(PluginModelJaxb.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				PluginModelJaxb loadedPluginModelJaxb = (PluginModelJaxb) jaxbUnmarshaller.unmarshal(pluginManagerFile);

				pluginModelJaxb = loadedPluginModelJaxb;

				System.out.println("Plugin Manager successfully loaded historic data from file="+pluginManagerFile.getAbsolutePath());
			} else {
				System.out.println("Plugin Manager data file="+pluginManagerFile.getAbsolutePath()+" does not exist. A new one will be created.");
				persist(); // persists first version of plugin model
			}
			System.out.println("Plugin Manager Started");
		} catch (JAXBException e) {
			System.out.println("Plugin Manager Problem Starting: "+ e.getMessage());
			throw new RuntimeException("Problem loading Plugin Manager Data",e);
		}
	}

	/**
	 * blueprint destroy-method
	 */
	public synchronized void close() {
		System.out.println("Plugin Manager Shutting Down ");
	}





}
