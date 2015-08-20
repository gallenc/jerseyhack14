/**
 * 
 */
package org.opennms.karaf.featuremanager.rest.client.jerseyimpl;


import javax.ws.rs.core.MediaType;
import org.opennms.karaf.featuremanager.rest.client.FeaturesServiceClient;
import org.opennms.karaf.featuremgr.jaxb.ErrorMessage;
import org.opennms.karaf.featuremgr.jaxb.FeatureList;
import org.opennms.karaf.featuremgr.jaxb.FeatureWrapperJaxb;
import org.opennms.karaf.featuremgr.jaxb.RepositoryList;
import org.opennms.karaf.featuremgr.jaxb.RepositoryWrapperJaxb;
import org.opennms.karaf.featuremgr.jaxb.ReplyMessage;
import org.opennms.karaf.featuremgr.jaxb.Util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * @author craig gallen
 *
 */
public class FeaturesServiceClientRestJerseyImpl implements FeaturesServiceClient {
	
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
	 * @see org.opennms.karaf.featuremanager.rest.client.FeaturesService#getFeaturesList()
	 */
	@Override
	public FeatureList getFeaturesList() throws Exception {

		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		
		Client client = Client.create();
		
		//http://localhost:8181/featuremgr/rest/features-list
		
		WebResource r = client
				.resource(baseUrl+basePath+"/rest/features-list");

		FeatureList featurelist = r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(FeatureList.class);
		
		return featurelist;

	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.rest.client.FeaturesService#getFeaturesInfo(java.lang.String, java.lang.String)
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
	 * @see org.opennms.karaf.featuremanager.rest.client.FeaturesService#featuresInstall(java.lang.String, java.lang.String)
	 */
	@Override
	public void featuresInstall(String name, String version) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
	    if (name==null)throw new RuntimeException("?name= parameter must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/featuremgr/rest/features-install?name=myproject.Feature&version=1.0-SNAPSHOT
		
		String getStr= baseUrl+basePath+"/rest/features-install?name="+name;
		if(version != null) getStr=getStr+"&version="+version;
		
		WebResource r = client.resource(getStr);

		String replyString= r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(String.class);
		
		// unmarshalling reply
		Object replyObject = Util.fromXml(replyString);
		if (replyObject instanceof ErrorMessage){
			ErrorMessage errorm= (ErrorMessage)replyObject;
			throw new RuntimeException("could not install feature."
					+" status:"+ errorm.getStatus()
					+" message:"+ errorm.getMessage()
					+" code:"+ errorm.getCode()
					+" developer message:"+errorm.getDeveloperMessage());
			
		} else if (! (replyObject instanceof ReplyMessage) ){
			throw new RuntimeException("received unexpected reply object: "+replyObject.getClass().getCanonicalName());
		} 
		// success !!!
	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.rest.client.FeaturesService#featuresUninstall(java.lang.String, java.lang.String)
	 */
	@Override
	public void featuresUninstall(String name, String version) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
	    if (name==null)throw new RuntimeException("?name= parameter must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/featuremgr/rest/features-uninstall?name=myproject.Feature&version=1.0-SNAPSHOT
		
		String getStr= baseUrl+basePath+"/rest/features-uninstall?name="+name;
		if(version != null) getStr=getStr+"&version="+version;
		
		WebResource r = client.resource(getStr);

		String replyString= r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(String.class);
		
		// unmarshalling reply
		Object replyObject = Util.fromXml(replyString);
		if (replyObject instanceof ErrorMessage){
			ErrorMessage errorm= (ErrorMessage)replyObject;
			throw new RuntimeException("could not uninstall feature."
					+" status:"+ errorm.getStatus()
					+" message:"+ errorm.getMessage()
					+" code:"+ errorm.getCode()
					+" developer message:"+errorm.getDeveloperMessage());
			
		} else if (! (replyObject instanceof ReplyMessage) ){
			throw new RuntimeException("received unexpected reply object: "+replyObject.getClass().getCanonicalName());
		} 
		// success !!!

	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.rest.client.FeaturesService#getFeaturesListRepositories()
	 */
	@Override
	public RepositoryList getFeaturesListRepositories() throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		
		Client client = Client.create();
		
		//http://localhost:8181/featuremgr/rest/features-listrepositories
		
		WebResource r = client
				.resource(baseUrl+basePath+"/rest/features-listrepositories");

		RepositoryList repositoryList = r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(RepositoryList.class);
		
		return repositoryList;

	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.rest.client.FeaturesService#getFeaturesRepositoryInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public RepositoryWrapperJaxb getFeaturesRepositoryInfo(String name,	String uriStr) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		// check parameters
		if (name== null && uriStr==null) throw new RuntimeException("you must specify either a ?uri= or ?name= parameter.");
		if (name!=null && uriStr!=null) throw new RuntimeException("you can only specify ONE of either a ?uri= or ?name= parameter.");
	    
		Client client = Client.create();

		//http://localhost:8181/featuremgr/rest/features-repositoryinfo?uri=mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features
		
		String getStr= baseUrl+basePath+"/rest/features-repositoryinfo?"+ ( (uriStr==null)? "name="+name : "uri="+uriStr);
		
		WebResource r = client
				.resource(getStr);

		RepositoryWrapperJaxb repositoryWrapper = r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(RepositoryWrapperJaxb.class);
		
		return repositoryWrapper;
	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.rest.client.FeaturesService#featuresRemoveRepository(java.lang.String)
	 */
	@Override
	public void featuresRemoveRepository(String uriStr) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		
	    if (uriStr==null)throw new RuntimeException("uriStr= parameter must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/featuremgr/rest/features-removerepository?uri=mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features
		
		String getStr= baseUrl+basePath+"/rest/features-removerepository?uri="+uriStr;
		
		WebResource r = client.resource(getStr);

		String replyString= r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(String.class);
		
		// unmarshalling reply
		Object replyObject = Util.fromXml(replyString);
		if (replyObject instanceof ErrorMessage){
			ErrorMessage errorm= (ErrorMessage)replyObject;
			throw new RuntimeException("could not uninstall feature."
					+" status:"+ errorm.getStatus()
					+" message:"+ errorm.getMessage()
					+" code:"+ errorm.getCode()
					+" developer message:"+errorm.getDeveloperMessage());
			
		} else if (! (replyObject instanceof ReplyMessage) ){
			throw new RuntimeException("received unexpected reply object: "+replyObject.getClass().getCanonicalName());
		} 
		// success !!!

	}

	/* (non-Javadoc)
	 * @see org.opennms.karaf.featuremanager.rest.client.FeaturesService#featuresAddRepository(java.lang.String)
	 */
	@Override
	public void featuresAddRepository(String uriStr) throws Exception {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
	    if (uriStr==null)throw new RuntimeException("uriStr= parameter must be set");
	    
		Client client = Client.create();
		
		//http://localhost:8181/featuremgr/rest/features-addrepositoryurl?uri=mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features
		
		String getStr= baseUrl+basePath+"/rest/features-addrepositoryurl?uri="+uriStr;
		
		WebResource r = client.resource(getStr);

		String replyString= r
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML).get(String.class);
		
		// unmarshalling reply
		Object replyObject = Util.fromXml(replyString);
		if (replyObject instanceof ErrorMessage){
			ErrorMessage errorm= (ErrorMessage)replyObject;
			throw new RuntimeException("could not uninstall feature."
					+" status:"+ errorm.getStatus()
					+" message:"+ errorm.getMessage()
					+" code:"+ errorm.getCode()
					+" developer message:"+errorm.getDeveloperMessage());
			
		} else if (! (replyObject instanceof ReplyMessage) ){
			throw new RuntimeException("received unexpected reply object: "+replyObject.getClass().getCanonicalName());
		} 
		// success !!!

	}

}
