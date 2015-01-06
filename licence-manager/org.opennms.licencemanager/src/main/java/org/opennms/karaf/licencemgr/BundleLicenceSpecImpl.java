package org.opennms.karaf.licencemgr;

import org.opennms.karaf.licencemgr.metadata.LicenceMetadata;
import org.opennms.karaf.licencepub.LicencePublisher;
import org.opennms.karaf.licencepub.LicenceSpecification;

public class BundleLicenceSpecImpl implements BundleLicenceSpec{
	
	private String productId="org.opennms/org.opennms.karaf.licencemanager.testbundle/1.0-SNAPSHOT";

	private String licenceMetadataSpecXml=null;
	private String aesSecretKeyStr=null;
	private String publicKeyStr=null;
	private LicencePublisher licencePublisher=null;
	private LicenceMetadata licenceMetadataSpec=null;
	
	/**
	 * constructor
	 * @param licencePublisher
	 */
	public BundleLicenceSpecImpl(LicencePublisher licencePublisher, String productId, String licenceMetadataSpecXml, String aesSecretKeyStr, String publicKeyStr){
		if (licencePublisher==null) throw new RuntimeException("BundleLicenceSpecImpl: licencePublisher cannot be null");
		if (productId==null) throw new RuntimeException("BundleLicenceSpecImpl: productId cannot be null");
		if (licenceMetadataSpecXml==null) throw new RuntimeException("BundleLicenceSpecImpl: licenceMetadataSpecXml cannot be null");
		if (aesSecretKeyStr==null) throw new RuntimeException("BundleLicenceSpecImpl: aesSecretKeyStr cannot be null");
		if (publicKeyStr==null) throw new RuntimeException("BundleLicenceSpecImpl: publicKeyStr cannot be null");
		this.licencePublisher=licencePublisher;
		this.productId= productId;
		this.licenceMetadataSpecXml=licenceMetadataSpecXml;
		this.aesSecretKeyStr=aesSecretKeyStr;
		this.publicKeyStr=publicKeyStr;
		
		licenceMetadataSpec=new LicenceMetadata();
		licenceMetadataSpec.fromXml(licenceMetadataSpecXml);
		
		
		LicenceSpecification licenceSpec= new LicenceSpecification(productId, 
				licenceMetadataSpec, aesSecretKeyStr, publicKeyStr);
		
		licencePublisher.addLicenceSpec(licenceSpec);
		System.out.println("Added licence specification for productId="+productId);
	}
	
	public void unregisterSpec(){
		if (licencePublisher!=null){
			licencePublisher.removeLicenceSpec(productId);
			System.out.println("Unregistered licence specification for productId="+productId);
		}
	}

	
}
