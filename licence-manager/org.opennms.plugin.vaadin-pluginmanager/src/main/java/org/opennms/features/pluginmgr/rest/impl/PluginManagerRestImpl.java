package org.opennms.features.pluginmgr.rest.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.opennms.features.pluginmgr.PluginManager;
import org.opennms.features.pluginmgr.rest.PluginManagerRest;
import org.opennms.features.pluginmgr.rest.impl.ServiceLoader;
import org.opennms.karaf.featuremgr.jaxb.ErrorMessage;

/**
 * REST service to access plugin manager
 */
@Path("/")
public class PluginManagerRestImpl implements PluginManagerRest {


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
	@Override
	public Response  getManifestList(@QueryParam("systemId") String systemId) throws Exception {
		
		PluginManager pluginManager = ServiceLoader.getPluginManager();
		if (pluginManager == null) throw new RuntimeException("ServiceLoader.getPluginManager() cannot be null.");

		return Response.status(400).entity(new ErrorMessage(400, 0, "getManifestList not implemented systemId="+systemId, null, "")).build();

	}

}
