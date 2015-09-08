package org.opennms.features.vaadin.config.model;

import java.util.List;

import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;

import com.vaadin.ui.CustomComponent;

public class PluginModel {

	CustomComponent getPluginPanel(){
		
		ProductSpecList psl = null;
		List<ProductMetadata> pmeta = psl.getProductSpecList();
		
		ProductMetadata productMetadata = new ProductMetadata();
		productMetadata.getProductName();
		productMetadata.getProductId();
     	productMetadata.getFeatureRepository();
		productMetadata.getProductDescription();
		productMetadata.getOrganization();
		productMetadata.getProductUrl();
		productMetadata.getLicenceKeyRequired();
		productMetadata.getLicenceType();

		
		
		
		
		
		return null;
	}
	
	
}
