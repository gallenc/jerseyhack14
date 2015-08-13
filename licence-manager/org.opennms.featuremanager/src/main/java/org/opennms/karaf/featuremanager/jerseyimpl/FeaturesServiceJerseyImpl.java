/**
 * 
 */
package org.opennms.karaf.featuremanager.jerseyimpl;

import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.opennms.karaf.featuremanager.FeaturesServiceI;
import org.opennms.karaf.featuremgr.jaxb.FeatureList;
import org.opennms.karaf.featuremgr.jaxb.FeatureWrapperJaxb;
import org.opennms.karaf.featuremgr.jaxb.RepositoryList;
import org.opennms.karaf.featuremgr.jaxb.RepositoryWrapperJaxb;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * @author craig gallen
 *
 */
public class FeaturesServiceJerseyImpl implements FeaturesServiceI {
	
	private String baseUrl = "http://localhost:8181";
	private String basePath = "/featuremgr";
	
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

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.FeaturesService#getFeaturesList()
	 */
	@Override
	public FeatureList getFeaturesList() throws Exception {

		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		
		Client client = Client.create();
		WebResource r = client
				.resource(baseUrl+basePath+"/rest/features-list");

		FeatureList featurelist = r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(FeatureList.class);
		
		return featurelist;

	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.FeaturesService#getFeaturesInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public FeatureWrapperJaxb getFeaturesInfo(String name, String version) throws Exception {

		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
	    if (name==null)throw new RuntimeException("?name= parameter must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/featuremgr/rest/features-info?name=myproject.Feature&version=1.0-SNAPSHOT
		
		String getStr= baseUrl+basePath+"/rest/features-info?name="+name;
		if(version != null) getStr=getStr+"&version="+version;
		
		WebResource r = client
				.resource(getStr);

		FeatureWrapperJaxb featurewrapper = r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(FeatureWrapperJaxb.class);
		
		return featurewrapper;
	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.FeaturesService#featuresInstall(java.lang.String, java.lang.String)
	 */
	@Override
	public void featuresInstall(String name, String version) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.FeaturesService#featuresUninstall(java.lang.String, java.lang.String)
	 */
	@Override
	public void featuresUninstall(String name, String version) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.FeaturesService#getFeaturesListRepositories()
	 */
	@Override
	public RepositoryList getFeaturesListRepositories() throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.FeaturesService#getFeaturesRepositoryInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public RepositoryWrapperJaxb getFeaturesRepositoryInfo(String name,	String uriStr) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.FeaturesService#featuresRemoveRepository(java.lang.String)
	 */
	@Override
	public void featuresRemoveRepository(String uriStr) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.FeaturesService#featuresAddRepository(java.lang.String)
	 */
	@Override
	public void featuresAddRepository(String uriStr) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		// TODO Auto-generated method stub

	}
	


}
