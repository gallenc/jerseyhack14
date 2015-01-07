package org.opennms.karaf.licencemgr;

import org.opennms.karaf.licencemgr.metadata.LicenceMetadata;
import org.opennms.karaf.licencepub.LicencePublisher;
import org.opennms.karaf.licencepub.LicenceSpecification;
import org.osgi.framework.BundleContext;
import org.opennms.karaf.licencemgr.BundleLicenceSpec;

public class BundleLicenceSpecImplOld implements BundleLicenceSpec {

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
	public BundleLicenceSpecImplOld(LicencePublisher licencePublisher, String productId, String licenceMetadataSpecXml, String aesSecretKeyStr, String publicKeyStr){
		if (licencePublisher==null) throw new RuntimeException("BundleLicenceSpecImplOld: licencePublisher cannot be null");
		if (productId==null) throw new RuntimeException("BundleLicenceSpecImplOld: productId cannot be null");
		if (licenceMetadataSpecXml==null) throw new RuntimeException("BundleLicenceSpecImplOld: licenceMetadataSpecXml cannot be null");
		if (aesSecretKeyStr==null) throw new RuntimeException("BundleLicenceSpecImplOld: aesSecretKeyStr cannot be null");
		if (publicKeyStr==null) throw new RuntimeException("BundleLicenceSpecImplOld: publicKeyStr cannot be null");
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
		System.out.println("Registered licence specification for productId="+productId);
	}

	public void unregisterSpec(){
		if (licencePublisher!=null){
			try{
				licencePublisher.removeLicenceSpec(productId);
				System.out.println("Unregistered licence specification for productId="+productId);
			} catch (Exception e){
				System.out.println("Problem unregistering licence specification for productId="+productId+"  "+e);
			} finally {
				licencePublisher=null; //release resources
			}
		}
	}

	@Override
	public void registerSpec() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BundleContext getBundleContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LicenceMetadata getLicenceMetadataSpec() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLicenceMetadataSpec(LicenceMetadata licenceMetadata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LicencePublisher getLicencePublisher() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLicencePublisher(LicencePublisher licencePublisher) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLicenceMetadataUri() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLicenceMetadataUri(String licenceMetadataUri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAesSecretKeyStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAesSecretKeyStr(String aesSecretKeyStr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPublicKeyStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPublicKeyStr(String publicKeyStr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProductId(String productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProductId() {
		// TODO Auto-generated method stub
		return null;
	}

}
