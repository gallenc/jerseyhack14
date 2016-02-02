/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.tt.client;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tmf.org.dsmapi.tt.TroubleTicket;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * Jersey REST client generated for REST resource:TroubleTicketFacadeREST
 */
public class TroubleTicketClientV2 {
	private static final Logger LOG = LoggerFactory.getLogger(TroubleTicketClientV2.class);


	private String baseUri=null;

	public String getBaseUri() {
		return baseUri;
	}

	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}

	/**
	 * REST Call = troubleTicketGet
	 * get trouble ticket by id 
	 * @param id
	 * @return trouble ticket object
	 */
	public TroubleTicket getTroubleTicket(String id){
		if(id==null) throw new RuntimeException("id cannot be null");
		if(baseUri==null) throw new RuntimeException("baseUri cannot be null");
		LOG.debug("calling getTroubleTicket id="+id + " baseUri="+baseUri);

		ClientConfig clientConfig = new DefaultClientConfig();
		Client client = Client.create(clientConfig);
		WebResource webResourceGet = client.resource(baseUri).path("troubleTicket/"+id);
		
		LOG.debug("calling getTroubleTicket uri="+webResourceGet.getURI().toString());
		
		ClientResponse response = webResourceGet.get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			LOG.debug("getTroubleTicket response status= "+response.getStatus());
			return null;
		}
		
		TroubleTicket responseEntity = response.getEntity(TroubleTicket.class);

		LOG.debug("getTroubleTicket response="+responseEntity.toString());
		return responseEntity;
	}
	
	
	public TroubleTicket createTroubleTicket(TroubleTicket tt){
		if(tt==null) throw new RuntimeException("trouble ticket cannot be null");
		if(baseUri==null) throw new RuntimeException("baseUri cannot be null");
		LOG.debug("calling createTroubleTicket tt="+tt.toString()+ " baseUri="+baseUri);

		ClientConfig clientConfig = new DefaultClientConfig();
		Client client = Client.create(clientConfig);

		WebResource webResourcePost = client.resource(baseUri).path("troubleTicket");
		webResourcePost.type(MediaType.APPLICATION_JSON_TYPE);
		webResourcePost.accept(MediaType.APPLICATION_JSON_TYPE);
	
		LOG.debug("calling createTroubleTicket uri="+webResourcePost.getURI().toString());

		ClientResponse response = webResourcePost.post(ClientResponse.class, tt);

		if (response.getStatus() != 200) {
			LOG.debug("createTroubleTicket response status= "+response.getStatus());
			return null;
		}
		
		TroubleTicket responseEntity = response.getEntity(TroubleTicket.class);

		LOG.debug("createTroubleTicket response="+responseEntity.toString());
		return responseEntity;
	}


	//    troubleTicketUpdate
	//    troubleTicketPatch

	//    troubleTicketCreate
	//    troubleTicketFind

	public void temp(){

		ClientConfig clientConfig = new DefaultClientConfig();
		//clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);

		// GET request to findBook resource with a query parameter
		String getBookURL = "http://env-0693795.jelastic.servint.net/DSTroubleTicket/api/troubleTicketManagement/v2/troubleTicket/2";
		// WebResource webResourceGet = client.resource(getBookURL).queryParam("id", "1");
		WebResource webResourceGet = client.resource(getBookURL);
		//WebResource webResourceGet = client.resource(getBookURL);
		ClientResponse response = webResourceGet.get(ClientResponse.class);
		TroubleTicket responseEntity = response.getEntity(TroubleTicket.class);

		if (response.getStatus() != 200) {
			throw new WebApplicationException();
		}

		System.out.println(responseEntity.toString());
	}



}
