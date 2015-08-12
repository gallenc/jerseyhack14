package org.opennms.karaf.featuremgr.rest;

import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.karaf.features.Repository;
import org.opennms.karaf.featuremgr.jaxb.FeatureList;
import org.opennms.karaf.featuremgr.jaxb.FeatureWrapperJaxb;
import org.opennms.karaf.featuremgr.jaxb.ErrorMessage;
import org.opennms.karaf.featuremgr.jaxb.RepositoryList;
import org.opennms.karaf.featuremgr.jaxb.RepositoryWrapperJaxb;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

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
public class FeaturesServiceRest {

	/**
	 * Returns an explicit collection of all features in XML format in response to HTTP GET requests.
	 * @return a response containing a list of Features
	 */
	@GET
	@Path("/features-list")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getFeaturesList() throws Exception {
		
		FeaturesService featuresService = ServiceLoader.getFeaturesService();
		if (featuresService == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		FeatureList featuresList = new FeatureList();
		Feature[] features = featuresService.listFeatures();
		for (int i = 0; i < features.length; i++) {
            Boolean isInstalled = featuresService.isInstalled(features[i]);
			FeatureWrapperJaxb wrapper = new FeatureWrapperJaxb(features[i].getName(), features[i].getVersion(), features[i].getDescription(), features[i].getDetails(),isInstalled);
			featuresList.getFeatureList().add(wrapper);     
		}     
		return Response.status(200).entity(featuresList).build();  
	}
	
	/** 
	 * Returns feature in XML format in response to HTTP GET requests.
	 * @return a response containing a feature or an ErrorMessage
	 */
	@GET
	@Path("/features-info")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getFeaturesInfo(@QueryParam("name") String name, @QueryParam("version") String version) throws Exception {
		
		FeaturesService featuresService = ServiceLoader.getFeaturesService();
		if (featuresService == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		Feature feature=null;
        Boolean isInstalled=null;
		try{
			if (name== null) throw new RuntimeException("feature name cannot be null.");
			if (version !=null) {
				feature = featuresService.getFeature(name,version); 
			} else feature = featuresService.getFeature(name); 
			if (feature== null) throw new RuntimeException("feature not found.");
			isInstalled = featuresService.isInstalled(feature);
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "unable to get feature name="+name+ " version="+version, null, exception)).build();
		}
		FeatureWrapperJaxb featurewrapper = new FeatureWrapperJaxb(feature.getName(), feature.getVersion(), feature.getDescription(), feature.getDetails(), isInstalled);
		return Response.status(200).entity(featurewrapper).build();  
	}
	
	/** 
	 * Uninstalls a feature with the specified name and version.
	 * @return a response containing a feature or an ErrorMessage
	 */
	@GET
	@Path("/features-uninstall")
	@Produces(MediaType.APPLICATION_XML)
	public Response  featuresUninstall(@QueryParam("name") String name, @QueryParam("version") String version) throws Exception {
		
		FeaturesService featuresService = ServiceLoader.getFeaturesService();
		if (featuresService == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		//TODO
		return Response.status(400).build();
	}
	
	/** 
	 * Installs a feature with the specified name and version.
	 * @return a response containing a feature or an ErrorMessage
	 */
	@GET
	@Path("/features-install")
	@Produces(MediaType.APPLICATION_XML)
	public Response  featuresInstall(@QueryParam("name") String name, @QueryParam("version") String version) throws Exception {
		
		FeaturesService featuresService = ServiceLoader.getFeaturesService();
		if (featuresService == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		//TODO
		return Response.status(400).build();
	}
	
	/** 
	 * Returns an explicit collection of all defined repositories in XML format in response to HTTP GET requests.
	 * @return a response containing a feature or an ErrorMessage
	 */
	@GET
	@Path("/features-listrepositories")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getFeaturesListRepositories() throws Exception {

		FeaturesService featuresService = ServiceLoader.getFeaturesService();
		if (featuresService == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		Repository[] repositories = featuresService.listRepositories();
		RepositoryList repositoryList= new RepositoryList();
		for (int i = 0; i < repositories.length; i++) {
			RepositoryWrapperJaxb wrapper = new RepositoryWrapperJaxb(repositories[i].getName(), repositories[i].getURI());
			repositoryList.getRepositoryList().add(wrapper);     
		}
		return Response.status(400).entity(repositoryList).build();
	}
	
	/** 
	 * Returns repository in XML format in response to HTTP GET requests.
	 * name or URI can be used to select the repository
	 * @return a response containing a feature or an ErrorMessage
	 */
	@GET
	@Path("/features-repositoryinfo")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getFeaturesRepositoryInfo(@QueryParam("name") String name, @QueryParam("uri") String uriStr) throws Exception {
		
		FeaturesService featuresService = ServiceLoader.getFeaturesService();
		if (featuresService == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		Repository repository=null;
		RepositoryWrapperJaxb repositoryWrapper=null;
		try{
			// check paramaters
			if (name== null && uriStr==null) throw new RuntimeException("you must specify either a ?uri= or ?name= parameter.");
			if (name!=null && uriStr!=null) throw new RuntimeException("you can only specify ONE of either a ?uri= or ?name= parameter.");
			
			URI repoUri=null;
			if(uriStr!=null) repoUri = new URI(uriStr); // will throw exception if uriStr cannot be parsed
			
			//find repository
			Repository repositories[]=featuresService.listRepositories();
			for (int i = 0; i < repositories.length; i++){
				if (name!=null){ //testing name 
					if(repositories[i].getName().equals(name)) repository=repositories[i];
				}else { // or testing uri
					if(repositories[i].getURI().equals(repoUri)) repository=repositories[i];
				}
			}	
			if (repository== null) throw new RuntimeException("repository not found.");
			
			// wrap repository details as Jaxb objects
			FeatureList featuresList = new FeatureList();
			Feature[] features = repository.getFeatures();
			for (int i = 0; i < features.length; i++) {
	            Boolean isInstalled = featuresService.isInstalled(features[i]);
				FeatureWrapperJaxb wrapper = new FeatureWrapperJaxb(features[i].getName(), features[i].getVersion(), features[i].getDescription(), features[i].getDetails(),isInstalled);
				featuresList.getFeatureList().add(wrapper);     
			}
			
			List<URI> repositoriesURI = Arrays.asList(repository.getRepositories());
			
			repositoryWrapper= new RepositoryWrapperJaxb(
					repository.getName(), 
					repository.getURI(), 
					featuresList, 
					repositoriesURI);
			
		} catch (URISyntaxException uriException){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "unable to parse URI for feature uri="+uriStr, null, uriException)).build();
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "problem finding repository="+name+ " "+uriStr, null, exception)).build();
		}

		return Response.status(200).entity(repositoryWrapper).build();  
	}
	
