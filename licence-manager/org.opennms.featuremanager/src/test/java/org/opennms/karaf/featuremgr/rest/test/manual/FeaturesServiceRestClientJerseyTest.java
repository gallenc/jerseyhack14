package org.opennms.karaf.featuremgr.rest.test.manual;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opennms.karaf.featuremanager.FeaturesServiceI;
import org.opennms.karaf.featuremanager.jerseyimpl.FeaturesServiceJerseyImpl;
import org.opennms.karaf.featuremgr.jaxb.FeatureList;
import org.opennms.karaf.featuremgr.jaxb.FeatureWrapperJaxb;
import org.opennms.karaf.featuremgr.jaxb.Util;

/**
 * Test of jersey web client implementation
 * @author admin
 *
 */
public class FeaturesServiceRestClientJerseyTest {

	// initialises tests
	private FeaturesServiceI getFeaturesService(){
		
		//defaults for test running on standard karaf
		String baseUrl = "http://localhost:8181";
		String basePath = "/featuremgr";
		
		FeaturesServiceJerseyImpl jerseyFeaturesService = new FeaturesServiceJerseyImpl(); 
		jerseyFeaturesService.setBasePath(basePath);
		jerseyFeaturesService.setBaseUrl(baseUrl);
		
		return jerseyFeaturesService;
	}
	
	@Test
	public void getFeaturesList() {

		System.out.println("@Test - getFeaturesList. START");
		
		FeaturesServiceI featuresService = getFeaturesService(); 
		try {
			FeatureList featuresList = featuresService.getFeaturesList();
			System.out.println(Util.toXml(featuresList));
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - getFeaturesList() failed see stack trace in consol");
		}
		
		System.out.println("@Test - getFeaturesList.FINISH");
	}

	@Test
	public void getFeaturesInfo() {
		
		System.out.println("@Test - getFeaturesInfo.START");
		
		//http://localhost:8181/featuremgr/rest/features-info?name=myproject.Feature&version=1.0-SNAPSHOT
		
		String name="myproject.Feature";
		String version="1.0-SNAPSHOT";
				
		FeaturesServiceI featuresService = getFeaturesService(); 
		try {
			FeatureWrapperJaxb featureWrapper = featuresService.getFeaturesInfo(name, version);
			System.out.println(Util.toXml(featureWrapper));
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - getFeaturesInfo() failed see stack trace in consol");
		}
		

		System.out.println("@Test - getFeaturesInfo.FINISH");

	}

	@Test
	public void featuresInstall() {
		System.out.println("@Test - featuresInstall.START");
		
		String name="myproject.Feature";
		String version="1.0-SNAPSHOT";
				
		FeaturesServiceI featuresService = getFeaturesService(); 
		try {
			featuresService.featuresInstall(name, version);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - getFeaturesInfo() failed see stack trace in consol");
		}
		
		System.out.println("@Test - featuresInstall.FINISH");

	}

	@Test
	public void featuresUninstall() {
		System.out.println("@Test - featuresUninstall.START");
		System.out.println("@Test - featuresUninstall.FINISH");
	}

	@Test
	public void getFeaturesListRepositories() {
		System.out.println("@Test - getFeaturesListRepositories.START");
		System.out.println("@Test - getFeaturesListRepositories.FINISH");
	}

	@Test
	public void getFeaturesRepositoryInfo() {
		System.out.println("@Test - getFeaturesRepositoryInfo.START");
		System.out.println("@Test - getFeaturesRepositoryInfo.FINISH");
	}

	@Test
	public void featuresRemoveRepository() {
		System.out.println("@Test - featuresRemoveRepository.START");
		System.out.println("@Test - featuresRemoveRepository.FINISH");
	}

	@Test
	public void featuresAddRepository() {
		System.out.println("@Test - featuresAddRepository.START");
		System.out.println("@Test - featuresAddRepository.FINISH");
	}
}
