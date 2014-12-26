package org.opennms.karaf.licencemgr.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.opennms.karaf.licencemgr.LicenceMetadata;

public class LicenceMetadataTest {

	public static LicenceMetadata metadata=null;
	public static String licenceMetadataHexStr=null;
	public static String licenceMetadataHashStr=null;
	
    @Test
    public void AmetadataTest(){
    	System.out.println("@Test AmetadataTest() Start");
    	metadata=new LicenceMetadata();
    	printMetadata(metadata);
    	System.out.println("@Test AmetadataTest() End");
    }
    
    @Test
    public void BmetadataTest(){
    	System.out.println("@Test BmetadataTest() Start");
    	metadata=new LicenceMetadata();
    	
    	metadata.setEndDate(new Date());
    	metadata.setStartDate(new Date());
    	metadata.setLiscencee("Mr Craig Gallen");
    	metadata.setLiscencor("OpenNMS UK");
    	metadata.setProductId("product id");
    	metadata.setSystemId("system id");
    	metadata.getOptions().put("key1", "value1");
    	
    	licenceMetadataHexStr=metadata.toHexString();
    	licenceMetadataHashStr=metadata.sha256Hash();
    	
    	System.out.println("@Test BmetadataTest() Original Metadata Object");
    	printMetadata(metadata);
    	System.out.println("@Test BmetadataTest() End");
    }
    
    @Test
    public void CmetadataTest(){
    	System.out.println("@Test CmetadataTest() Start");
    	
    	LicenceMetadata newMetadata = new LicenceMetadata();
    	newMetadata.fromHexString(licenceMetadataHexStr);
    	String newHash=newMetadata.sha256Hash();
    	
    	System.out.println("@Test CmetadataTest() Original Metadata Object");
    	printMetadata(metadata);
    	System.out.println("@Test CmetadataTest() New Metadata Object");
    	printMetadata(newMetadata);
    	
    	assertEquals(licenceMetadataHashStr,newHash);
    	
    	
    	System.out.println("@Test CmetadataTest() End");
    }
    
    
    public void printMetadata(LicenceMetadata metadata){
    	
    	String xml = metadata.toXml();
    	System.out.println("@Test metadataTest1() MetatdataXML="+xml);
    	String metatdataHex = metadata.toHexString();
    	System.out.println("@Test metadataTest1() MetatdataHex="+metatdataHex);
    	String hash = metadata.sha256Hash();
    	System.out.println("@Test metadataTest1() Metatdatasha256Hash="+hash);
    	
    }
    
    
    
    
    
    
}
