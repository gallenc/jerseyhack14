package org.opennms.karaf.licencemgr.rest.client.test.manual;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadataList;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecList;
import org.opennms.karaf.licencemgr.metadata.jaxb.Util;
import org.opennms.karaf.licencemgr.rest.client.LicencePublisherClient;
import org.opennms.karaf.licencemgr.rest.client.jerseyimpl.LicencePublisherClientRestJerseyImpl;


public class LicencePublisherClientRestJerseyTest {
	
	// initialises tests
	private LicencePublisherClient getLicencePublisherClient() {

		// defaults for test running on standard karaf
		String baseUrl = "http://localhost:8181";
		String basePath = "/pluginmgr/rest/licence-pub";

		LicencePublisherClientRestJerseyImpl licencePublisherClient = new LicencePublisherClientRestJerseyImpl();
		licencePublisherClient.setBasePath(basePath);
		licencePublisherClient.setBaseUrl(baseUrl);

		return licencePublisherClient;
	}

	
	@Test
	public void addLicenceSpecTest(){
		
		fail("Not yet implemented");
		// TODO Auto-generated method stub
		
	}

	@Test
	public void removeLicenceSpecTest(){
		
		fail("Not yet implemented");
		// TODO Auto-generated method stub
		
	}

	@Test
	public void getLicenceSpecTest(){
		
		fail("Not yet implemented");
		// TODO Auto-generated method stub
		
	}

	@Test
	public void getLicenceMetadataTest(){
		
		fail("Not yet implemented");
		// TODO Auto-generated method stub
		
	}

	@Test
	public void getLicenceSpecListTest(){
		System.out.println("@Test - getLicenceSpecListTest().START");

		LicencePublisherClient licencePublisherClient = getLicencePublisherClient();

		try {
			LicenceSpecList licenceSpecList = licencePublisherClient.getLicenceSpecList();
			System.out.println(Util.toXml(licenceSpecList));
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - getListTest() failed. See stack trace in consol");
		}

		System.out.println("@Test - getLicenceSpecListTest().FINISH");
		
	}

	@Test
	public void getLicenceMetadataListTest(){
		System.out.println("@Test - getLicenceMetadataListTest().START");

		LicencePublisherClient licencePublisherClient = getLicencePublisherClient();

		try {
			LicenceMetadataList licenceMetadataList = licencePublisherClient.getLicenceMetadataList();
			System.out.println(Util.toXml(licenceMetadataList));
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - getListTest() failed. See stack trace in consol");
		}

		System.out.println("@Test - getLicenceMetadataListTest().FINISH");
		
	}

	@Test
	public void deleteLicenceSpecificationsTest(){
		
		fail("Not yet implemented");
		// TODO Auto-generated method stub
		
	}

	@Test
	public void createLicenceInstanceStrTest(){
		
		fail("Not yet implemented");
		// TODO Auto-generated method stub
		
	}

}
