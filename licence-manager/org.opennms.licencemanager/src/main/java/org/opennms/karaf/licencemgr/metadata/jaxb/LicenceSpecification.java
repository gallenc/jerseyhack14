package org.opennms.karaf.licencemgr.metadata.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.karaf.licencemgr.PublisherKeys;

@XmlRootElement(name="LicenceSpecification")
@XmlAccessorType(XmlAccessType.NONE)
public class LicenceSpecification {

	@XmlElement()
	private String aesSecretKeyStr;
	
	@XmlElement()
	private String publicKeyStr;
	
	@XmlElement()
	private LicenceMetadata licenceMetadataSpec;
	
	@XmlElement()
    private String productId;
	
	/**
	 * Constructor for use by jaxb only
	 * LicenceSpecification has no setters as an immutable object.
	 * (you should use the constructors with arguments for all other purposes)
	 */
	public LicenceSpecification(){
		super();
	}
    
	/**
	 * Constructor
	 * @param productId
	 * @param licenceMetadataSpec
	 * @param publisherKeys
	 */
    public LicenceSpecification(String productId, LicenceMetadata licenceMetadataSpec, PublisherKeys publisherKeys){
    	this(productId, licenceMetadataSpec, publisherKeys.getAesSecretKeyStr(), publisherKeys.getPublicKeyStr());
    }
	
    /**
     * Constructor
     * @param productId
     * @param licenceMetadataSpec
     * @param aesSecretKeyStr
     * @param publicKeyStr
     */
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
