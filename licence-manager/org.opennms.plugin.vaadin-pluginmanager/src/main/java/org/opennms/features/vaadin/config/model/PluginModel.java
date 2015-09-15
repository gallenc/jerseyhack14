package org.opennms.features.vaadin.config.model;


import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.opennms.karaf.licencemgr.metadata.Licence;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceEntry;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;


public class PluginModel {

	private String fileUri="./pluginmodeldata.xml";

	private PluginModelJaxb pluginModelJaxb = new PluginModelJaxb();

	public String getFileUri() {
		return fileUri;
	}

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
	 * gets the username to access the plugin server
	 * @return the pluginServerUsername
	 */
	public synchronized String getPluginServerUsername() {
		return pluginModelJaxb.getPluginServerUsername();
	}



	/**
	 * gets the url to access the plugin server
	 * @return the pluginServerUrl
	 */
	public synchronized String getPluginServerUrl() {
		return pluginModelJaxb.getPluginServerUrl();
	}



	/**
	 * @return the licenceShoppingCartUrl
	 */
	public synchronized String getLicenceShoppingCartUrl() {
		return pluginModelJaxb.getLicenceShoppingCartUrl();
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

		karafInstances.put("localhost", "http://localhost:8980");

		return karafInstances;

	}

	public synchronized void refreshAvailablePlugins() {
		// TODO REPLACE WITH GETTER CODE

		// test code generating a spec list

		ProductSpecList availablePlugins = new ProductSpecList();

		String[] productIds = {"Mercury","Venus","Earth","Mars","Jupiter","Saturn","Neptune","Uranus"};

		for (String pid: productIds){
			ProductMetadata pmeta= new ProductMetadata();
			pmeta.setProductId(pid);
			pmeta.setOrganization("OpenNMS Project");
			pmeta.setProductDescription("Test product description");
			pmeta.setFeatureRepository("mvn:org.opennms.licencemgr/licence.manager.example/2.10.0/xml/features");
			pmeta.setProductName("test Bundle");
			pmeta.setProductUrl("http:\\\\opennms.co.uk");
			pmeta.setLicenceKeyRequired(true);
			pmeta.setLicenceType("OpenNMS EULA See http:\\\\opennms.co.uk\\EULA");
			availablePlugins.getProductSpecList().add(pmeta);
		}

		pluginModelJaxb.setAvailablePlugins(availablePlugins);
		pluginModelJaxb.setAvailablePluginsLastUpdated(new Date());
		persist();
		
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
			
			// TODO REPLACE WITH GETTER CODE against karaf


			ProductSpecList installedPlugins = new ProductSpecList();

			String[] productIds = {"Mercury","Venus","Earth","Mars","Jupiter","Saturn","Neptune","Uranus"};

			for (String pid: productIds){
				ProductMetadata pmeta= new ProductMetadata();
				pmeta.setProductId(pid);
				pmeta.setOrganization("OpenNMS Project");
				pmeta.setProductDescription("Test product description");
				pmeta.setFeatureRepository("mvn:org.opennms.licencemgr/licence.manager.example/2.10.0/xml/features");
				pmeta.setProductName("test Bundle");
				pmeta.setProductUrl("http:\\\\opennms.co.uk");
				pmeta.setLicenceKeyRequired(true);
				pmeta.setLicenceType("OpenNMS EULA See http:\\\\opennms.co.uk\\EULA");
				installedPlugins.getProductSpecList().add(pmeta);
			}

			// test values
			LicenceList installedLicenceList = new LicenceList();
			List<LicenceEntry> licenceList = installedLicenceList.getLicenceList();

			LicenceEntry le = new LicenceEntry();
			le.setProductId("product1");
			le.setLicenceStr("incorrect String");
			licenceList.add(le);

			LicenceEntry le2 = new LicenceEntry();
			le2.setProductId("myproject/1.0-SNAPSHOT");
			String correctlicence="3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D22796573223F3E3C6C6963656E63654D657461646174613E3C70726F6475637449643E6D7970726F6A6563742F312E302D534E415053484F543C2F70726F6475637449643E3C6C6963656E7365653E4D722043726169672047616C6C656E3C2F6C6963656E7365653E3C6C6963656E736F723E4F70656E4E4D5320554B3C2F6C6963656E736F723E3C73797374656D49643E333265333936653336623238656635642D61343865663163623C2F73797374656D49643E3C7374617274446174653E323031352D30312D30375431353A31303A34352E3133385A3C2F7374617274446174653E3C657870697279446174653E323031352D30312D30375431353A31303A34352E3133385A3C2F657870697279446174653E3C6F7074696F6E733E3C6F7074696F6E3E3C6465736372697074696F6E3E7468697320697320746865206465736372697074696F6E3C2F6465736372697074696F6E3E3C6E616D653E6F7074696F6E313C2F6E616D653E3C76616C75653E6E657776616C75653C2F76616C75653E3C2F6F7074696F6E3E3C2F6F7074696F6E733E3C2F6C6963656E63654D657461646174613E:612D7BC47629FEF9D0B6ECE90AFDC3A2817027F4815CDFA866914C23B54FF7A428D815E0F8A5939EDFE742ACE10711BC533CC5F73F09130537F8DC4579718FB0AA113FA185707257BBD44E53008368BBF0FAA9E84C53B15302B56E0F222C22AE0B732A1B0E72A267592FF34A3101C209A988BBF71CC2ACF530EDE95164A3CCFF243C2B638FFA7AE243B0436BF91DC0B4EBB0F8884A100BBAE7CA884C258C7F680131FDF6FEEC0566F04483A56DE517FB8D189078A4A98D5FA77F7C2F2C298560EB5C3624C912331F2B750DE082486C190337A3AD51B20DADC1C48B717E8D85464FCDEB30A9DF6072DE7A1933A6745823CE6D2B2C1C30D7D194DCEA122AD54DB8:3E10949F8A08F5CDCB78BF9A944C9534-286cdfd5";
			le2.setLicenceStr(correctlicence);
			licenceList.add(le2);

			karafEntryJaxb.setInstalledLicenceList(installedLicenceList);
			karafEntryJaxb.setInstalledPlugins(installedPlugins);
			karafEntryJaxb.setKarafInstanceName(karafInstance);

			karafEntryJaxb.setSystemId("32e396e36b28ef5d-a48ef1cb");

			karafEntryJaxb.setKarafInstanceUrl(karafInstanceUrl);

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
		if (! pluginModelJaxb.getKarafDataMap().containsKey(karafInstance)){
			refreshKarafEntry(karafInstance);
		} 
		KarafEntryJaxb karafEntry = pluginModelJaxb.getKarafDataMap().get(karafInstance);
		return karafEntry.getKarafInstanceLastUpdated();
	}
	
