package org.opennms.karaf.licencemgr.rest.client.jerseyimpl;

import javax.ws.rs.core.MediaType;

import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadataList;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecList;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecification;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;
import org.opennms.karaf.licencemgr.metadata.jaxb.Util;
import org.opennms.karaf.licencemgr.rest.client.LicencePublisherClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class LicencePublisherClientRestJerseyImpl implements LicencePublisherClient {
	
	private String baseUrl = "http://localhost:8181";
	private String basePath = "/pluginmgr/rest/licence-pub";
	
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
	public void addLicenceSpec(LicenceSpecification licenceSpec)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLicenceSpec(String productId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LicenceSpecification getLicenceSpec(String productId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LicenceMetadata getLicenceMetadata(String productId) throws Exception {

return null;
	}

	@Override
	public LicenceSpecList getLicenceSpecList() throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");

		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-pub/listspecs
		
		String getStr= baseUrl+basePath+"/listspecs";
		
		WebResource r = client.resource(getStr);

		String replyString= r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(String.class);
		
		// unmarshalling reply
		LicenceSpecList licenceSpecList=null;
		Object replyObject = Util.fromXml(replyString);
		if (replyObject instanceof LicenceSpecList){
			licenceSpecList= (LicenceSpecList) replyObject;
		} else {
			throw new RuntimeException("received unexpected reply object: "+replyObject.getClass().getCanonicalName());
		}
		
		// success !!!
		return licenceSpecList;
	}

	@Override
	public LicenceMetadataList getLicenceMetadataList() throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");

		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-pub/list
		
		String getStr= baseUrl+basePath+"/list";
		
		WebResource r = client.resource(getStr);

		String replyString= r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(String.class);
		
		// unmarshalling reply
		LicenceMetadataList licenceMetadataSpecList=null;
		Object replyObject = Util.fromXml(replyString);
		if (replyObject instanceof LicenceMetadataList){
			licenceMetadataSpecList= (LicenceMetadataList) replyObject;
		} else {
			throw new RuntimeException("received unexpected reply object: "+replyObject.getClass().getCanonicalName());
		}
		
		// success !!!
		return licenceMetadataSpecList;
	}

	@Override
	public void deleteLicenceSpecifications(String confirm) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createLicenceInstanceStr(LicenceMetadata licenceMetadata) {
		// TODO Auto-generated method stub
		return null;
	}



}
