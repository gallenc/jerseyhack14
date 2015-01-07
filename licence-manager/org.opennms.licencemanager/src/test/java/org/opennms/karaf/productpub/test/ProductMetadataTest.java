package org.opennms.karaf.productpub.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opennms.karaf.licencemgr.metadata.ProductMetadata;

public class ProductMetadataTest {

	@Test
	public void testProductMetadata() {
		System.out.println("@Test - testProductMetadata. START");
		ProductMetadata pmeta= new ProductMetadata();
		pmeta.setOrganization("OpenNMS Project");
		pmeta.setProductDescription("Test product description");
		pmeta.setProductId("org.opennms/org.opennms.karaf.licencemanager.testbundle/1.0-SNAPSHOT");
		pmeta.setProductName("test Bundle");
		pmeta.setProductUrl("http:opennms.co.uk");
		
		String productMetadataXml=pmeta.toXml();
		String productMetadataHex=pmeta.toHexString();
		
		System.out.println("@Test - testProductMetadata. productMetadataXml="+productMetadataXml);
		System.out.println("@Test - testProductMetadata. productMetadataHex="+productMetadataHex);
		
		ProductMetadata pmeta2= new ProductMetadata();
		pmeta2.fromXml(productMetadataXml);
		assertEquals(pmeta,pmeta2);
		assertEquals(pmeta.toXml(),pmeta2.toXml());
		
		ProductMetadata pmeta3= new ProductMetadata();
		pmeta3.fromHexString(productMetadataHex);
		assertEquals(pmeta,pmeta3);
		assertEquals(pmeta.toXml(),pmeta3.toXml());
		
		System.out.println("@Test - testProductMetadata. END");
	}

}
