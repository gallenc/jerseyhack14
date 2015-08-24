package org.opennms.karaf.licencemgr.rest.client.jerseyimpl;

import javax.ws.rs.core.MediaType;

import org.opennms.karaf.licencemgr.metadata.jaxb.ErrorMessage;
import org.opennms.karaf.licencemgr.metadata.jaxb.ReplyMessage;
import org.opennms.karaf.licencemgr.metadata.jaxb.Util;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;
import org.opennms.karaf.licencemgr.rest.client.ProductRegisterClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ProductRegisterClientRestJerseyImpl implements ProductRegisterClient {
	
	private String baseUrl = "http://localhost:8181";
	private String basePath = "/pluginmgr/rest/product-reg";
	
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
	 * e.g http://localhost:8181/pluginmgr/rest/product-pub
	 * @return basePath
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * base path of service starting with '/' such that service is accessed using baseUrl/basePath... 
	 * e.g http://localhost:8181/pluginmgr/rest/product-pub
	 * @return basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	
	@Override
	public void addProductSpec(ProductMetadata productMetadata) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(productMetadata==null ) throw new RuntimeException("productMetadata must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/product-pub/addproductspec
		
		String getStr= baseUrl+basePath+"/addproductspec";
		
		WebResource r = client.resource(getStr);
		
		// POST method
		ClientResponse response = r.accept(MediaType.APPLICATION_XML)
                .type(MediaType.APPLICATION_XML).post(ClientResponse.class, productMetadata);

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
	public void removeProductSpec(String productId) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(productId==null ) throw new RuntimeException("productId must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/product-pub/removeproductspec?productId=
		
		String getStr= baseUrl+basePath+"/removeproductspec?productId="+productId;
		
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
	public ProductMetadata getProductSpec(String productId) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(productId==null ) throw new RuntimeException("productId must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/product-pub/getproductspec?productId=
		
		String getStr= baseUrl+basePath+"/getproductspec?productId="+productId;
		
		WebResource r = client.resource(getStr);

		ReplyMessage replyMessage= r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(ReplyMessage.class);
		
		if(replyMessage.getProductMetadata()==null) {
			throw new RuntimeException("unable to get product metadata for productId="+productId
					+ " replyMessage.getReplyComment()="+replyMessage.getReplyComment());
		}
		else return replyMessage.getProductMetadata(); // success !!!
		
	}

	@Override
	public ProductSpecList getList() throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");

		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/product-pub/list
		
		String getStr= baseUrl+basePath+"/list";
		
		WebResource r = client.resource(getStr);

		String replyString= r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(String.class);
		
		// unmarshalling reply
		ProductSpecList productSpecList=null;
		Object replyObject = Util.fromXml(replyString);
		if (replyObject instanceof ProductSpecList){
			productSpecList= (ProductSpecList) replyObject;
		} else {
			throw new RuntimeException("received unexpected reply object: "+replyObject.getClass().getCanonicalName());
		}
		
		// success !!!
		return productSpecList;

	}

	@Override
	public void clearProductSpecs(Boolean confirm) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(confirm==null) throw new RuntimeException("confirm must be set true of false");

		Client client = Client.create();
		
		//http://localhost:8181/pluginmgr/rest/product-pub/clearproductspecs?confirm=false
		
		String getStr= baseUrl+basePath+"/clearproductspecs?confirm="+ (confirm ? "true":"false");
		
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
	
}
