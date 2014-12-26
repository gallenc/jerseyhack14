package org.opennms.karaf.licencemgr.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import org.junit.Test;
import org.opennms.karaf.licencemgr.AesSymetricKeyCipher;
import org.opennms.karaf.licencemgr.LicenceMetadata;
import org.opennms.karaf.licencemgr.RsaAsymetricKeyCipher;
import org.opennms.karaf.licencemgr.StringCrc32Checksum;

public class TestEncodeDecodeLicence {
	
	public static  String aesSecretKeyStr=null;
	private static String privateKeyStr=null;
	private static String publicKeyStr=null;
	
	private static String licenceStrPlusCrc=null;

	@Test
	public void generateKeys(){
		System.out.println("@Test AgenerateKeys() Start");
		
		//asemetric cipher key
        AesSymetricKeyCipher aesCipher = new AesSymetricKeyCipher();
        try {
			aesCipher.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        aesSecretKeyStr = aesCipher.getEncodedSecretKeyStr();
        System.out.println("secretKeyStr="+aesSecretKeyStr);
        
        // generate RSA keys
		RsaAsymetricKeyCipher rsaAsymetricKeyCipher = new RsaAsymetricKeyCipher();
		rsaAsymetricKeyCipher.generateKeys();
		
		privateKeyStr=rsaAsymetricKeyCipher.getPrivateKeyStr();
		publicKeyStr=rsaAsymetricKeyCipher.getPublicKeyStr();
		
		assertNotNull(privateKeyStr);
		assertNotNull(publicKeyStr);
		
		System.out.println("@Test generateKeys() privateKeyStr="+privateKeyStr);
		System.out.println("@Test generateKeys() publicKeyStr="+publicKeyStr);
		
		System.out.println("@Test AgenerateKeys() END");
	}

	@Test
	public void BencodeLicence(){
    	System.out.println("@Test BencodeLicence() Start");
    	LicenceMetadata metadata = new LicenceMetadata();
    	
    	metadata.setEndDate(new Date());
    	metadata.setStartDate(new Date());
    	metadata.setLiscencee("Mr Craig Gallen");
    	metadata.setLiscencor("OpenNMS UK");
    	metadata.setProductId("product id");
    	metadata.setSystemId("system id");
    	metadata.getOptions().put("key1", "value1");
    	
    	String licenceMetadataHexStr = metadata.toHexString();
    	String licenceMetadataHashStr=metadata.sha256Hash();
    	
		RsaAsymetricKeyCipher rsaAsymetricKeyCipher = new RsaAsymetricKeyCipher();
		rsaAsymetricKeyCipher.setPublicKeyStr(publicKeyStr);
		
		String encryptedHashStr = rsaAsymetricKeyCipher.rsaEncryptString(licenceMetadataHashStr);
		System.out.println("@Test BencodeLicence licenceMetadataHashStr="+licenceMetadataHashStr);
		System.out.println("@Test BencodeLicence encryptedHashStr="+encryptedHashStr);
    	
    	String licenceStr= licenceMetadataHexStr+":"+encryptedHashStr+":"+aesSecretKeyStr;
		System.out.println("@Test BencodeLicence licenceStr="+licenceStr);
		
		// add checksum
		StringCrc32Checksum stringCrc32Checksum = new StringCrc32Checksum();
		licenceStrPlusCrc=stringCrc32Checksum.addCRC(licenceStr);
		
		System.out.println("    LicenceStringPlusCrc="+licenceStrPlusCrc);
		
		assertTrue(stringCrc32Checksum.checkCRC(licenceStrPlusCrc));

    	System.out.println("@Test BencodeLicence() END");
    	
	}
	
	@Test
	public void decodeLicence(){
    	System.out.println("@Test CdecodeLicence() Start");

    	
		// check checksum
		StringCrc32Checksum stringCrc32Checksum = new StringCrc32Checksum();
    	assertTrue(stringCrc32Checksum.checkCRC(licenceStrPlusCrc));
    	
    	String licenceStr= stringCrc32Checksum.removeCRC(licenceStrPlusCrc);
    	
    	String[] components = licenceStr.split(":");
    	assertEquals(components.length, 3);
    	
    	String receivedLicenceMetadataHexStr=components[0];
    	String receivedEncryptedHashStr=components[1];
    	String receivedAesSecretKeyStr=components[2];
    	
    	//TODO decode licence private key before using
    	System.out.println("@Test testDecrypt TODO ENCRYPT AND DECRYPT PUBLIC LICENCE CODE STR");
    	
    	//verify hash
		RsaAsymetricKeyCipher rsaAsymetricKeyCipher = new RsaAsymetricKeyCipher();
		rsaAsymetricKeyCipher.setPrivateKeyStr(privateKeyStr);
		
		String decriptedHashStr= rsaAsymetricKeyCipher.rsaDecryptString(receivedEncryptedHashStr);
		
		System.out.println("@Test testDecrypt decriptedHashStr="+decriptedHashStr);
		
		LicenceMetadata licenceMetadata= new LicenceMetadata();
		licenceMetadata.fromHexString(receivedLicenceMetadataHexStr);
		String sha256Hash = licenceMetadata.sha256Hash();
		
		String metadataxml= licenceMetadata.toXml();
		System.out.println("@Test testDecrypt licenceMetadata.toxml="+metadataxml);
		System.out.println("@Test testDecrypt licenceMetadata.sha256Hash="+sha256Hash);
		
		assertEquals(sha256Hash,decriptedHashStr);
		
    	System.out.println("@Test CdecodeLicence() End");
    	
	}
}
