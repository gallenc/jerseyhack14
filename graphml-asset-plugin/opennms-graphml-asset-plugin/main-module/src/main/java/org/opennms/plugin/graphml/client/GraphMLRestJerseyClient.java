/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2016 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2016 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.plugin.graphml.client;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.graphdrawing.graphml.GraphmlType;
import org.opennms.karaf.licencemgr.metadata.jaxb.ErrorMessage;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ReplyMessage;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;


public class GraphMLRestJerseyClient {

    
    
	private String baseUrl = "http://localhost:8980";
	private String basePath = "/opennms/rest";
	private String userName = null; // If userName is null no basic authentication is generated
	private String password = "";
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * User name to use in basic authentication
	 * If userName is null then no basic authentication is generated
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
     * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * password to use in basic authentication.
	 * password must not be set to null but if not set, password will default to empty string "".
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		if (password==null) throw new RuntimeException("password must not be set to null");
		this.password = password;
	}
	
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
  
	private Client newClient(){
		Client client = Client.create();
		if (userName!=null) client.addFilter(new HTTPBasicAuthFilter(userName, password));
		return  client;
	}

    public String createGraph(String graphname, GraphmlType graphmlType) throws IOException {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(graphmlType==null ) throw new RuntimeException("graphmlType must be set");
	    
		Client client = newClient();
		
		//http://localhost:8980/opennms/rest/graphml/minimalistic1
		
		String getStr= baseUrl+basePath+"/graphml/"+graphname;
		
		WebResource r = client.resource(getStr);

		// get method
		//ClientResponse response = r.accept(MediaType.APPLICATION_XML)
        //        .type(MediaType.APPLICATION_FORM_URLENCODED).get(ClientResponse.class);
		
		// POST method
		ClientResponse response = r
			    .type(MediaType.APPLICATION_XML)
			    .post(ClientResponse.class, graphmlType);

        // check response status code and reply error message
        if (response.getStatus() != 200) {
        	return response.toString();
        }


        return  "OK";
    }
    

    public String deleteGraph(String graphname) throws IOException {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(graphname==null || "".equals(graphname)) throw new RuntimeException("graphname must be set");
	    
		Client client = newClient();
		
		//http://localhost:8980/opennms/rest/graphml/minimalistic1
		
		String getStr= baseUrl+basePath+"/graphml/"+graphname;
		
		WebResource r = client.resource(getStr);

		// get method
		//ClientResponse response = r.accept(MediaType.APPLICATION_XML)
        //        .type(MediaType.APPLICATION_FORM_URLENCODED).get(ClientResponse.class);
		
		// DELETE method
		ClientResponse response = r
			    .type(MediaType.APPLICATION_XML)
			    .delete(ClientResponse.class);

        // check response status code and reply error message
        if (response.getStatus() != 200) {
        	return response.toString();
        }


        return  graphname+" deleted";
    }


    public GraphmlType getGraph( String graphname) throws IOException {
    	if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(graphname==null || "".equals(graphname)) throw new RuntimeException("graphname must be set");
	    
		Client client = newClient();
		
		GraphmlType graphmlType =null;
		
		//http://localhost:8980/opennms/rest/graphml/minimalistic1
		
		String getStr= baseUrl+basePath+"/graphml/"+graphname;
		
		WebResource r = client.resource(getStr);

		// get method
		//ClientResponse response = r.accept(MediaType.APPLICATION_XML)
        //        .type(MediaType.APPLICATION_FORM_URLENCODED).get(ClientResponse.class);
		
		// GET method
		ClientResponse response = r.accept(MediaType.APPLICATION_XML)
			    .type(MediaType.APPLICATION_XML)
			    .get(ClientResponse.class);

        // check response status code and reply error message
        if (response.getStatus() != 200) {
        	return null;
        }

        graphmlType = response.getEntity(GraphmlType.class);

        return graphmlType;
    }
    
    
    
    

	



}