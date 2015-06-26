package org.opennms.features.eventgateway.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.opennms.features.eventgateway.AutoAuthKeyGenerator;
import org.opennms.features.eventgateway.ConfigLoader;
import org.opennms.features.eventgateway.ConfigLoader.EventGatewayConfigObject;
import org.opennms.features.eventgateway.jaxb.EventGatewayErrorMessage;
import org.opennms.features.eventgateway.jaxb.NameValuePair;
import org.opennms.features.eventgateway.jaxb.RestEventReplyMessage;

/**
 * ReST interface to access the authentication key interfaces for event gateway
 * 
 * Note that in all cases, only gateways configured to have an auto-authentication athKey
 * will have information accessible through this interface
 * 
 * Create a new Authorization key. 
 * Create a new AuthKey for supplied gateway reference . 
 * This becomes the current AuthKey and the previous authKey remains in the cache
 * until it times out.
 * /eventgateway/authkey/newAuthKey?reference=xxxx
 * 
 * Get the current Authorisation key
 * Returns the latest generated authorisation key
 * <opennmshome>/eventgateway/authkey/currentAuthKey?reference=xxxx
 * 
 * Get the maximum age of a current authKey before it is replaced with a new current key (in milliseconds)
 * <opennmshome>/eventgateway/authkey/maxCurrentAuthKeyAge?reference=xxxx
 * 
 * Get the maximum age of an authKey before it is deleted from the cache (in milliseconds)
 * <opennmshome>/eventgateway/authkey/maxTimeKeepOldAuthKeys?reference=xxxx
 * 
 * Get Valid Authkeys
 * Get a list of valid authorisation keys in the cache with their time of creation
 * <opennmshome>/eventgateway/authkey/validAuthKeys?reference=xxxx
 * 
 * Reset the authKey cache and create a new current key.
 * <opennmshome>/eventgateway/authkey/resetAuthKeys?reference=xxxx
 * 
 * @author Craig Gallen cgallen@opennms.org
 *
 */

@Path("/authkey")
public class EventGatewayAuthKeyRest {

	@GET
	@Path("/authenticate")
	@Produces(MediaType.APPLICATION_XML)
	public Response authenticateAuthKey( @QueryParam("reference") String reference, @QueryParam("authKey") String authKey ){

		// validate input
		if(reference==null){
			return Response
					.status(400)
					.entity(new EventGatewayErrorMessage(400, -1, "you have not supplied a gateway reference. Use form /eventgateway/authkey/authenticate?reference=xxxx&authKey=yyyy"  , "", "")).build();
		}
		if(authKey==null){
			return Response
					.status(400)
					.entity(new EventGatewayErrorMessage(400, -1, "you have not supplied a key to authenticate for reference="+reference+". Use form /eventgateway/authkey/authenticate?reference=xxxx&authKey=yyyy"  , "", "")
							).build();
		}

		// get event gateway configuration and authenticators
		EventGatewayConfigObject gatewayConfigObject = ConfigLoader.getGatewayConfigObject();
		if (gatewayConfigObject==null){
			throw new RuntimeException("ConfigLoader.getGatewayConfigObject() cannot be null. Check event gateway configuration file.");
		}
		HashMap<String, AutoAuthKeyGenerator> authenticatorMap = gatewayConfigObject.getAuthenticatorMap();
		if (authenticatorMap==null){
			throw new RuntimeException("ConfigLoader.getConfigMap() cannot be null. Check event gateway configuration file");
		}

		// find autoAuthKeyGenerator for gateway reference
		AutoAuthKeyGenerator autoAuthKeyGenerator = authenticatorMap.get(reference);

		if(autoAuthKeyGenerator==null){
			return Response
					.status(404)
					.entity(new EventGatewayErrorMessage(404, -1, "there is no authenticator defined for event gateway reference=" + reference , "", "")
							).build();
		}

		RestEventReplyMessage reply = new RestEventReplyMessage();
		
		NameValuePair nvpReference = new NameValuePair();
		nvpReference.setName("reference");
		nvpReference.setValue(reference);
		reply.getReplyValues().add(nvpReference);
		
		NameValuePair nvpAuthkey = new NameValuePair();
		nvpAuthkey.setName("authKey");
		nvpAuthkey.setValue(authKey);
		reply.getReplyValues().add(nvpAuthkey);

		NameValuePair nvpValidAuthKey = new NameValuePair();
		nvpValidAuthKey.setName("keyValid");
		nvpValidAuthKey.setValue(Boolean.toString(autoAuthKeyGenerator.authenticateAuthKey(authKey)));
		reply.getReplyValues().add(nvpValidAuthKey);

		return Response
				.status(200)
				.entity(reply).build();

	}