	/** 
	 * Removes the specified repository features service..
	 * @return a response containing a message or an ErrorMessage
	 */
	@GET
	@Path("/features-removerepository")
	@Produces(MediaType.APPLICATION_XML)
	public Response  featuresRemoveRepository() throws Exception {
		
		FeaturesService featuresService = ServiceLoader.getFeaturesService();
		if (featuresService == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		// featuresService.removeRepository(url);
		return Response.status(400).build();
	}
	
	/** 
	 * adds a repository url.
	 * @return a response containing a message or an ErrorMessage
	 */
	@GET
	@Path("/features-addrepositoryurl")
	@Produces(MediaType.APPLICATION_XML)
	public Response  featuresAddRepository() throws Exception {
		
		FeaturesService featuresService = ServiceLoader.getFeaturesService();
		if (featuresService == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		// featuresService.addRepository(url);

		
		return Response.status(400).build();
	}
	

	
	/*
	 *        features:addurl              Adds a list of repository URLs to the features service
        features:chooseurl           Add a repository url for well known features
        features:info                Shows information about selected feature.
        features:install             Installs a feature with the specified name and version.
        features:list                Lists all existing features available from the defined repositories.
        features:listrepositories    Displays a list of all defined repositories.
        features:listurl             Displays a list of all defined repository URLs.
        features:listversions        Lists all versions of a feature available from the currently available repositories.
        features:refreshurl          Reloads the list of available features from the repositories.
        features:removerepository    Removes the specified repository features service.
        features:removeurl           Removes the given list of repository URLs from the features service
        features:uninstall           Uninstalls a feature with the specified name and version.


	 */
	
	//TODO REMOVE
	/*
	TODO 
	complete rest services for repositories and features
	add, delete, list repositories and features
	complete test page for features service
	
	create config load/save service for vaadin ui
	vaadin ui - load information from given rest interfaces
	vaadin ui - deploy features from product spec
	vaadin ui - deploy licences for features
	
	get working with opennms 16
	convert to work with apache cxf / opennms 17
	
	*/
	private void temp(){
		FeaturesService featuresService=ServiceLoader.getFeaturesService();

		URI url=null;
		try {
			featuresService.addRepository(url);
			featuresService.removeRepository(url);
			featuresService.listRepositories();
			
			
			String name=null;
			featuresService.getFeature(name); // done
			
			String version=null;
			featuresService.getFeature(name, version); //done
			featuresService.installFeature(name);
			featuresService.installFeature(name, version);
			featuresService.uninstallFeature(name);
			featuresService.uninstallFeature(name, version);
			
			Feature f=null;
			featuresService.isInstalled(f);

			URI uri=null;
			featuresService.validateRepository(uri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
} 