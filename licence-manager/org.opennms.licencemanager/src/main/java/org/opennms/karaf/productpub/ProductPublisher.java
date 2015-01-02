package org.opennms.karaf.productpub;

import java.util.Map;

import org.opennms.karaf.licencemgr.metadata.ProductDescription;


public interface ProductPublisher {

	public void addProductDescription(ProductDescription productDescription);
	
	public boolean removeProductDescription(String productId);

	public ProductDescription getProductDescription(String productId);

	public Map<String, ProductDescription> getProductDescriptionMap();
	
	public void deleteProductDescriptions();
}
