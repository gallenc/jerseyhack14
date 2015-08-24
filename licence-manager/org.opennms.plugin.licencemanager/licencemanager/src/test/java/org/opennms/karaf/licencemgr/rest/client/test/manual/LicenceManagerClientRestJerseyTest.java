package org.opennms.karaf.licencemgr.rest.client.test.manual;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceEntry;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.rest.client.LicenceManagerClient;
import org.opennms.karaf.licencemgr.rest.client.jerseyimpl.LicenceManagerClientRestJerseyImpl;

public class LicenceManagerClientRestJerseyTest {
	// constants for tests
	private static String test_productId="myproject/1.0-SNAPSHOT";

	private static String test_system_id="32e396e36b28ef5d-a48ef1cb";

	private static String test_licence_instance="3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D22796573223F3E3C6C6963656E63654D657461646174613E3C70726F6475637449643E6D7970726F6A6563742F312E302D534E415053484F543C2F70726F6475637449643E3C666561747572655265706F7369746F72793E6F72672E6F70656E6E6D732E70726F6A6563742F6D7970726F6A6563742F312E302D534E415053484F542F786D6C2F66656174757265733C2F666561747572655265706F7369746F72793E3C6C6963656E7365653E3C2F6C6963656E7365653E3C6C6963656E736F723E4F70656E4E4D5320554B3C2F6C6963656E736F723E3C73797374656D49643E333265333936653336623238656635642D61343865663163623C2F73797374656D49643E3C7374617274446174653E323031352D30382D31385431363A35303A30382E3534312B30313A30303C2F7374617274446174653E3C6475726174696F6E3E303C2F6475726174696F6E3E3C6F7074696F6E733E3C6F7074696F6E3E3C6465736372697074696F6E3E7468697320697320746865206465736372697074696F6E206F66206F7074696F6E20313C2F6465736372697074696F6E3E3C6E616D653E6F7074696F6E313C2F6E616D653E3C76616C75653E3C2F76616C75653E3C2F6F7074696F6E3E3C2F6F7074696F6E733E3C2F6C6963656E63654D657461646174613E:519FB5FFFF47C0DD79655A3D699416DA7BDDFF62DD563854850867703C272C9B2C5E4E45DAD14B28915D3B27D106A1154B1B4A9B0F3C2CDAEFBBB47AF875BCF00195F9845DF35E7947CA4E0006E7B697D2F893CD8CA15EC05B9166A6E7FFEED7A698FE9A8FF9E805A6315FED53267E11836FB481876539B3B160C9DC3B647264E9BBDE28DD9663BD5DEE39B16F2DC926E7AAEC1EB01E6224C3C707E5AEAFC56C376D1FE74A3E59EAC9764C79C23015473B774771B4E9AEF07A6A3C7BECE1F353368F9657A449C3104D48092A9F47E0C27A3C69E18A766C75C3298286BAC175F720D3236DEB7E687846DFE83F291A4BBAB6893646375EF80D5FA606940B418768:CB0059D481BD446A49FBCBF21298A3CD-afd3af9";

	// initialises tests
	private LicenceManagerClient getLicenceManagerClient() {

		// defaults for test running on standard karaf
		String baseUrl = "http://localhost:8181";
		String basePath = "/licencemgr/rest/licence-mgr";

		LicenceManagerClientRestJerseyImpl licenceManagerClient = new LicenceManagerClientRestJerseyImpl();
		licenceManagerClient.setBasePath(basePath);
		licenceManagerClient.setBaseUrl(baseUrl);

		return licenceManagerClient;
	}

	@Test
	public void testsInOrder(){
		System.out.println("@Test - LICENCE MANAGER TESTS.START");
		
		this.checksumForStringTest();
		this.makeSystemInstanceTest();
		this.getSystemIdTest();
		this.setSystemIdTest();
		
		this.deleteLicencesTest();
		this.addLicenceTest();
		this.getLicenceTest();
		this.getLicenceMapTest();
		this.removeLicenceTest();
		
		this.deleteLicencesTest(); // final clean up
		this.getLicenceMapTest();  //  just to confirm licences gone
		
		System.out.println("@Test - LICENCE MANAGER TESTS.FINISH");
		
	}
	
	/**
	 * /addlicence (GET licence)
	 * 
	 * Adds a licence to the licence service. 
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/licence-mgr/addlicence?licence=

	 */
	//@Test
	public void addLicenceTest() {
		System.out.println("@Test - addLicenceTest().START");

		LicenceManagerClient licenceManagerClient = getLicenceManagerClient();

		try {
			String licence=test_licence_instance;
			licenceManagerClient.addLicence(licence);;
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - addLicenceTest() failed. See stack trace in consol");
		}

		System.out.println("@Test - addLicenceTest().FINISH");
	}


	/**
	 * /removelicence (GET productId)
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/licence-mgr/removelicence?productId=
	 * 
	 * removes any licence corresponding to productId.
	 */
	//@Test
	public void removeLicenceTest() {
		System.out.println("@Test - removeLicenceTest().START");

		LicenceManagerClient licenceManagerClient = getLicenceManagerClient();

		try {
			String productId=test_productId;
			licenceManagerClient.removeLicence(productId);
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - removeLicenceTest() failed. See stack trace in consol");
		}

		System.out.println("@Test - removeLicenceTest().FINISH");

	}


