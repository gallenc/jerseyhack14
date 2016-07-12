/*
 * Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opennms.plugins.tmforum.sla.rest.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.opennms.plugins.tmforum.sla.SlaNotificationService;
import org.opennms.plugins.tmforum.sla.rest.SlaManagerServiceRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * ************************************
 * sla managmement ReST interface
 * ************************************
 */
@Path("/")
public class SlaManagerServiceRestImpl implements SlaManagerServiceRest {
	private static final Logger LOG = LoggerFactory.getLogger(SlaManagerServiceRestImpl.class);

	/**
	 * GET listeners list
	 * get /hub
	 * hubFind
	 * Response Class (Status 200)
	 * List of Hub
	 * Model
	 * Model Schema
	 * [
	 *   {
	 *       "id": "string",
	 *       "callback": "string",
	 *       "query": "string"
	 *   }
	 * ]
	 */
	@GET
	@Path("/hub")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response  getListeners()  throws Exception {
		LOG.debug("getListeners() called ");

		SlaNotificationService  slaNotificationService = ServiceLoader.getSlaNotificationService();
		if (slaNotificationService == null) throw new RuntimeException("ServiceLoader.getSlaNotificationService() cannot be null.");
		
		JSONObject jsonobj1 = new JSONObject();
		jsonobj1.put("id", "1");
		jsonobj1.put("callback","http://in.listener.com");
		jsonobj1.put("query",null);
		
		
		JSONObject jsonobj2 = new JSONObject();
		jsonobj2.put("id", "2");
		jsonobj2.put("callback","http://in.listener.com");
		jsonobj2.put("query",null);
		
		JSONArray jsonarray= new JSONArray();
		jsonarray.add(jsonobj1);
		jsonarray.add(jsonobj2);

		return Response.status(200).entity(jsonarray.toString()).build();

		//return Response.status(200).build();  
	}


	/**
	 *  Get the listener corresponding to hubid GET /hub/{hubId}
	 *  hubGet
	 *  response Class (Status 200)
	 *  Hub
	 *  Model
	 *  Model Schema
	 *  {
	 * "id": "string",
	 * "callback": "string",
	 * "query": "string"
	 * }
	 */
	@GET
	@Path("/hub/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response  getListener(@PathParam("id") String id)  throws Exception {
		LOG.debug("getListener() called for id="+id);

		SlaNotificationService  slaNotificationService = ServiceLoader.getSlaNotificationService();
		if (slaNotificationService == null) throw new RuntimeException("ServiceLoader.getSlaNotificationService() cannot be null.");

		JSONObject jsonobj = new JSONObject();
		
		jsonobj.put("id", id);
		jsonobj.put("callback","http://in.listener.com");
		jsonobj.put("query",null);
		
		//{"id":"42","callback":"http://in.listener.com","query":null}

		return Response.status(200).entity(jsonobj.toString()).build();  
	}

	/**
	 * REGISTER LISTENER   POST /HUB
	 * Description : 
	 * Sets the communication endpoint address the service instance must use to deliver
	 *  information about its health state, execution state, failures and metrics. 
	 *  Subsequent POST calls will be rejected by the service if it does not support multiple listeners.
	 *   In this case DELETE /api/hub/{id} must be called before an endpoint can be created again.
	 *   Behavior : 
	 *   Returns HTTP/1.1 status code 204 if the request was successful.
	 *   Returns HTTP/1.1 status code 409 if request is not successful.
	 *   REQUEST
	 *   POST /api/hub
	 *   Accept: application/json
	 *   {"callback": "http://in.listener.com"}
	 *   RESPONSE
	 *   201
	 *   Content-Type: application/json
	 *   Location: /api/hub/42
	 *   {"id":"42","callback":"http://in.listener.com","query":null}
	 */
	@POST
	@Path("/hub")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response  addListener(String body)  throws Exception {
		LOG.debug("addListener() called for body:"+body);
		

		SlaNotificationService  slaNotificationService = ServiceLoader.getSlaNotificationService();
		if (slaNotificationService == null) throw new RuntimeException("ServiceLoader.getSlaNotificationService() cannot be null.");

		JSONParser parser = new JSONParser();

		JSONObject jsonobj = null;
		try {
			Object obj = parser.parse(body);
			jsonobj = (JSONObject) obj;
		} catch (Exception e){
			LOG.error("failed to parse body string:"+body,e);
		}
		

		return Response.status(200).entity(jsonobj.toString()).build();  
	}


	/**
	 * UNREGISTER LISTENER DELETE HUB/{ID}
	 * Description : 
	 * Clears the communication endpoint address that was set by creating the Hub.
	 * Behavior : 
	 * Returns HTTP/1.1 status code 204 if the request was successful.
	 * Returns HTTP/1.1 status code 404 if the resource is not found.
	 * REQUEST
	 * DELETE /api/hub/{id}
	 * Accept: application/json
	 * RESPONSE
	 * 204
	 */
	@DELETE
	@Path("/hub/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response  removeListener(@PathParam("id") String id) throws Exception {
		LOG.debug("removeListener() called for id="+id);

		SlaNotificationService  slaNotificationService = ServiceLoader.getSlaNotificationService();
		if (slaNotificationService == null) throw new RuntimeException("ServiceLoader.getSlaNotificationService() cannot be null.");
		LOG.debug("removeListener called");

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("id", id);
		jsonobj.put("callback","http://in.listener.com");
		jsonobj.put("query",null);

		return Response.status(200).entity(jsonobj.toString()).build();  
	}



} 