	@GET
	@Path("/newAuthKey")
	@Produces(MediaType.APPLICATION_XML)
	public Response getNewAuthKey(@QueryParam("reference") String reference){

		// validate input
		if(reference==null){
			return Response
					.status(400)
					.entity(new EventGatewayErrorMessage(400, -1, "you have not supplied a gateway reference. Use form /eventgateway/authkey/newAuthKey?reference=xxxx"   , "", "")
							).build();
		}

		// get event gateway configuration and authenticators
		EventGatewayConfigObject gatewayConfigObject = ConfigLoader.getGatewayConfigObject();
		if (gatewayConfigObject==null){
			throw new RuntimeException("ConfigLoader.getGatewayConfigObject() cannot be null. Check event gateway configuration file.");
		}
		HashMap<String, AutoAuthKeyGenerator> authenticatorMap = gatewayConfigObject.getAuthenticatorMap();
		if (authenticatorMap==null){
			throw new RuntimeException("ConfigLoader.getConfigMap() cannot be null. Check event gateway configuration file");
		}

		// find autoAuthKeyGenerator for gateway reference
		AutoAuthKeyGenerator autoAuthKeyGenerator = authenticatorMap.get(reference);

		if(autoAuthKeyGenerator==null){
			return Response
					.status(404)
					.entity(new EventGatewayErrorMessage(404, -1, "there is no authenticator defined for event gateway reference=" + reference , "", "")
							).build();
		}

		String newAuthkey=autoAuthKeyGenerator.createNewAuthKey();
		
		RestEventReplyMessage reply= new RestEventReplyMessage();

		NameValuePair nvpReference = new NameValuePair();
		nvpReference.setName("reference");
		nvpReference.setValue(reference);
		reply.getReplyValues().add(nvpReference);
		
		NameValuePair nvp = new NameValuePair();
		nvp.setName("newAuthKey");
		nvp.setValue(newAuthkey);
		reply.getReplyValues().add(nvp);
		
		return Response
				.status(200)
				.entity(reply).build();

	}


	@GET
	@Path("/currentAuthKey")
	@Produces(MediaType.APPLICATION_XML)
	public Response getCurrentAuthKey(@QueryParam("reference") String reference){

		// validate input
		if(reference==null){
			return Response
					.status(400)
					.entity(new EventGatewayErrorMessage(400, -1, "you have not supplied a gateway reference. Use form /eventgateway/authkey/currentAuthKey?reference=xxxx"  , "", "")
							).build();
		}

		// get event gateway configuration and authenticators
		EventGatewayConfigObject gatewayConfigObject = ConfigLoader.getGatewayConfigObject();
		if (gatewayConfigObject==null){
			throw new RuntimeException("ConfigLoader.getGatewayConfigObject() cannot be null. Check event gateway configuration file.");
		}
		HashMap<String, AutoAuthKeyGenerator> authenticatorMap = gatewayConfigObject.getAuthenticatorMap();
		if (authenticatorMap==null){
			throw new RuntimeException("ConfigLoader.getConfigMap() cannot be null. Check event gateway configuration file");
		}

		// find autoAuthKeyGenerator for gateway reference
		AutoAuthKeyGenerator autoAuthKeyGenerator = authenticatorMap.get(reference);

		if(autoAuthKeyGenerator==null){
			return Response
					.status(404)
					.entity(new EventGatewayErrorMessage(404, -1, "there is no authenticator defined for event gateway reference=" + reference  , "", "")
							).build();
		}

		String currentAuthkey=autoAuthKeyGenerator.getCurrentAuthKey();
		
		RestEventReplyMessage reply= new RestEventReplyMessage();
		
		NameValuePair nvpReference = new NameValuePair();
		nvpReference.setName("reference");
		nvpReference.setValue(reference);
		reply.getReplyValues().add(nvpReference);
		
		NameValuePair nvp = new NameValuePair();
		nvp.setName("currentAuthKey");
		nvp.setValue(currentAuthkey);
		reply.getReplyValues().add(nvp);
		
		return Response
				.status(200)
				.entity(reply).build();

	}

