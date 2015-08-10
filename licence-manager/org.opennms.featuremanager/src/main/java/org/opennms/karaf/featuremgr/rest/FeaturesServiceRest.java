package org.opennms.karaf.featuremgr.rest;

import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.opennms.karaf.featuremgr.jaxb.FeatureList;
import org.opennms.karaf.featuremgr.jaxb.FeatureWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * REST service to manipulate Karaf features
 */
@Path("/")
public class FeaturesServiceRest {

	/**
	 * Returns an explicit collection of all features in XML format in response to HTTP GET requests.
	 * @return the collection of features
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
			FeatureWrapper wrapper = new FeatureWrapper(features[i].getName(), features[i].getVersion());
			featuresList.getFeatureList().add(wrapper);     
		}     
		return Response.status(200).entity(featuresList).build();  
	}
} 