package org.opennms.karaf.featuremgr.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * REST service to manipulate Karaf features
 */
@Path("/")
public interface FeaturesServiceRest {

	/* ************************************
	 * feature management rest interface
	 * ************************************
	 */
	
	/**
	 * Returns an explicit collection of all features in XML format in response to HTTP GET requests.
	 * @return a response containing a list of Features
	 */
	@GET
	@Path("/features-list")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getFeaturesList() throws Exception ;
	
	/** 
	 * Returns feature in XML format in response to HTTP GET requests.
	 * @return a response containing a feature or an ErrorMessage
	 */
	@GET
	@Path("/features-info")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getFeaturesInfo(@QueryParam("name") String name, @QueryParam("version") String version) throws Exception ;
	/** 
	 * Installs a feature with the specified name and version.
	 * @param name  name of the feature
	 * @param version version of the feature (optional - if not supplied will use the latest found)
	 * @return a 200 response or an ErrorMessage
	 */
	@GET
	@Path("/features-install")
	@Produces(MediaType.APPLICATION_XML)
	public Response  featuresInstall(@QueryParam("name") String name, @QueryParam("version") String version) throws Exception ;
	

	/** 
	 * Uninstalls a feature with the specified name and version.
	 * @param name  name of the feature
	 * @param version version of the feature (optional - if not supplied will use the latest found)
	 * @return a 200 response or an ErrorMessage
	 */
	@GET
	@Path("/features-uninstall")
	@Produces(MediaType.APPLICATION_XML)
	public Response  featuresUninstall(@QueryParam("name") String name, @QueryParam("version") String version) throws Exception ;
	
/* ************************************
 * repository management rest interface
 * ************************************
 */
	
	/** 
	 * Returns an explicit collection of all defined repositories in XML format in response to HTTP GET requests.
	 * @return a response containing a feature or an ErrorMessage
	 */
	@GET
	@Path("/features-listrepositories")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getFeaturesListRepositories() throws Exception ;
	
	/** 
	 * Returns repository in XML format in response to HTTP GET requests.
	 * name or URI can be used to select the repository
	 * @return a response containing a feature or an ErrorMessage
	 */
	@GET
	@Path("/features-repositoryinfo")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getFeaturesRepositoryInfo(@QueryParam("name") String name, @QueryParam("uri") String uriStr) throws Exception ;
	
	/** 
	 * Removes the specified repository features service..
	 * @param String uri locating the repository
	 * @return a 200 response or an ErrorMessage
	 */
	@GET
	@Path("/features-removerepository")
	@Produces(MediaType.APPLICATION_XML)
	public Response  featuresRemoveRepository(@QueryParam("uri") String uriStr) throws Exception ;
	
	/** 
	 * adds a repository url.
	 * @param String uri locating the repository
	 * @return a 200 response or an ErrorMessage
	 */
	@GET
	@Path("/features-addrepositoryurl")
	@Produces(MediaType.APPLICATION_XML)
	public Response  featuresAddRepository(@QueryParam("uri") String uriStr) throws Exception ;

} 