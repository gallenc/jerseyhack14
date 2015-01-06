package org.opennms.karaf.licencemgr;

/**
 * This interface defines the keys to be made available to the Client code in order to verify
 * a licence.
 * @author cgallen
 *
 */
public interface ClientKeys {
	
	/**
	 * @return the privateKeyEnryptedStr
	 */
	public String getPrivateKeyEnryptedStr();


}
