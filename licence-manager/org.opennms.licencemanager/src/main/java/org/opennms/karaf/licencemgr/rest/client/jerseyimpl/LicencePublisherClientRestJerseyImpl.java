package org.opennms.karaf.licencemgr.rest.client.jerseyimpl;

import javax.ws.rs.core.MediaType;

import org.opennms.karaf.licencemgr.metadata.jaxb.ErrorMessage;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadataList;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecList;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecification;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;
import org.opennms.karaf.licencemgr.metadata.jaxb.ReplyMessage;
import org.opennms.karaf.licencemgr.metadata.jaxb.Util;
import org.opennms.karaf.licencemgr.rest.client.LicencePublisherClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
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
	public void addLicenceSpec(LicenceSpecification licenceSpec) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(licenceSpec==null ) throw new RuntimeException("licenceSpec must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-pub/addlicencespec
		
		String getStr= baseUrl+basePath+"/addlicencespec";
		
		WebResource r = client.resource(getStr);
		
		// POST method
		ClientResponse response = r.accept(MediaType.APPLICATION_XML)
                .type(MediaType.APPLICATION_XML).post(ClientResponse.class, licenceSpec);

        // check response status code and reply error message
        if (response.getStatus() != 200) {
        	ErrorMessage errorMessage=null;
        	try {
        		errorMessage = response.getEntity(ErrorMessage.class);
        	} catch (Exception e) {
        	}
        	String errMsg= "Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
		
        // success !!!
	}

	@Override
	public void removeLicenceSpec(String productId) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(productId==null ) throw new RuntimeException("productId must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-pub/removelicencespec?productId=
		
		String getStr= baseUrl+basePath+"/removelicencespec?productId="+productId;
		
		WebResource r = client.resource(getStr);

		// GET method
		ClientResponse response = r.accept(MediaType.APPLICATION_XML)
                .type(MediaType.APPLICATION_FORM_URLENCODED).get(ClientResponse.class);

        // check response status code and reply error message
        if (response.getStatus() != 200) {
        	ErrorMessage errorMessage=null;
        	try {
        		errorMessage = response.getEntity(ErrorMessage.class);
        	} catch (Exception e) {
        	}
        	String errMsg= "removeProductSpec Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
		
        // success !!!

	}

	@Override
	public LicenceSpecification getLicenceSpec(String productId) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(productId==null ) throw new RuntimeException("productId must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-pub/getlicencespec?productId=
		
		String getStr= baseUrl+basePath+"/getlicencespec?productId="+productId;
		
		WebResource r = client.resource(getStr);

		// GET method
		ClientResponse response = r.accept(MediaType.APPLICATION_XML)
                .type(MediaType.APPLICATION_FORM_URLENCODED).get(ClientResponse.class);

        // check response status code and reply error message
        if (response.getStatus() != 200) {
        	ErrorMessage errorMessage=null;
        	try {
        		errorMessage = response.getEntity(ErrorMessage.class);
        	} catch (Exception e) {
        	}
        	String errMsg= "getlicencespec Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
        
        ReplyMessage replyMessage= response.getEntity(ReplyMessage.class);
        
        LicenceSpecification  licenceSpecification= replyMessage.getLicenceSpecification();
        
        return licenceSpecification;

	}

	@Override
	public LicenceMetadata getLicenceMetadata(String productId) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(productId==null ) throw new RuntimeException("productId must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-pub/getlicencemetadataspec?productId=
		
		String getStr= baseUrl+basePath+"/getlicencemetadataspec?productId="+productId;
		
		WebResource r = client.resource(getStr);

		// GET method
		ClientResponse response = r.accept(MediaType.APPLICATION_XML)
                .type(MediaType.APPLICATION_FORM_URLENCODED).get(ClientResponse.class);

        // check response status code and reply error message
        if (response.getStatus() != 200) {
        	ErrorMessage errorMessage=null;
        	try {
        		errorMessage = response.getEntity(ErrorMessage.class);
        	} catch (Exception e) {
        	}
        	String errMsg= "getlicencespec Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
		
        ReplyMessage replyMessage = response.getEntity(ReplyMessage.class);
        
        LicenceMetadata  licenceMetadata =  replyMessage.getLicenceMetadataSpec();

        return licenceMetadata;
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
	public void deleteLicenceSpecifications(Boolean confirm) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(confirm==null) throw new RuntimeException("confirm must be set true of false");

		Client client = Client.create();
		
		////http://localhost:8181/pluginmgr/rest/licence-pub/clearlicencespecs?confirm=false
		
		String getStr= baseUrl+basePath+"/clearlicencespecs?confirm="+ (confirm ? "true":"false");
		
		WebResource r = client.resource(getStr);

		// GET method
		ClientResponse response = r.accept(MediaType.APPLICATION_XML)
                .type(MediaType.APPLICATION_FORM_URLENCODED).get(ClientResponse.class);

        // check response status code and reply error message
        if (response.getStatus() != 200) {
        	ErrorMessage errorMessage=null;
        	try {
        		errorMessage = response.getEntity(ErrorMessage.class);
        	} catch (Exception e) {
        	}
        	String errMsg= "clearProductSpecs Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
        // success !!!
	}

	@Override
	public String createLicenceInstanceStr(LicenceMetadata licenceMetadata) {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(licenceMetadata==null ) throw new RuntimeException("licenceMetadata must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-pub/createlicence
		
		String getStr= baseUrl+basePath+"/createlicence";
		
		WebResource r = client.resource(getStr);
		
		// POST method
		ClientResponse response = r.accept(MediaType.APPLICATION_XML)
                .type(MediaType.APPLICATION_XML).post(ClientResponse.class, licenceMetadata);

        // check response status code and reply error message
        if (response.getStatus() != 200) {
        	ErrorMessage errorMessage=null;
        	try {
        		errorMessage = response.getEntity(ErrorMessage.class);
        	} catch (Exception e) {
        	}
        	String errMsg= "Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
        // success !!!
        
        ReplyMessage replyMessage = response.getEntity(ReplyMessage.class);
        
        String licenceStr =  replyMessage.getLicence();

        return licenceStr;
        
	}



}
