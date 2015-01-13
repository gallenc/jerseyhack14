package org.opennms.karaf.licencemgr;

import java.util.Map;

public interface LicenceService {

	/**
	 * adds a licence to the licence service. Throws RuntimeError if licence string incorrectly formatted
	 * licence string must have correct checksum and readable LicenceMetadata
	 * the licence is added for productId corresponding to the productId in the LicenceMetadata
	 * previous entries for that productId are overwritten
	 * @param licence
	 */
	public void addLicence(String licence);

	/**
	 * removes any licence corresponding to productId. 
	 * @param productId
	 * @return true if entry corresponding to productId was found and removed. false if entry not found.
	 */
	public boolean removeLicence(String productId);

	/**
	 * gets the licence corresponding to the productId
	 * @param productId
	 * @return licence string if found or null if no entry for productId found
	 */
	public String getLicence(String productId);

	/**
	 * returns a map of all installed licences with 
	 * key=productId 
	 * and value = licence string
	 * @return
	 */
	public Map<String, String> getLicenceMap();
	
	/**
	 * deletes all licence entries
	 */
	public void deleteLicences();

	/**
	 * 
	 * @return the systemId for this system
	 */
	public String getSystemId();

	/**
	 * sets the systemId. Note that the checksum for the systemId must be correct
	 * throws RuntimeError if systemId not correct
	 * @param systemId
	 */
	public void setSystemId(String systemId);
	
	/**
	 * Makes a new systemId with a random identifier and checksum
	 * sets the systemId to the newly generated value
	 * @return
	 */
	public String makeSystemInstance();
	
	/**
	 * Generates a checksum for the supplied string
	 * Adds a CRC32 encoded string to supplied string separated by '-' resulting in string of form 
	 * form 'valueString'-'CRC32 in Hex'
	 * @param string
	 * @return original string plus checksum in form 'valueString'-'CRC32 in Hex'
	 */
	public String checksumForString(String string);

}