	/**
	 * returns the time when the available plugisn were last updated.
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
		if (! pluginModelJaxb.getKarafDataMap().containsKey(karafInstance)){
			refreshKarafEntry(karafInstance);
		} 
		KarafEntryJaxb karafEntry = pluginModelJaxb.getKarafDataMap().get(karafInstance);
		return karafEntry.getInstalledLicenceList();
	}


	/**
	 * returns the system id of hte karaf instance
	 * @return the systemId
	 */
	public synchronized String getSystemId(String karafInstance) {
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
		refreshKarafEntry(karafInstance);
		KarafEntryJaxb karafEntry = pluginModelJaxb.getKarafDataMap().get(karafInstance);
		
		// TODO CREATE COMMAND
		karafEntry.setSystemId(systemId);
		
	}

	/**
	 * generates and installs a random system id for the karaf instance
	 * @param karafInstance
	 * @return new system id
	 */
	public synchronized String generateRandomSystemId(String karafInstance){
		refreshKarafEntry(karafInstance);
		KarafEntryJaxb karafEntry = pluginModelJaxb.getKarafDataMap().get(karafInstance);
		
		// TODO CREATE COMMAND
		karafEntry.setSystemId("32e396e36b28ef5d-a48ef1cb");
        return karafEntry.getSystemId();
	}

