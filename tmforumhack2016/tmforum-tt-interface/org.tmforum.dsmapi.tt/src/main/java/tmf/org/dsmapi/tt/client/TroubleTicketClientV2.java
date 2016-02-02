package tmf.org.dsmapi.tt.client;

import java.io.IOException;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmf.org.dsmapi.tt.TroubleTicket;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

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
		
		LOG.debug("calling createTroubleTicket baseUri="+baseUri+"\n"
				+ "trouble ticket="+tt.toString()+ "");

		ClientConfig clientConfig = new DefaultClientConfig();
		
		// only create json string if debugging
		if (LOG.isDebugEnabled()){
			ObjectMapper mapper = new ObjectMapper();
		    AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
		    // make deserializer use JAXB annotations (only)
		    mapper.getDeserializationConfig().withAnnotationIntrospector(introspector);
		    String jsonString =null;
				try {
					jsonString = mapper.writeValueAsString( tt );
				} catch (IOException e) {
					LOG.debug("problem logging jsonString",e);
				}
			LOG.debug("calling jsonString="+jsonString);
		}

		Client client = Client.create(clientConfig);
		
		WebResource webResourcePost = client.resource(baseUri).path("troubleTicket");
		LOG.debug("calling createTroubleTicket uri="+webResourcePost.getURI().toString());
		
		WebResource.Builder builder = webResourcePost.type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE);
		ClientResponse response = builder.post(ClientResponse.class, tt);

		if (response.getStatus() != 201) {
			LOG.debug("createTroubleTicket response status= "+response.getStatus()+" status info=" +response.getStatusInfo().toString());
			return null;
		}
		
		TroubleTicket responseEntity = response.getEntity(TroubleTicket.class);

		LOG.debug("createTroubleTicket response="+responseEntity.toString());
		return responseEntity;
	}


	/**
	 * troubleTicketUpdate
	 * @param tt
	 * @return
	 */
	public TroubleTicket updateTroubleTicket(TroubleTicket tt){
		throw new UnsupportedOperationException("this operation is not yet implemented");
		//return null;
	}
	
	/**
	 * troubleTicketPatch
	 * @param tt
	 * @return
	 */
	public TroubleTicket patchTroubleTicket(TroubleTicket tt){
		throw new UnsupportedOperationException("this operation is not yet implemented");
		//return null;
	}
	
/**
 *  troubleTicketFind
 * @param tt
 * @return
 */
	public TroubleTicket findTroubleTicket(TroubleTicket tt){
		throw new UnsupportedOperationException("this operation is not yet implemented");
		//return null;
	}




}
