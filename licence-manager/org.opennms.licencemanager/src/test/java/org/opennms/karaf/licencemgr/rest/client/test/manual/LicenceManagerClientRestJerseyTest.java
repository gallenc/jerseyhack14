package org.opennms.karaf.licencemgr.rest.client.test.manual;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opennms.karaf.licencemgr.rest.client.LicenceManagerClient;
import org.opennms.karaf.licencemgr.rest.client.jerseyimpl.LicenceManagerClientRestJerseyImpl;

//TODO COMPLETE
public class LicenceManagerClientRestJerseyTest {

	// initialises tests
	private LicenceManagerClient getLicenceManagerClient(){
		
		//defaults for test running on standard karaf
		String baseUrl = "http://localhost:8181";
		String basePath = "/featuremgr";
		
		LicenceManagerClientRestJerseyImpl jerseyFeaturesService = new LicenceManagerClientRestJerseyImpl(); 
		jerseyFeaturesService.setBasePath(basePath);
		jerseyFeaturesService.setBaseUrl(baseUrl);
		
		return jerseyFeaturesService;
	}
	
	@Test
	public void AfeaturesAddRepository() {
		System.out.println("@Test - featuresAddRepository.START");

		String uriStr="mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features";

		//http://localhost:8181/featuremgr/rest/features-addrepositoryurl?uri=mvn:org.opennms.project/myproject.Feature/1.0-SNAPSHOT/xml/features

		LicenceManagerClient  licenceManager = getLicenceManagerClient(); 
		try {
	//		licenceManager.featuresAddRepository(uriStr);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("@Test - featuresAddRepository() failed. See stack trace in consol");
		}
		
		System.out.println("@Test - featuresAddRepository.FINISH");
		
	}

}