	/**
	 * adds a licence to the karaf instance
	 * @param licenceStr
	 * @param karafInstance
	 */
	public synchronized void addLicence(String licenceStr,String karafInstance){
		refreshKarafEntry(karafInstance);
		KarafEntryJaxb karafEntry = pluginModelJaxb.getKarafDataMap().get(karafInstance);
		
		// TODO CREATE COMMAND
		LicenceMetadata licenceMetadata=null;
		try {
			licenceMetadata = Licence.getUnverifiedMetadata(licenceStr);
		} catch (Exception e) {
			throw new RuntimeException("cannot decode licence string");
		}
		String productId = licenceMetadata.getProductId();
		LicenceEntry le=null;
		for (LicenceEntry licenceEntry : karafEntry.getInstalledLicenceList().getLicenceList()){
			if (licenceEntry.getProductId().equals(productId)){
				le=licenceEntry;
				break;
			};
		}
		if (le!=null) karafEntry.getInstalledLicenceList().getLicenceList().remove(le);

		LicenceEntry licenceEntry = new LicenceEntry();
		licenceEntry.setLicenceStr(licenceStr);
		licenceEntry.setProductId(productId);
		karafEntry.getInstalledLicenceList().getLicenceList().add(licenceEntry);
		
	}

	public void removeLicence(String selectedLicenceId, String karafInstance) {
		refreshKarafEntry(karafInstance);
		KarafEntryJaxb karafEntry = pluginModelJaxb.getKarafDataMap().get(karafInstance);
		
		//TODO REPLACE WITH REAL COMMAND
		LicenceEntry le=null;
		for (LicenceEntry licenceEntry : karafEntry.getInstalledLicenceList().getLicenceList()){
			if (licenceEntry.getProductId().equals(selectedLicenceId)){
				le=licenceEntry;
				break;
			};
		}
		if (le!=null) {
			karafEntry.getInstalledLicenceList().getLicenceList().remove(le);
		} else throw new RuntimeException("licenceId " + selectedLicenceId
				+ " not installed in karaf instance "+ karafInstance);

	}

	/**
	 * installs a plugin for the product id in the selected karaf instance
	 * @param selectedProductId
	 * @param karafInstance
	 */
	public synchronized void installPlugin(String selectedProductId,String karafInstance) {
		
		refreshKarafEntry(karafInstance);
		
		if (! pluginModelJaxb.getKarafDataMap().containsKey(karafInstance)){
			throw new RuntimeException("cannot install plugin "+selectedProductId+" Unknown karaf instance "+karafInstance);
		} 

		throw new RuntimeException("installPlugin plugin model unimplimented method");
		// TODO ADD COMMAND INSTALL PLUGIN
		//refreshKarafEntry(karafInstance);
		

	}

	/**
	 * uninstalls a plugin for the product id in the selected karaf instance
	 * @param selectedProductId
	 * @param karafInstance
	 */
	public synchronized void unInstallPlugin(String selectedProductId,String karafInstance) {
		refreshKarafEntry(karafInstance);
		
		if (! pluginModelJaxb.getKarafDataMap().containsKey(karafInstance)){
			throw new RuntimeException("cannot install plugin "+selectedProductId+" Unknown karaf instance "+karafInstance);
		} 

		throw new RuntimeException("unInstallPlugin plugin model unimplimented method");
		// TODO ADD COMMAND INSTALL PLUGIN
		//refreshKarafEntry(karafInstance);


	}


	public synchronized void persist(){
		if (fileUri==null) throw new RuntimeException("fileUri must be set for plugin manager");

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
	 */
	public synchronized void load(){
		if (fileUri==null) throw new RuntimeException("fileUri must be set for plugin manager");
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
