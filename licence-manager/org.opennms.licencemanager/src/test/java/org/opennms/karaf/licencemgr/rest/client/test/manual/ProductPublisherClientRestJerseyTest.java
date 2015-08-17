package org.opennms.karaf.licencemgr.rest.client.test.manual;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;
import org.opennms.karaf.licencemgr.rest.client.LicenceManagerClient;
import org.opennms.karaf.licencemgr.rest.client.ProductPublisherClient;
import org.opennms.karaf.licencemgr.rest.client.jerseyimpl.LicenceManagerClientRestJerseyImpl;
import org.opennms.karaf.licencemgr.rest.client.jerseyimpl.ProductPublisherClientRestJerseyImpl;

//TODO COMPLETE
public class ProductPublisherClientRestJerseyTest {

	// initialises tests
	private ProductPublisherClient getProductPublisherClient() {

		// defaults for test running on standard karaf
		String baseUrl = "http://localhost:8181";
		String basePath = "/pluginmgr/rest/product-pub";

		ProductPublisherClientRestJerseyImpl productPublisherClient = new ProductPublisherClientRestJerseyImpl();
		productPublisherClient.setBasePath(basePath);
		productPublisherClient.setBaseUrl(baseUrl);

		return productPublisherClient;
	}


	public void addProductSpecTest() {
		System.out.println("@Test - addProductSpecTest.START");

		ProductPublisherClient productPublisherClient = getProductPublisherClient();

		try {
			// TODO
			// productPublisherClient
			fail("@Test - addProductSpecTest() not implemented");
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - addProductSpecTest() failed. See stack trace in consol");
		}

		System.out.println("@Test - addProductSpecTest.FINISH");
	}

	
	public void getListTest() {
		System.out.println("@Test - getListTest.START");

		ProductPublisherClient productPublisherClient = getProductPublisherClient();

		try {
			// TODO
			// productPublisherClient
			fail("@Test - getListTest() not implemented");
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - getListTest() failed. See stack trace in consol");
		}

		System.out.println("@Test - getListTest.FINISH");
	}

	public void getProductSpecTest() {
		System.out.println("@Test - getProductSpecTest.START");

		ProductPublisherClient productPublisherClient = getProductPublisherClient();

		try {
			// TODO
			fail("@Test - getProductSpecTest() not implemented");

			// productPublisherClient
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - getProductSpecTest() failed. See stack trace in consol");
		}

		System.out.println("@Test - getProductSpecTest.FINISH");
	}

	
	public void removeProductSpecTest() {
		System.out.println("@Test - removeProductSpecTest.START");

		ProductPublisherClient productPublisherClient = getProductPublisherClient();

		try {
			// TODO
			fail("@Test - removeProductSpecTest() not implemented");
			// productPublisherClient
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - removeProductSpecTest() failed. See stack trace in consol");
		}

		System.out.println("@Test - removeProductSpecTest.FINISH");
	}
	

	public void clearProductSpecsTest() {
		System.out.println("@Test - clearProductSpecTest.START");

		ProductPublisherClient productPublisherClient = getProductPublisherClient();

		try {
			// TODO
			fail("@Test - clearProductSpecTest() not implemented");
			// productPublisherClient
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - clearProductSpecTest() failed. See stack trace in consol");
		}

		System.out.println("@Test - clearProductSpecTest.FINISH");
	}

}