	@GET
	@Path("/maxCurrentAuthKeyAge")
	@Produces(MediaType.APPLICATION_XML)
	public Response getMaxAuthKeyLife(@QueryParam("reference") String reference){

		// validate input
		if(reference==null){
			return Response
					.status(400)
					.entity(new EventGatewayErrorMessage(400, -1, "you have not supplied a gateway reference. Use form /eventgateway/authkey/maxCurrentAuthKeyAge?reference=xxxx" , "", "")).build();
		}


		// get event gateway configuration and authenticators
		EventGatewayConfigObject gatewayConfigObject = ConfigLoader.getGatewayConfigObject();
		if (gatewayConfigObject==null){
			throw new RuntimeException("ConfigLoader.getGatewayConfigObject() cannot be null. Check event gateway configuration file.");
		}
		HashMap<String, AutoAuthKeyGenerator> authenticatorMap = gatewayConfigObject.getAuthenticatorMap();
		if (authenticatorMap==null){
			throw new RuntimeException("ConfigLoader.getConfigMap() cannot be null. Check event gateway configuration file");
		}

		// find autoAuthKeyGenerator for gateway reference
		AutoAuthKeyGenerator autoAuthKeyGenerator = authenticatorMap.get(reference);

		if(autoAuthKeyGenerator==null){
			return Response
					.status(404)
					.entity(new EventGatewayErrorMessage(404, -1, "there is no authenticator defined for event gateway reference=" + reference , "", "")).build();
		}

		long maxCurrentAuthKeyAge=autoAuthKeyGenerator.getMaxCurrentAuthKeyAge();
		
		RestEventReplyMessage reply= new RestEventReplyMessage();
		
		NameValuePair nvpReference = new NameValuePair();
		nvpReference.setName("reference");
		nvpReference.setValue(reference);
		reply.getReplyValues().add(nvpReference);
		
		NameValuePair nvp = new NameValuePair();
		nvp.setName("maxCurrentAuthKeyAge");
		nvp.setValue(Long.toString(maxCurrentAuthKeyAge));
		reply.getReplyValues().add(nvp);
		
		return Response
				.status(200)
				.entity(reply).build();

	}

	@GET
	@Path("/maxTimeKeepOldAuthKeys")
	@Produces(MediaType.APPLICATION_XML)
	public Response getMaxCurrentLife(@QueryParam("reference") String reference){

		// validate input
		if(reference==null){
			return Response
					.status(400)
					.entity(new EventGatewayErrorMessage(400, -1, "you have not supplied a gateway reference. Use form /eventgateway/authkey/maxTimeKeepOldAuthKeys?reference=xxxx" , "", "")
							).build();
		}

		// get event gateway configuration and authenticators
		EventGatewayConfigObject gatewayConfigObject = ConfigLoader.getGatewayConfigObject();
		if (gatewayConfigObject==null){
			throw new RuntimeException("ConfigLoader.getGatewayConfigObject() cannot be null. Check event gateway configuration file.");
		}
		HashMap<String, AutoAuthKeyGenerator> authenticatorMap = gatewayConfigObject.getAuthenticatorMap();
		if (authenticatorMap==null){
			throw new RuntimeException("ConfigLoader.getConfigMap() cannot be null. Check event gateway configuration file");
		}

		// find autoAuthKeyGenerator for gateway reference
		AutoAuthKeyGenerator autoAuthKeyGenerator = authenticatorMap.get(reference);

		if(autoAuthKeyGenerator==null){
			return Response
					.status(404)
					.entity(new EventGatewayErrorMessage(400, -1, "there is no authenticator defined for event gateway reference=" + reference  , "", "")
							).build();
		}

		long maxTimeKeepOldAuthKeys=autoAuthKeyGenerator.getMaxTimeKeepOldAuthKeys();
		
		RestEventReplyMessage reply= new RestEventReplyMessage();
		
		NameValuePair nvpReference = new NameValuePair();
		nvpReference.setName("reference");
		nvpReference.setValue(reference);
		reply.getReplyValues().add(nvpReference);
		
		NameValuePair nvp = new NameValuePair();
		nvp.setName("maxTimeKeepOldAuthKeys");
		nvp.setValue(Long.toString(maxTimeKeepOldAuthKeys));
		reply.getReplyValues().add(nvp);
		
		return Response
				.status(200)
				.entity(reply).build();

	}

