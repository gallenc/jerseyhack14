package org.opennms.karaf.productpub;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.opennms.karaf.licencemgr.metadata.ProductDescription;

public class ProductPublisherImpl implements ProductPublisher{

	private SortedMap<String, ProductDescription> productSpecMap = new TreeMap<String, ProductDescription>();
		
	@Override
	public void addProductDescription(ProductDescription productDescription) {
		if (productDescription==null) throw new IllegalArgumentException("productMetadata cannot be null");
		productSpecMap.put(productDescription.getProductId(), productDescription);
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
	public ProductDescription getProductDescription(String productId) {
		if (productId==null) throw new IllegalArgumentException("productId cannot be null");
		return productSpecMap.get(productId);
	}

	@Override
	public Map<String, ProductDescription> getProductDescriptionMap() {
		return new TreeMap<String, ProductDescription>(productSpecMap);
	}

	@Override
	public void deleteProductDescriptions() {
		productSpecMap.clear();		
	}

}
