package org.opennms.karaf.featuremgr.rest.client.test.manual;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opennms.karaf.featuremanager.rest.client.FeaturesServiceClient;
import org.opennms.karaf.featuremanager.rest.client.jerseyimpl.FeaturesServiceClientRestJerseyImpl;
import org.opennms.karaf.featuremgr.jaxb.FeatureList;
import org.opennms.karaf.featuremgr.jaxb.FeatureWrapperJaxb;
import org.opennms.karaf.featuremgr.jaxb.RepositoryList;
import org.opennms.karaf.featuremgr.jaxb.RepositoryWrapperJaxb;
import org.opennms.karaf.featuremgr.jaxb.Util;

/**
 * Test of jersey web client implementation
 * @author admin
 *
 */
public class FeaturesServiceClientRestJerseyTest {

	// initialises tests
	private FeaturesServiceClient getFeaturesService(){
		
		//defaults for test running on standard karaf
		String baseUrl = "http://localhost:8181";
		String basePath = "/featuremgr";
		
		FeaturesServiceClientRestJerseyImpl jerseyFeaturesService = new FeaturesServiceClientRestJerseyImpl(); 
		jerseyFeaturesService.setBasePath(basePath);
		jerseyFeaturesService.setBaseUrl(baseUrl);
		
		return jerseyFeaturesService;
	}
	
	@Test
	public void AfeaturesAddRepository() {
		System.out.println("@Test - featuresAddRepository.START");

		String uriStr="mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features";

		//http://localhost:8181/featuremgr/rest/features-addrepositoryurl?uri=mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features

		FeaturesServiceClient featuresService = getFeaturesService(); 
		try {
			featuresService.featuresAddRepository(uriStr);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - featuresAddRepository() failed. See stack trace in consol");
		}
		
		System.out.println("@Test - featuresAddRepository.FINISH");
		
	}
	
	@Test
	public void BgetFeaturesListRepositories() {
		System.out.println("@Test - getFeaturesListRepositories.START");
		
		FeaturesServiceClient featuresService = getFeaturesService(); 
		try {
			RepositoryList repositoryList = featuresService.getFeaturesListRepositories();
			System.out.println(Util.toXml(repositoryList));
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - getFeaturesListRepositories() failed. See stack trace in consol");
		}

		System.out.println("@Test - getFeaturesListRepositories.FINISH");
		
	}

	@Test
	public void CgetFeaturesRepositoryInfo() {
		System.out.println("@Test - getFeaturesRepositoryInfo.START");
		
		String name=null;
		String uriStr="mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features";

		//http://localhost:8181/featuremgr/rest/features-repositoryinfo?uri=mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features
				
		FeaturesServiceClient featuresService = getFeaturesService(); 
		try {
			RepositoryWrapperJaxb repositoryWrapper = featuresService.getFeaturesRepositoryInfo(name, uriStr);
			System.out.println(Util.toXml(repositoryWrapper));
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - getFeaturesRepositoryInfo failed. See stack trace in consol");
		}

		System.out.println("@Test - getFeaturesRepositoryInfo.FINISH");
		
	}

	
	@Test
	public void DgetFeaturesList() {

		System.out.println("@Test - getFeaturesList. START");
		
		FeaturesServiceClient featuresService = getFeaturesService(); 
		try {
			FeatureList featuresList = featuresService.getFeaturesList();
			System.out.println(Util.toXml(featuresList));
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - getFeaturesList() failed. See stack trace in consol");
		}
		
		System.out.println("@Test - getFeaturesList.FINISH");
		
	}

	@Test
	public void EfeaturesInstall() {
		System.out.println("@Test - featuresInstall.START");
		
		String name="myproject.Feature";
		String version="1.0-SNAPSHOT";
				
		FeaturesServiceClient featuresService = getFeaturesService(); 
		try {
			featuresService.featuresInstall(name, version);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - featuresInstall() failed. See stack trace in consol");
		}
		
		System.out.println("@Test - featuresInstall.FINISH");

	}

	@Test
	public void FgetFeaturesInfo() {
		
		System.out.println("@Test - getFeaturesInfo.START");
		
		//http://localhost:8181/featuremgr/rest/features-info?name=myproject.Feature&version=1.0-SNAPSHOT
		
		String name="myproject.Feature";
		String version="1.0-SNAPSHOT";
				
		FeaturesServiceClient featuresService = getFeaturesService(); 
		try {
			FeatureWrapperJaxb featureWrapper = featuresService.getFeaturesInfo(name, version);
			System.out.println(Util.toXml(featureWrapper));
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - getFeaturesInfo() failed. See stack trace in consol");
		}
		
		System.out.println("@Test - getFeaturesInfo.FINISH");

	}

	@Test
	public void GfeaturesUninstall() {
		System.out.println("@Test - featuresUninstall.START");
		String name="myproject.Feature";
		String version="1.0-SNAPSHOT";
				
		FeaturesServiceClient featuresService = getFeaturesService(); 
		try {
			featuresService.featuresUninstall(name, version);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - featuresUninstall() failed. See stack trace in consol");
		}
		System.out.println("@Test - featuresUninstall.FINISH");
	}


	@Test
	public void HfeaturesRemoveRepository() {
		System.out.println("@Test - featuresRemoveRepository.START");
		
		String uriStr="mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features";

		//http://localhost:8181/featuremgr/rest/features-removerepository?uri=mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features
				
		FeaturesServiceClient featuresService = getFeaturesService(); 
		try {
			featuresService.featuresRemoveRepository(uriStr);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - featuresRemoveRepository() failed. See stack trace in consol");
		}
		
		System.out.println("@Test - featuresRemoveRepository.FINISH");
	}


}