	/**
	 * /getlicence (GET productId)
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/licence-mgr/getlicence?productId=
	 * 
	 * Gets the licence corresponding to the productId
	 */
	//@Test
	public void getLicenceTest() {
		System.out.println("@Test - getLicenceTest().START");

		LicenceManagerClient licenceManagerClient = getLicenceManagerClient();
		String licence=null;
		String productId=test_productId;
		try {
			licence = licenceManagerClient.getLicence(productId);
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - getLicenceTest() failed. See stack trace in consol");
		}
		System.out.println("licence for productId='"+productId+"' ='"+licence+"'");

		System.out.println("@Test - getLicenceTest().FINISH");
	}


	/**
	 * /list (GET )
	 * e.g http://localhost:8181/licencemgr/rest/licence-mgr/list
	 * 
	 * returns a map of all installed licences 
	 * with key=productId and value = licence string
	 */
	//@Test
	public void getLicenceMapTest() {
		System.out.println("@Test - getLicenceMapTest().START");

		LicenceManagerClient licenceManagerClient = getLicenceManagerClient();
		
		LicenceList licenceList=null;
		try {
			licenceList = licenceManagerClient.getLicenceMap();
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - getLicenceMapTest() failed. See stack trace in consol");
		}
		List<LicenceEntry> licenceEntries = licenceList.getLicenceList();
		
		System.out.println("Licence List:");
		for (LicenceEntry licenceEntry : licenceEntries) {
			System.out.println("    productId='"+licenceEntry.getProductId()+"' licence='"+licenceEntry.getLicenceStr()+"'");
		}

		System.out.println("@Test - getLicenceMapTest().FINISH");
	}


	/**
	 * /clearlicences (GET )
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/licence-mgr/clearlicences?confirm=false
	 * 
	 * deletes all licence entries. Will only delete licences if paramater confirm=true 
	 */
	//@Test
	public void deleteLicencesTest() {
		System.out.println("@Test - deleteLicencesTest().START");

		LicenceManagerClient licenceManagerClient = getLicenceManagerClient();
		Boolean confirm=true;
		try {
			licenceManagerClient.deleteLicences(confirm);
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - deleteLicencesTest() failed. See stack trace in consol");
		}
        // success !

		System.out.println("@Test - deleteLicencesTest().FINISH");
	}


	/**
	 * /getsystemid (GET ) 
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/licence-mgr/getsystemid
	 * 
	 * gets the systemId for this system
	 */
	//@Test
	public void getSystemIdTest() {
		System.out.println("@Test - getSystemIdTest.START");

		LicenceManagerClient licenceManagerClient = getLicenceManagerClient();
		String systemId=null;
		try {
			systemId = licenceManagerClient.getSystemId();
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - getSystemIdTest failed. See stack trace in consol");
		}
		System.out.println("systemId="+systemId);

		System.out.println("@Test - getSystemIdTest.FINISH");
	}


	/**
	 * /setsystemid (GET systemId) 
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/licence-mgr/setsystemid?systemId=
	 * 
	 * sets the systemId. 
	 * Note that the checksum for the systemId must be correct
	 */
	//@Test
	public void setSystemIdTest() {
		System.out.println("@Test - setSystemIdTest.START");

		LicenceManagerClient licenceManagerClient = getLicenceManagerClient();

		String systemId=test_system_id;
		try {
			licenceManagerClient.setSystemId(systemId);
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - setSystemIdTest failed. See stack trace in consol");
		}
		System.out.println("systemId set to "+systemId);

		System.out.println("@Test - setSystemIdTest.FINISH");
	}


	/**
	 * /makesystemid (GET )
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/licence-mgr/makesystemid
	 * 
	 * Makes a new systemId with a random identifier and checksum.
	 * Sets the systemId to the newly generated value.
	 */
	//@Test
	public void makeSystemInstanceTest() {
		System.out.println("@Test - makeSystemInstanceTest.START");

		LicenceManagerClient licenceManagerClient = getLicenceManagerClient();

		String systemId=null;		

		try {
			systemId = licenceManagerClient.makeSystemInstance();
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - makeSystemInstanceTest failed. See stack trace in consol");
		}
		System.out.println("new systemId set to "+systemId);

		System.out.println("@Test - makeSystemInstanceTest.FINISH");
	}



	/**
	 * /checksumforstring (GET string)
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/licence-mgr/checksumforstring?string=
	 * 
	 * Generates a checksum for the supplied string
	 * Adds a CRC32 encoded string to supplied string separated by '-' 
	 * resulting in string of form 'valueString'-'CRC32 in Hex'. 
	 * returns original string plus checksum in form 'valueString'-'CRC32 in Hex' 
	 */
	//@Test
	public void  checksumForStringTest() {
		System.out.println("@Test - checksumForStringTest.START");

		LicenceManagerClient licenceManagerClient = getLicenceManagerClient();

		String testString="32e396e36b28ef5d"; // string part of test_system_id
		String testWithChecksumString=null;	

		try {
			// should return test_system_id
			testWithChecksumString = licenceManagerClient.checksumForString(testString);
		} catch (Exception e) {
			e.printStackTrace();
			fail("@Test - checksumForStringTest failed. See stack trace in consol");
		}
		System.out.println("testString="+testString+" testString with checksum="+testWithChecksumString);
		
		// checksum should be same as on test_system_id
		assertEquals(testWithChecksumString, test_system_id);

		System.out.println("@Test - checksumForStringTest.FINISH");
	}

}
