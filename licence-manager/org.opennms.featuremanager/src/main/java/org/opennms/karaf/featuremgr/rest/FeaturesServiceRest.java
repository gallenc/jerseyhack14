package org.opennms.karaf.featuremgr.rest;

import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.opennms.karaf.featuremgr.jaxb.FeatureList;
import org.opennms.karaf.featuremgr.jaxb.FeatureWrapper;
import org.opennms.karaf.featuremgr.jaxb.ErrorMessage;

import java.net.URI;

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
	@Path("/features")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getFeatures() throws Exception {
		
		FeaturesService featuresService = ServiceLoader.getFeaturesService();
		if (featuresService == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		FeatureList featuresList = new FeatureList();
		Feature[] features = featuresService.listFeatures();
		for (int i = 0; i < features.length; i++) {      
			FeatureWrapper wrapper = new FeatureWrapper(features[i].getName(), features[i].getVersion(), features[i].getDescription(), features[i].getDetails());
			featuresList.getFeatureList().add(wrapper);     
		}     
		return Response.status(200).entity(featuresList).build();  
	}
	
	/** 
	 * Returns feature in XML format in response to HTTP GET requests.
	 * @return a response containing a feature or an ErrorMessage
	 */
	@GET
	@Path("/feature")
	@Produces(MediaType.APPLICATION_XML)
	public Response  getFeature(@QueryParam("name") String name, @QueryParam("version") String version) throws Exception {
		
		FeaturesService featuresService = ServiceLoader.getFeaturesService();
		if (featuresService == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		Feature feature=null;
		try{
			if (name== null) throw new RuntimeException("feature name cannot be null.");
			if (version !=null) {
				feature = featuresService.getFeature(name,version); 
			} else feature = featuresService.getFeature(name); 
			if (feature== null) throw new RuntimeException("feature not found.");
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "unable to get feature name="+name+ " version="+version, null, exception)).build();
		}
		FeatureWrapper featurewrapper = new FeatureWrapper(feature.getName(), feature.getVersion(), feature.getDescription(), feature.getDetails());
		return Response.status(200).entity(featurewrapper).build();  
	}
	

	
	
	//TODO REMOVE
	private void temp(){
		FeaturesService featuresService=ServiceLoader.getFeaturesService();
		
		URI url=null;
		try {
			featuresService.addRepository(url);

		String name=null;
		featuresService.getFeature(name);
		String version=null;
		featuresService.getFeature(name, version);
		featuresService.installFeature(name);
		featuresService.installFeature(name, version);
		Feature f=null;
		featuresService.isInstalled(f);
		featuresService.removeRepository(url);
		featuresService.uninstallFeature(name);
		featuresService.uninstallFeature(name, version);
		featuresService.listRepositories();
		featuresService.removeRepository(url);
		URI uri=null;
		featuresService.validateRepository(uri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
} 