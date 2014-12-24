package org.opennms.karaf.licencemgr.test;


import javax.xml.bind.DatatypeConverter; 

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.*;
import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencemgr.LicenceServiceImpl;
import org.opennms.karaf.licencemgr.RsaKeyEncryptor;
import org.opennms.karaf.licencemgr.StringChecksum;

import static org.junit.Assert.*;


/**
 * @author cgallen
 * See http://www.javamex.com/tutorials/cryptography/asymmetric.shtml 
 * This example based on tutorial
 * Note tests are run by surefire in alphabetical order - not order in class
 *
 */

public class RSAEncryptionTest {

	static String publicKeyStr=null;
	static String privateKeyStr=null;
	static String encryptedStr=null;
	static String encryptedStrPlusCrc=null;
	
	// system instance
	static final String testString="4ad72a34e3635c1b-99da3323";

	//static LicenceService licenceService=null;

	@BeforeClass
	public static void oneTimeSetUp() {
		System.out.println("@Before - setting up tests");

	}

	@AfterClass
	public static void oneTimeTearDown() {
		System.out.println("@After - tearDown");
	}


	@Test
	public void atestGenerateKeys() {
		RsaKeyEncryptor rsaKeyEncryptor = new RsaKeyEncryptor();
		rsaKeyEncryptor.generateKeys();
		
		privateKeyStr=rsaKeyEncryptor.getPrivateKeyStr();
		publicKeyStr=rsaKeyEncryptor.getPublicKeyStr();
		
		assertNotNull(privateKeyStr);
		assertNotNull(publicKeyStr);
		
		System.out.println("@Test generateKeys() privateKeyStr="+privateKeyStr);
		System.out.println("@Test generateKeys() publicKeyStr="+publicKeyStr);
	}

	@Test
	public void btestEncrypt() {

		RsaKeyEncryptor rsaKeyEncryptor = new RsaKeyEncryptor();
		rsaKeyEncryptor.setPublicKeyStr(publicKeyStr);
		
		encryptedStr = rsaKeyEncryptor.rsaEncryptString(testString);
		System.out.println("@Test testEncrypt testString="+testString);
		System.out.println("@Test testEncrypt encryptedStr="+encryptedStr);
		
		// test string plus crc
		encryptedStrPlusCrc=rsaKeyEncryptor.rsaEncryptStringAddChecksum(testString);
		System.out.println("@Test testEncrypt encryptedStrPlusCrc="+encryptedStrPlusCrc);
		
		
	}

	@Test
	public void ctestDecrypt() {
		System.out.println("@Test testDecrypt encryptedStr="+encryptedStr);
		
		RsaKeyEncryptor rsaKeyEncryptor = new RsaKeyEncryptor();
		rsaKeyEncryptor.setPrivateKeyStr(privateKeyStr);
		
		String decriptedStr= rsaKeyEncryptor.rsaDecryptString(encryptedStr);
		
		System.out.println("@Test testDecrypt decryptedStr="+decriptedStr);
		
		assertEquals(testString,decriptedStr);
		
		// test string plus crc
		decriptedStr=rsaKeyEncryptor.rsaDecryptStringRemoveChecksum(encryptedStrPlusCrc);
		System.out.println("@Test testDecrypt  encryptedStrPlusCrc="+encryptedStrPlusCrc);
		System.out.println("@Test testDecrypt  decryptedstring="+decriptedStr);
		
		assertEquals(testString,decriptedStr);
		
	}





}