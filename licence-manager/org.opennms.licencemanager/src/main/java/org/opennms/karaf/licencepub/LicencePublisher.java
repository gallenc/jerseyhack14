package org.opennms.karaf.licencepub;

import java.util.Map;

import org.opennms.karaf.licencemgr.metadata.Licence;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecification;

public interface LicencePublisher {

	public void addLicenceSpec(LicenceSpecification licenceSpec);
	
	public boolean removeLicenceSpec(String productId );

	public LicenceSpecification getLicenceSpec(String productId);

	public Map<String, LicenceSpecification> getLicenceSpecMap();
	
	public void deleteLicenceSpecifications();
	
	public String createLicenceInstanceStr(LicenceMetadata licenceMetadata);
	
	public String createLicenceInstanceStr(String licenceMetadataXml);
	
}
