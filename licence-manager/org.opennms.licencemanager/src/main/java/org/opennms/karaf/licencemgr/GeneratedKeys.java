package org.opennms.karaf.licencemgr;

import org.opennms.karaf.licencemgr.AesSymetricKeyCipher;
import org.opennms.karaf.licencemgr.RsaAsymetricKeyCipher;

/**
 * This Class generates all public and private keys for a licence / LicenceValidator pair
 * Each new instance of GeneratedKeys generates a new key set at instance creation. 
 * @author cgallen
 *
 */
public class GeneratedKeys {

	private final String aesSecretKeyStr;
	private final String privateKeyStr;
	private final String publicKeyStr;
	private final String privateKeyEnryptedStr;

	public GeneratedKeys(){

		//Asymmetric cipher key
		AesSymetricKeyCipher aesCipher = new AesSymetricKeyCipher();

		aesCipher.generateKey();

		aesSecretKeyStr = aesCipher.getEncodedSecretKeyStr();

		// generate RSA keys
		RsaAsymetricKeyCipher rsaAsymetricKeyCipher = new RsaAsymetricKeyCipher();
		rsaAsymetricKeyCipher.generateKeys();

		privateKeyStr=rsaAsymetricKeyCipher.getPrivateKeyStr();
		publicKeyStr=rsaAsymetricKeyCipher.getPublicKeyStr();

		//encrypt private key
		privateKeyEnryptedStr = aesCipher.aesEncryptStr(privateKeyStr);

	}
	
	/**
	 * @return the privateKeyStr
	 */
	public String getPrivateKeyStr() {
		return privateKeyStr;
	}

	/**
	 * @return the publicKeyStr
	 */
	public String getPublicKeyStr() {
		return publicKeyStr;
	}

	
	/**
	 * @return the aesSecretKeyStr
	 */
	public String getAesSecretKeyStr() {
		return aesSecretKeyStr;
	}

	/**
	 * Returns the private key encrypted using the aesSecretKeyStr
	 * @return the privateKeyEnryptedStr
	 */
	public String getPrivateKeyEnryptedStr() {
		return privateKeyEnryptedStr;
	}
}
