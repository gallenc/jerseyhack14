package org.opennms.karaf.licencemgr;

/**
 * This interface defines the keys to be made available to the LicencePublisher in order to create 
 * new licences
 * @author cgallen
 *
 */
public interface PublisherKeys {
	
	/**
	 * @return the aesSecretKeyStr
	 */
	public String getAesSecretKeyStr() ;
	
	/**
	 * @return the publicKeyStr
	 */
	public String getPublicKeyStr();

}
