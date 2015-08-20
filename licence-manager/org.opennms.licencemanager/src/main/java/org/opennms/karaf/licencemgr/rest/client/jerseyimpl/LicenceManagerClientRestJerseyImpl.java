package org.opennms.karaf.licencemgr.rest.client.jerseyimpl;

import javax.ws.rs.core.MediaType;

import org.opennms.karaf.licencemgr.metadata.jaxb.ErrorMessage;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecification;
import org.opennms.karaf.licencemgr.metadata.jaxb.ReplyMessage;
import org.opennms.karaf.licencemgr.rest.client.LicenceManagerClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

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
	public LicenceMetadata addLicence(String licence) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(licence==null ) throw new RuntimeException("licence must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-mgr/addlicence?licence=
		
		String getStr= baseUrl+basePath+"/addlicence?licence="+licence;
		
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
        //success !
        ReplyMessage replyMessage = response.getEntity(ReplyMessage.class);
        
        LicenceMetadata  licenceMetadata =  replyMessage.getLicenceMetadata();

        return  licenceMetadata;
	}

	@Override
	public void removeLicence(String productId) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(productId==null ) throw new RuntimeException("productId must be set");

		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-mgr/removelicence?productId=
		
		String getStr= baseUrl+basePath+"/removelicence?productId="+productId;
		
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
        	String errMsg= "removeLicence Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
        // success !
	}

	@Override
	public String getLicence(String productId) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(productId==null ) throw new RuntimeException("productId must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-mgr/getlicence?productId=
		
		String getStr= baseUrl+basePath+"/getlicence?productId="+productId;
		
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
        	String errMsg= "getLicence Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
		
        ReplyMessage replyMessage = response.getEntity(ReplyMessage.class);
        
        String  licence =  replyMessage.getLicence();

        return licence;
	}

	@Override
	public LicenceList getLicenceMap() throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-mgr/list
		
		String getStr= baseUrl+basePath+"/list";
		
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
        	String errMsg= "getLicenceMap Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
		// success !
        LicenceList licenceListResponse = response.getEntity(LicenceList.class);

        return licenceListResponse;
	}

	@Override
	public void deleteLicences(Boolean confirm) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(confirm==null) throw new RuntimeException("confirm must be set true of false");

		Client client = Client.create();

		//http://localhost:8181/pluginmgr/rest/licence-mgr/clearlicences?confirm=false
		
		String getStr= baseUrl+basePath+"/clearlicences?confirm="+ (confirm ? "true":"false");
		
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
        	String errMsg= "removeLicence Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
        // success !
	}

	@Override
	public String getSystemId() throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-mgr/getsystemid
		
		String getStr= baseUrl+basePath+"/getsystemid";
		
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
        	String errMsg= "getLicence Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
		
        ReplyMessage replyMessage = response.getEntity(ReplyMessage.class);
        
        String  systemid = replyMessage.getSystemId();

        return systemid;
	}

	@Override
	public void setSystemId(String systemId) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(systemId==null ) throw new RuntimeException("systemId must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-mgr/setsystemid?systemId=
		
		String getStr= baseUrl+basePath+"/setsystemid?systemId="+systemId;
		
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
        	String errMsg= "setSystemId Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
        // success !
	}

	@Override
	public String makeSystemInstance() throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-mgr/makesystemid
		
		String getStr= baseUrl+basePath+"/makesystemid";
		
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
        	String errMsg= "makeSystemInstance Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
		
        ReplyMessage replyMessage = response.getEntity(ReplyMessage.class);
        
        String  systemid = replyMessage.getSystemId();

        return systemid;
	}

	@Override
	public String checksumForString(String string) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(string==null ) throw new RuntimeException("string must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/licence-mgr/checksumforstring?string=
		
		String getStr= baseUrl+basePath+"/checksumforstring?string="+string;
		
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
        	String errMsg= "checksumForString Failed : HTTP error code : "+ response.getStatus();
        	if (errorMessage!=null){
        		errMsg=errMsg+" message:"+ errorMessage.getMessage()
					+" code:"+ errorMessage.getCode()
					+" developer message:"+errorMessage.getDeveloperMessage();
        	}
            throw new RuntimeException(errMsg);
        }
		//success !
        ReplyMessage replyMessage = response.getEntity(ReplyMessage.class);
        
        String  checksum =  replyMessage.getChecksum();

        return checksum;
	}


}
