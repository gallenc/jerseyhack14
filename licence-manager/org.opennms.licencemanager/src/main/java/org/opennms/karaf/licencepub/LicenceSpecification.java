package org.opennms.karaf.licencepub;

import org.opennms.karaf.licencemgr.PublisherKeys;
import org.opennms.karaf.licencemgr.metadata.LicenceMetadata;

public class LicenceSpecification {

	private final String aesSecretKeyStr;
	private final String publicKeyStr;
	private final LicenceMetadata licenceMetadataSpec;
    private final String productId;
    
    public LicenceSpecification(String productId, LicenceMetadata licenceMetadataSpec, PublisherKeys publisherKeys){
    	this(productId, licenceMetadataSpec, publisherKeys.getAesSecretKeyStr(), publisherKeys.getPublicKeyStr());
    }
	
	public LicenceSpecification(String productId, LicenceMetadata licenceMetadataSpec, String aesSecretKeyStr, 
			String publicKeyStr) {
		super();
		if (productId==null) throw new IllegalArgumentException("productId cannot be null in LicenceSpecification");
		if (licenceMetadataSpec==null) throw new IllegalArgumentException("licenceMetadataSpec cannot be null in LicenceSpecification");
		if (aesSecretKeyStr==null) throw new IllegalArgumentException("aesSecretKeyStr cannot be null in LicenceSpecification");
		if (publicKeyStr==null) throw new IllegalArgumentException("publicKeyStr cannot be null in LicenceSpecification");
		if (! productId.equals(licenceMetadataSpec.getProductId())) 
			throw new IllegalArgumentException("productId in LicenceSpecification constructor (="+productId
					+ ") does not match productId in licenceMetadataSpec(="+licenceMetadataSpec.getProductId()+")");
		
		this.productId = productId;
		this.aesSecretKeyStr = aesSecretKeyStr;
		this.publicKeyStr = publicKeyStr;
		this.licenceMetadataSpec = licenceMetadataSpec;
	}
	
	/**
	 * @return the aesSecretKeyStr
	 */
	public String getAesSecretKeyStr() {
		return aesSecretKeyStr;
	}

	/**
	 * @return the publicKeyStr
	 */
	public String getPublicKeyStr() {
		return publicKeyStr;
	}

	/**
	 * @return the licenceMetadataSpec
	 */
	public LicenceMetadata getLicenceMetadataSpec() {
		return licenceMetadataSpec;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
}
