package org.opennms.features.vaadin.config.model;


import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;


public class PluginModel {

	ProductSpecList availablePlugins;
	
	ProductSpecList installedPlugins;

	/**
	 * @return the availablePlugins
	 */
	public ProductSpecList getAvailablePlugins() {
		
		// test code generating a spec list
		ProductSpecList productSpecList = new ProductSpecList();
		
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
			productSpecList.getProductSpecList().add(pmeta);
		}

		availablePlugins= productSpecList;
		return availablePlugins;
	}

	/**
	 * @param availablePlugins the availablePlugins to set
	 */
	public void setAvailablePlugins(ProductSpecList availablePlugins) {
		this.availablePlugins = availablePlugins;
	}

	/**
	 * @return the installedPlugins
	 */
	public ProductSpecList getInstalledPlugins() {
		// test code generating a spec list
		ProductSpecList productSpecList = new ProductSpecList();
		
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
			productSpecList.getProductSpecList().add(pmeta);
		}
		installedPlugins=productSpecList;
		return installedPlugins;
	}

	/**
	 * @param installedPlugins the installedPlugins to set
	 */
	public void setInstalledPlugins(ProductSpecList installedPlugins) {
		this.installedPlugins = installedPlugins;
	}
	
}
