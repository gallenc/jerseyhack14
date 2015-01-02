package org.opennms.karaf.licencemgr;

import java.util.Map;

public interface LicenceService {

	public void addLicence(String licence);

	public boolean removeLicence(String productId);

	public String getLicence(String productId);

	public Map<String, String> getLicenceMap();
	
	public void deleteLicences();

	public String getSystemId();

	public void setSystemId(String systemId);
	
	public String makeSystemInstance();
	
	public String checksumForString(String string);

}
