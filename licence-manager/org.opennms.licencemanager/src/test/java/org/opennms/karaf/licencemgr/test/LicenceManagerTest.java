package org.opennms.karaf.licencemgr.test;

import org.junit.*;
import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencemgr.LicenceServiceImpl;
import org.opennms.karaf.licencemgr.StringCrc32Checksum;

import static org.junit.Assert.*;


/**
 * @author cgallen
 *
 */
public class LicenceManagerTest {

	static LicenceService licenceService=null;

    @BeforeClass
	public static void oneTimeSetUp() {
		System.out.println("@Before - setting up tests");
		LicenceServiceImpl licenceServiceImpl = new LicenceServiceImpl();

		licenceServiceImpl.setFileUri("./target/licenceService.xml");
		licenceServiceImpl.load();

		licenceService=licenceServiceImpl;

	}

	@AfterClass
	public static void oneTimeTearDown() {
		System.out.println("@After - tearDown");
	}

	@Test
	public void testMakeSystemInstance() {
		String instanceId = licenceService.makeSystemInstance();
		System.out.println("@Test - testMakeSystemInstance(). instanceId="+instanceId);

		StringCrc32Checksum stringCrc32Checksum = new StringCrc32Checksum();
		assertTrue(stringCrc32Checksum.checkCRC(instanceId));

	}


	@Test
	public void testSetWrongSystemInstance() {

		boolean thrown = false;
		try {
			licenceService.setSystemId("ddfgdfhdhjd");
		} catch (RuntimeException e) {
			System.out.println("testSetWrongSystemInstance() 1 Expected Exception:"+e);
			thrown = true;
		}
		assertTrue(thrown);
		
		try {
			licenceService.setSystemId("bc032e68b0defeb5-e7dde04b");
		} catch (RuntimeException e) {
			System.out.println("testSetWrongSystemInstance() 2 Expected Exception:"+e);
			thrown = true;
		}
		assertTrue(thrown);
		
		try {
			licenceService.setSystemId("bc032e68b0de-feb4-e7dde04b");
		} catch (RuntimeException e) {
			System.out.println("testSetWrongSystemInstance() 3 Expected Exception:"+e);
			thrown = true;
		}
		assertTrue(thrown);

	}

	@Test
	public void testSetCorrectSystemInstance() {
		boolean thrown=false;
		try {
			licenceService.setSystemId("bc032e68b0defeb4-e7dde04b");
		} catch (RuntimeException e) {
			System.out.println("testSetCorrectSystemInstance() Unexpected Exception:"+e);
			thrown = true;
		}
		assertFalse(thrown);

	}
	
	@Test
	public void testAddCorrectLicences() {
		// adds 2 licences
		boolean thrown=false;
		try {
			String productId="com.mycompany.myproject/myproject-service/3.0.4.RELEASE";
			String licence="bc032e68b0defeb4-e7dde04b";
			licenceService.addLicence(productId, licence);
		} catch (RuntimeException e) {
			System.out.println("testAddCorrectLicences() Unexpected Exception:"+e);
			thrown = true;
		}
		
		try {
			String productId="com.mycompany.myproject/myproject-service2/3.0.4.RELEASE";
			String licence="bc032e68b0defeb4-e7dde04b";
			licenceService.addLicence(productId, licence);
		} catch (RuntimeException e) {
			System.out.println("testAddCorrectLicences() Unexpected Exception:"+e);
			thrown = true;
		}
		
		assertFalse(thrown);

	}
	
	@Test
	public void testAddInCorrectLicence() {
		boolean thrown=false;
		try {
			String productId="com.mycompany.myproject/myproject-service/3.0.4.RELEASE";
			String licence="bc032e68b0defeb5-e7dde04b"; //incorrect checksum
			licenceService.addLicence(productId, licence);
		} catch (RuntimeException e) {
			System.out.println("testAddInCorrectLicence() expected Exception:"+e);
			thrown = true;
		}
		assertTrue(thrown);

	}
	
	
	@Test
	public void testGetLicence() {
		String productId="com.mycompany.myproject/myproject-service2/3.0.4.RELEASE";
		String licence = licenceService.getLicence(productId);
		assertEquals("bc032e68b0defeb4-e7dde04b",licence);
	}

}