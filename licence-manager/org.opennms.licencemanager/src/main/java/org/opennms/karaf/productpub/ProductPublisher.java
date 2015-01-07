package org.opennms.karaf.productpub;

import java.util.Map;

import org.opennms.karaf.licencemgr.metadata.ProductMetadata;


public interface ProductPublisher {

	public void addProductDescription(ProductMetadata productMetadata);
	
	public boolean removeProductDescription(String productId);

	public ProductMetadata getProductDescription(String productId);

	public Map<String, ProductMetadata> getProductDescriptionMap();
	
	public void deleteProductDescriptions();
}
