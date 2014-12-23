package org.opennms.karaf.licencemgr.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class RSAEncryptionTest1 {

	static byte[] encrypted=null;

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
	}

	@Test
	public void testEncrypt() {
		byte[] src=null;
		try {
			src = "teststring".getBytes("UTF-8");
			encrypted= rsaEncrypt(src);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testDecrypt() {
		byte[] decrypt;
		decrypt = rsaDecrypt(encrypted);
		String str=null;
		
		try {
			str = new String(decrypt, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(str);
	}



	public void generateKeys() {

		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");

			kpg.initialize(2048);
			KeyPair kp = kpg.genKeyPair();
			Key publicKey = kp.getPublic();
			Key privateKey = kp.getPrivate();

			KeyFactory fact;

			fact = KeyFactory.getInstance("RSA");

			RSAPublicKeySpec pub;

			pub = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);

			RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(),  RSAPrivateKeySpec.class);

			saveToFile("./public.key", pub.getModulus(), pub.getPublicExponent());
			saveToFile("./private.key", priv.getModulus(), priv.getPrivateExponent());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void saveToFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {
		ObjectOutputStream oout = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(fileName)));
		try {
			oout.writeObject(mod);
			oout.writeObject(exp);
		} catch (Exception e) {
			throw new IOException("Unexpected error", e);
		} finally {
			oout.close();
		}
	}

	PrivateKey readPrivateKeyFromFile(String keyFileName) throws IOException {
		FileInputStream in = new FileInputStream(keyFileName);
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
		try {
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = fact.generatePrivate(keySpec);
			return privateKey;
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		} finally {
			oin.close();
		}
	}

	PublicKey readPublicKeyFromFile(String keyFileName) throws IOException {
		FileInputStream in = new FileInputStream(keyFileName);
		ObjectInputStream oin =
				new ObjectInputStream(new BufferedInputStream(in));
		try {
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey pubKey = fact.generatePublic(keySpec);
			return pubKey;
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		} finally {
			oin.close();
		}
	}

	public byte[] rsaEncrypt(byte[] src) {
		PublicKey pubKey;
		byte[] cipherData=null;
		try {
			pubKey = readPublicKeyFromFile("./public.key");

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			cipherData = cipher.doFinal(src);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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


	public byte[] rsaDecrypt(byte[] src) {
		byte[] cipherData=null;
		try {
			PrivateKey privateKey = readPrivateKeyFromFile("./private.key");
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			cipherData = cipher.doFinal(src);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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