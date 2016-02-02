/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.tt.client;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tmf.org.dsmapi.tt.TroubleTicket;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientRequest.Builder;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
//import com.sun.jersey.api.client.WebResource.Builder;

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
		webResourceGet.type(MediaType.APPLICATION_JSON_TYPE);
		webResourceGet.accept(MediaType.APPLICATION_JSON_TYPE);
		
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
		//clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			//clientConfig.getClasses().add(JacksonJsonProvider.class);
		//clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
		
		ObjectMapper mapper = new ObjectMapper();
	    AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
	    // make deserializer use JAXB annotations (only)
	    mapper.getDeserializationConfig().setAnnotationIntrospector(introspector);

	    String jsonString =null;
	    try {
			jsonString = mapper.writeValueAsString( tt );
			
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		Client client = Client.create(clientConfig);
		
		WebResource webResourcePost = client.resource(baseUri).path("troubleTicket");
		WebResource.Builder builder = webResourcePost.type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE);
		
		//WebResource.Builder builder = webResourcePost.accept(MediaType.APPLICATION_JSON_TYPE);
		
		//webResourcePost.accept(MediaType.APPLICATION_JSON_TYPE);
		//webResourcePost.type(MediaType.APPLICATION_JSON_TYPE);
		
		//WebResource res = c.resource("http://localhost:5984/");
		
		//System.out.println(builder.get(String.class));
		
		
	
		LOG.debug("calling createTroubleTicket uri="+webResourcePost.getURI().toString());
		LOG.debug("calling json call="+jsonString);

		ClientResponse response = builder.post(ClientResponse.class, jsonString);

		if (response.getStatus() != 201) {
			
			LOG.debug("createTroubleTicket response status= "+response.getStatus()+" status info=" +response.getStatusInfo().toString());
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

//	public void temp(){
//
//		ClientConfig clientConfig = new DefaultClientConfig();
//		//clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//		Client client = Client.create(clientConfig);
//
//		// GET request to findBook resource with a query parameter
//		String getBookURL = "http://env-0693795.jelastic.servint.net/DSTroubleTicket/api/troubleTicketManagement/v2/troubleTicket/2";
//		// WebResource webResourceGet = client.resource(getBookURL).queryParam("id", "1");
//		WebResource webResourceGet = client.resource(getBookURL);
//		//WebResource webResourceGet = client.resource(getBookURL);
//		ClientResponse response = webResourceGet.get(ClientResponse.class);
//		TroubleTicket responseEntity = response.getEntity(TroubleTicket.class);
//
//		if (response.getStatus() != 200) {
//			throw new WebApplicationException();
//		}
//
//		System.out.println(responseEntity.toString());
//	}



}