	@GET
	@Path("/validAuthKeys")
	@Produces(MediaType.APPLICATION_XML)
	public Response getAuthKeys(@QueryParam("reference") String reference){
		// validate input
		if(reference==null){
			return Response
					.status(400)
					.entity(new EventGatewayErrorMessage(400, -1, "you have not supplied a gateway reference. Use form /eventgateway/authkey/validAuthKeys?reference=xxxx"  , "", "")
							).build();
		}

		// get event gateway configuration and authenticators
		EventGatewayConfigObject gatewayConfigObject = ConfigLoader.getGatewayConfigObject();
		if (gatewayConfigObject==null){
			throw new RuntimeException("ConfigLoader.getGatewayConfigObject() cannot be null. Check event gateway configuration file.");
		}
		HashMap<String, AutoAuthKeyGenerator> authenticatorMap = gatewayConfigObject.getAuthenticatorMap();
		if (authenticatorMap==null){
			throw new RuntimeException("ConfigLoader.getConfigMap() cannot be null. Check event gateway configuration file");
		}

		// find autoAuthKeyGenerator for gateway reference
		AutoAuthKeyGenerator autoAuthKeyGenerator = authenticatorMap.get(reference);

		if(autoAuthKeyGenerator==null){
			return Response
					.status(404)
					.entity(new EventGatewayErrorMessage(404, -1, "there is no authenticator defined for event gateway reference=" + reference  , "", "")).build();
		}

		Map<String, Date> authKeyCache = autoAuthKeyGenerator.getAuthKeyCache();

		StringBuilder authKeyList = new StringBuilder();

		for (Entry<String, Date> entry : authKeyCache.entrySet()){
			Date creationDate =entry.getValue();
			authKeyList.append(entry.getKey()).append(":");
			authKeyList.append(creationDate.toString()).append(", ");
		}
		
		RestEventReplyMessage reply= new RestEventReplyMessage();
		
		NameValuePair nvpReference = new NameValuePair();
		nvpReference.setName("reference");
		nvpReference.setValue(reference);
		reply.getReplyValues().add(nvpReference);
		
		NameValuePair nvp = new NameValuePair();
		nvp.setName("authKeyList");
		nvp.setValue(authKeyList.toString());
		reply.getReplyValues().add(nvp);

		return Response
				.status(200)
				.entity(reply).build();

	}

	@GET
	@Path("/resetAuthKeys")
	@Produces(MediaType.APPLICATION_XML)
	public Response clearAuthKeys(@QueryParam("reference") String reference){
		// validate input
		if(reference==null){
			return Response
					.status(400)
					.entity(new EventGatewayErrorMessage(400, -1, "you have not supplied a gateway reference. Use form /eventgateway/authkey/resetAuthKeys?reference=xxxx" , "", "")
							).build();
		}

		// get event gateway configuration and authenticators
		EventGatewayConfigObject gatewayConfigObject = ConfigLoader.getGatewayConfigObject();
		if (gatewayConfigObject==null){
			throw new RuntimeException("ConfigLoader.getGatewayConfigObject() cannot be null. Check event gateway configuration file.");
		}
		HashMap<String, AutoAuthKeyGenerator> authenticatorMap = gatewayConfigObject.getAuthenticatorMap();
		if (authenticatorMap==null){
			throw new RuntimeException("ConfigLoader.getConfigMap() cannot be null. Check event gateway configuration file");
		}

		// find autoAuthKeyGenerator for gateway reference
		AutoAuthKeyGenerator autoAuthKeyGenerator = authenticatorMap.get(reference);

		if(autoAuthKeyGenerator==null){
			return Response
					.status(404)
					.entity(new EventGatewayErrorMessage(404, -1, "there is no authenticator defined for event gateway reference=" + reference , "", "")).build();

		}

		String newAuthkey = autoAuthKeyGenerator.resetAuthKeyCache();

		RestEventReplyMessage reply= new RestEventReplyMessage();
		
		NameValuePair nvpReference = new NameValuePair();
		nvpReference.setName("reference");
		nvpReference.setValue(reference);
		reply.getReplyValues().add(nvpReference);
		
		NameValuePair nvp = new NameValuePair();
		nvp.setName("message");
		nvp.setValue("authenticaton cache reset with new current authKey for gateway reference"+reference );
		reply.getReplyValues().add(nvp);
		
		NameValuePair nvpNewAuthKey = new NameValuePair();
		nvpNewAuthKey.setName("newAuthKey");
		nvpNewAuthKey.setValue(newAuthkey);
		reply.getReplyValues().add(nvpNewAuthKey);
		
		return Response
				.status(200)
				.entity(reply).build();

	}


}
