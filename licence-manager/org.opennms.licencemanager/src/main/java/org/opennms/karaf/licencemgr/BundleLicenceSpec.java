package org.opennms.karaf.licencemgr;

import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.opennms.karaf.licencepub.LicencePublisher;
import org.osgi.framework.BundleContext;

public interface BundleLicenceSpec {

	public void unregisterSpec();

	public void registerSpec();

	public void setBundleContext(BundleContext bundleContext);

	public BundleContext getBundleContext();

	public LicenceMetadata getLicenceMetadataSpec();

	public void setLicenceMetadataSpec(LicenceMetadata licenceMetadata);

	public LicencePublisher getLicencePublisher();

	public void setLicencePublisher(LicencePublisher licencePublisher);

	public String getLicenceMetadataUri();

	public void setLicenceMetadataUri(String licenceMetadataUri);

	public String getAesSecretKeyStr();

	public void setAesSecretKeyStr(String aesSecretKeyStr);

	public String getPublicKeyStr();

	public void setPublicKeyStr(String publicKeyStr);

	public void setProductId(String productId);

	public String getProductId();
	
}
