package org.opennms.karaf.licencemanager.testbundle.licence;

import org.opennms.karaf.licencemgr.BundleLicenceSpec;
import org.opennms.karaf.licencemgr.metadata.LicenceMetadata;
import org.opennms.karaf.licencepub.LicencePublisher;
import org.opennms.karaf.licencepub.LicenceSpecification;
import org.opennms.karaf.licencemgr.BundleLicenceSpecImpl;

public class BundleLocalLicenceSpecImpl extends BundleLicenceSpecImpl implements BundleLicenceSpec{
	
	private static String productId="org.opennms/org.opennms.karaf.licencemanager.testbundle/1.0-SNAPSHOT";
	private static String licenceMetadataSpecXml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
			+ "<LicenceMetadata>"
			+ "<productId>org.opennms/org.opennms.karaf.licencemanager.testbundle/1.0-SNAPSHOT</productId>"
			+ "<licensee>Mr Craig Gallen</licensee><licensor>OpenNMS UK</licensor>"
			+ "<systemId>4ad72a34e3635c1b-99da3323</systemId><startDate>2015-01-06T14:30:33.948Z</startDate>"
			+ "<expiryDate>2015-01-06T14:30:33.948Z</expiryDate><options><option>"
			+ "<description>this is the description</description><name>newname</name><value>newvalue</value>"
			+ "</option></options></LicenceMetadata>";
	private static String aesSecretKeyStr="FC13C81D9C4690A335FA4289CC54C591";
	private static String publicKeyStr="e2670200057d9d0f4b92c465d8b195c6573e329af04efe13f44dcbbafdaf101ddf4ca1a9fdde0861087d2daef38ada09ef82ea5260dd9b5c08cf390cac188c9012e30ea70ef611b756f23e27abc2829fc453c3077da2390253b8a6b9a82aaa86483ea5189ed0f850c60a7bdd91f7f98a02d4d46a245de38178b02bb9086b11c23519e18a505d1141e6d37e81fa4191ac54f1aad085181fd0b8a5af83c4d71ae0abe2fb80d6ab0cab9196965b5aec8e03739ad57c3bbe948000d0a0ab4e29c6d4a7b24e556c7cae5c1e777057f193a4c289192b110d4f7fc26b635df5a88c6383a0b40924174faa5f1e542fb55449c9722000a640bbe2440a3b6176c486f04ef3-10001";

	
	/**
	 * constructor
	 * @param licencePublisher
	 */
	public BundleLocalLicenceSpecImpl(LicencePublisher licencePublisher){
		super(licencePublisher, productId, licenceMetadataSpecXml, aesSecretKeyStr, publicKeyStr);

	}



	
}
