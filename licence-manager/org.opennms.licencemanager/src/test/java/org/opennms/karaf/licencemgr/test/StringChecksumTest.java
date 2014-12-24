package org.opennms.karaf.licencemgr.test;

import org.junit.*;
import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencemgr.LicenceServiceImpl;
import org.opennms.karaf.licencemgr.StringChecksum;

import static org.junit.Assert.*;


/**
 * @author cgallen
 *
 */
public class StringChecksumTest {

    @BeforeClass
	public static void oneTimeSetUp() {
		System.out.println("@Before - setting up tests");

	}

	@AfterClass
	public static void oneTimeTearDown() {
		System.out.println("@After - tearDown");
	}

	@Test
	public void testaStringChecksum() {
		System.out.println("@Test - testStringChecksum ");
		String testString="testString";
		checksumTest(testString);
	}
	
	@Test
	public void testbStringChecksum() {
		System.out.println("@Test - testStringChecksum ");
		String testString="test-String";
		checksumTest(testString);
	}
	
	@Test
	public void testcStringChecksum() {
		System.out.println("@Test - testStringChecksum ");
		String testString="testString-";
		checksumTest(testString);
	}
	
	@Test
	public void testdStringChecksum() {
		System.out.println("@Test - testStringChecksum ");
		String testString="-testString";
		checksumTest(testString);
	}

    public void checksumTest(String valueString){
		StringChecksum stringChecksum = new StringChecksum();
		String testStringPlusCrc=stringChecksum.addCRC(valueString);
		
		System.out.println("     testStringChecksum testStringPlusCrc="+testStringPlusCrc);
		
		assertTrue(stringChecksum.checkCRC(testStringPlusCrc));
		
		String stringwithoutChecksum = stringChecksum.removeCRC(testStringPlusCrc);
		assertEquals(valueString, stringwithoutChecksum );
		System.out.println("     stringwithoutChecksum="+stringwithoutChecksum);
    }

	@Test
    public void checksum1FailTest(){
		System.out.println("@Test - checksum1FailTest ");
		String valueString="test-String";
		StringChecksum stringChecksum = new StringChecksum();
		String testStringPlusCrc=stringChecksum.addCRC(valueString)+"b"; // incorrect checksum
		
		System.out.println("     testStringChecksum Wrong testStringPlusCrc="+testStringPlusCrc);
		
		assertFalse(stringChecksum.checkCRC(testStringPlusCrc));
		
		String stringwithoutChecksum = stringChecksum.removeCRC(testStringPlusCrc);
		assertNull(stringwithoutChecksum);
		System.out.println("     null  stringwithoutChecksum="+stringwithoutChecksum);
    }
	
    public void checksum2FailTest(){
		System.out.println("@Test - checksum2FailTest ");
		String valueString="test-String";
		StringChecksum stringChecksum = new StringChecksum();
		String testStringPlusCrc=stringChecksum.addCRC(valueString)+"z"; // incorrect checksum not a number
		
		System.out.println("     testStringChecksum Wrong testStringPlusCrc="+testStringPlusCrc);
		
		assertFalse(stringChecksum.checkCRC(testStringPlusCrc));
		
		String stringwithoutChecksum = stringChecksum.removeCRC(testStringPlusCrc);
		assertNull(stringwithoutChecksum);
		System.out.println("     null  stringwithoutChecksum="+stringwithoutChecksum);
    }
	
	@Test
    public void checksum3FailTest(){
		System.out.println("@Test - checksum2FailTest ");
		String valueString="test-String";
		StringChecksum stringChecksum = new StringChecksum();
		String testStringPlusCrc="EXTRA"+stringChecksum.addCRC(valueString); // incorrect checksum
		
		System.out.println("     testString3Checksum Wrong testStringPlusCrc="+testStringPlusCrc);
		
		assertFalse(stringChecksum.checkCRC(testStringPlusCrc));
		
		String stringwithoutChecksum = stringChecksum.removeCRC(testStringPlusCrc);
		assertNull(stringwithoutChecksum);
		System.out.println("     null  stringwithoutChecksum="+stringwithoutChecksum);
    }
}