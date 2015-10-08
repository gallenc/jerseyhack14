package org.opennms.features.pluginmgr.rest;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * REST service to access plugin manager
 */
@Path("/")
public interface PluginManagerRest {
	

	/**
	 * Returns manifest list in XML format in response to HTTP GET request.
	 * e.g. http://localhost:8980/opennms/pluginmgr/rest/manifest-list?systemId=
	 * @param systemId the system id for which to get the manifest
	 * @return response containing manifest list or an error message if not found
	 * @throws Exception 
	 */
	@GET
	@Path("/manifest-list")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getManifestList(@QueryParam("systemId") String systemId) throws Exception ;


}
