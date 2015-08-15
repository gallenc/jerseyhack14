package org.opennms.karaf.featuremanager;

import org.opennms.karaf.featuremgr.jaxb.FeatureList;
import org.opennms.karaf.featuremgr.jaxb.FeatureWrapperJaxb;
import org.opennms.karaf.featuremgr.jaxb.RepositoryList;
import org.opennms.karaf.featuremgr.jaxb.RepositoryWrapperJaxb;


/**
 * service to manipulate Karaf features
 */
public interface FeaturesServiceClient {

	/* ************************************
	 * feature management rest interface
	 * ************************************
	 */

	/**
	 * Returns an explicit collection of all features in XML format in response to HTTP GET requests.
	 * @return a response containing a list of Features
	 */
	public FeatureList  getFeaturesList() throws Exception ;

	/** 
	 * Returns feature in XML format in response to HTTP GET requests.
	 * @return a response containing a feature or Exception
	 */
	public FeatureWrapperJaxb  getFeaturesInfo( String name,  String version) throws Exception ;

	/** 
	 * Installs a feature with the specified name and version.
	 * @param name  name of the feature
	 * @param version version of the feature (optional - if not supplied will use the latest found)
	 */
	public void  featuresInstall( String name,  String version) throws Exception ;


	/** 
	 * Uninstalls a feature with the specified name and version.
	 * @param name  name of the feature
	 * @param version version of the feature (optional - if not supplied will use the latest found)
	 */
	void  featuresUninstall(String name,  String version) throws Exception ;

	/* *******************************
	 * repository management interface
	 * *****************************
	 */

	/** 
	 * Returns an explicit collection of all defined repositories in XML format in response to HTTP GET requests.
	 * @return a response containing a RepositoryList
	 */
	public RepositoryList  getFeaturesListRepositories() throws Exception ;

	/** 
	 * Returns repository in XML format in response to HTTP GET requests.
	 * name or URI can be used to select the repository
	 * @return a response containing a feature or an ErrorMessage
	 */

	public RepositoryWrapperJaxb  getFeaturesRepositoryInfo( String name,  String uriStr) throws Exception ;

	/** 
	 * Removes the specified repository features service..
	 * @param String uri locating the repository
	 */
	public void featuresRemoveRepository(String uriStr) throws Exception ;

	/** 
	 * adds a repository url.
	 * @param String uri locating the repository
	 */
	public void  featuresAddRepository( String uriStr) throws Exception ;

} 