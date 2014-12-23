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
import org.opennms.karaf.licencemgr.StringChecksum;

import static org.junit.Assert.*;


/**
 * @author cgallen
 * See http://www.javamex.com/tutorials/cryptography/asymmetric.shtml 
 * This example based on tutorial
 *
 */
public class RSAEncryptionTest3 {

	static String publicKeyStr=null;
	static String privateKeyStr=null;
	static String encryptedStr=null;

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
	public void testGenerateKeys() {
		RsaKeyEncryptor rsaKeyEncryptor = new RsaKeyEncryptor();
		rsaKeyEncryptor.generateKeys();
		
		privateKeyStr=rsaKeyEncryptor.getPrivateKeyStr();
		publicKeyStr=rsaKeyEncryptor.getPublicKeyStr();
		
		assertNotNull(privateKeyStr);
		assertNotNull(publicKeyStr);
		
		System.out.println("@Test generateKeys() privatekey="+privateKeyStr);
		System.out.println("@Test generateKeys() publickey="+publicKeyStr);
	}

	@Test
	public void testEncrypt() {
		String testString="teststring";
		
		RsaKeyEncryptor rsaKeyEncryptor = new RsaKeyEncryptor();
		rsaKeyEncryptor.setPublicKeyStr(publicKeyStr);
		
		encryptedStr = rsaKeyEncryptor.rsaEncryptString(testString);
		System.out.println("@Test testEncrypt testString="+testString);
		System.out.println("@Test testEncrypt encryptedStr="+encryptedStr);

	}

	@Test
	public void testDecrypt() {
		System.out.println("@Test testDecrypt encryptedStr="+encryptedStr);
		
		RsaKeyEncryptor rsaKeyEncryptor = new RsaKeyEncryptor();
		rsaKeyEncryptor.setPrivateKeyStr(privateKeyStr);
		
		String decriptedStr= rsaKeyEncryptor.rsaDecryptString(encryptedStr);
		System.out.println("@Test testDecrypt decryptedStr="+decriptedStr);

	}





}