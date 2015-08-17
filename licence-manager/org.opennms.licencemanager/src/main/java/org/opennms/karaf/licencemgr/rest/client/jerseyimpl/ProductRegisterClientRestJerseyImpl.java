package org.opennms.karaf.licencemgr.rest.client.jerseyimpl;

import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;
import org.opennms.karaf.licencemgr.rest.client.ProductRegisterClient;

public class ProductRegisterClientRestJerseyImpl implements ProductRegisterClient {
	
	private String baseUrl = "http://localhost:8181";
	private String basePath = "/featuremgr";
	
	/**
	 * base URL of service as http://HOSTNAME:PORT e.g http://localhost:8181
	 * @return baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * base URL of service as http://HOSTNAME:PORT/ e.g http://localhost:8181
	 * @param baseUrl
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * base path of service starting with '/' such that service is accessed using baseUrl/basePath... 
	 * e.g http://localhost:8181/featuremgr
	 * @return basePath
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * base path of service starting with '/' such that service is accessed using baseUrl/basePath... 
	 * e.g http://localhost:8181/featuremgr
	 * @return basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public void addProductSpec(ProductMetadata productMetadata)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProductSpec(String productId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProductMetadata getProductSpec(String productId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductSpecList getList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearProductSpecs(Boolean confirm) throws Exception {
		// TODO Auto-generated method stub
		
	}


}
