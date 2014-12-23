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
public class RSAEncryptionTest2 {

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
		generateKeys();
		System.out.println("@Test generateKeys() privatekey="+privateKeyStr);
		System.out.println("@Test generateKeys() publickey="+publicKeyStr);
	}

	@Test
	public void testEncrypt() {
		String testString="teststring";
		encryptedStr = this.rsaEncryptString(testString);
		System.out.println("@Test testEncrypt testString="+testString);
		System.out.println("@Test testEncrypt encryptedStr="+encryptedStr);

	}

	@Test
	public void testDecrypt() {
		System.out.println("@Test testDecrypt encryptedStr="+encryptedStr);
		String decriptedStr=null;
		decriptedStr = this.rsaDecryptString(encryptedStr);
		System.out.println("@Test testDecrypt decryptedStr="+decriptedStr);

	}

	public void generateKeys() {

		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.genKeyPair();
			Key publicKey = kp.getPublic();
			Key privateKey = kp.getPrivate();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey,  RSAPrivateKeySpec.class);

			BigInteger pubKeyMod=rsaPublicKeySpec.getModulus();
			BigInteger pubKeyExp=rsaPublicKeySpec.getPublicExponent();
			publicKeyStr=pubKeyMod.toString(16)+"-"+pubKeyExp.toString(16); // converts to hex and concatenates with -

			BigInteger privateKeyMod=rsaPrivateKeySpec.getModulus();
			BigInteger privateKeyExp=rsaPrivateKeySpec.getPrivateExponent();
			privateKeyStr=privateKeyMod.toString(16)+"-"+privateKeyExp.toString(16); // converts to hex and concatenates with -

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	PrivateKey readPrivateKeyFromString(String privateKeyStr) {
		try {
			String[] parts = privateKeyStr.split("-");
			if (parts.length!=2) throw new RuntimeException("incorrectly formatted keystring");
			String privateKeyModStr=parts[0];
			String privateKeyExpStr=parts[1];
			BigInteger modulus = new BigInteger(privateKeyModStr ,16);
			BigInteger exponent = new BigInteger(privateKeyExpStr ,16);
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, exponent);
			KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = rsaKeyFactory.generatePrivate(keySpec);
			return privateKey;
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		} 
	}

	PublicKey readPublicKeyFromString(String privateKeyStr) {
		try {
			String[] parts = privateKeyStr.split("-");
			if (parts.length!=2) throw new RuntimeException("incorrectly formatted keystring");
			String privateKeyModStr=parts[0];
			String privateKeyExpStr=parts[1];
			BigInteger modulus = new BigInteger(privateKeyModStr ,16);
			BigInteger exponent = new BigInteger(privateKeyExpStr ,16);
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey pubKey = fact.generatePublic(keySpec);
			return pubKey;
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		} 
	}

	/**
	 * returns a string containing a lexical representation of xsd:hexBinary 
	 * of RSA encoded source string
	 * @param sourceStr string to encode
	 * @return xsd:hexBinary string of RSA encoded source
	 */
	public String rsaEncryptString(String sourceStr){
		byte[] src;
		try {
			src = sourceStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding is not supported");
		}
		byte[] encrypted= rsaEncrypt(src);
		return DatatypeConverter.printHexBinary(encrypted);
	}

	public byte[] rsaEncrypt(byte[] src) {
		PublicKey pubKey;
		byte[] cipherData=null;
		try {
			pubKey = readPublicKeyFromString(publicKeyStr);

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			cipherData = cipher.doFinal(src);

		}  catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cipherData;

	}

	/**
	 * expects a string containing a lexical representation of xsd:hexBinary 
	 * of RSA encoded source string.
	 * @param encryptedStr string to decode
	 * @return decryptedString decrypted string
	 */
	public String rsaDecryptString(String encryptedStr){
		String decriptedStr=null;
		try {
			byte[] decrypt;
			//byte[] encrypted = DatatypeConverter.parseBase64Binary(encryptedStr);
			byte[] encrypted = DatatypeConverter.parseHexBinary(encryptedStr);
			decrypt = rsaDecrypt(encrypted);
			decriptedStr = new String(decrypt, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding is not supported");
		}
		return decriptedStr;
	}

	public byte[] rsaDecrypt(byte[] src) {
		byte[] cipherData=null;
		try {
			PrivateKey privateKey = readPrivateKeyFromString(privateKeyStr);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			cipherData = cipher.doFinal(src);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cipherData;
	}



}