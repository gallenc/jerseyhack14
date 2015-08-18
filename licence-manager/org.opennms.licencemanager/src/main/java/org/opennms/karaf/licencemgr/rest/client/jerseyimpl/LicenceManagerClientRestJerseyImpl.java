package org.opennms.karaf.licencemgr.rest.client.jerseyimpl;

import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.rest.client.LicenceManagerClient;

public class LicenceManagerClientRestJerseyImpl implements LicenceManagerClient {
	
	private String baseUrl = "http://localhost:8181";
	private String basePath = "/pluginmgr/rest/licence-mgr";
	
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
	public void addLicence(String licence) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLicence(String productId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLicence(String productId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LicenceList getLicenceMap() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLicences(String confirm) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSystemId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSystemId(String systemId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String makeSystemInstance() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checksumForString(String string) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
