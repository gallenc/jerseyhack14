package org.opennms.karaf.productpub;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.opennms.karaf.licencemgr.metadata.ProductMetadata;

public class ProductPublisherImpl implements ProductPublisher{

	private SortedMap<String, ProductMetadata> productSpecMap = new TreeMap<String, ProductMetadata>();
		
	@Override
	public void addProductDescription(ProductMetadata productMetadata) {
		if (productMetadata==null) throw new IllegalArgumentException("productMetadata cannot be null");
		productSpecMap.put(productMetadata.getProductId(), productMetadata);
	}

	@Override
	public boolean removeProductDescription(String productId) {
		if (productId==null) throw new IllegalArgumentException("productId cannot be null");
		if (! productSpecMap.containsKey(productId)) {
			return false;
		} else{
			productSpecMap.remove(productId);
			return true;
		}
	}

	@Override
	public ProductMetadata getProductDescription(String productId) {
		if (productId==null) throw new IllegalArgumentException("productId cannot be null");
		return productSpecMap.get(productId);
	}

	@Override
	public Map<String, ProductMetadata> getProductDescriptionMap() {
		return new TreeMap<String, ProductMetadata>(productSpecMap);
	}

	@Override
	public void deleteProductDescriptions() {
		productSpecMap.clear();		
	}

}
