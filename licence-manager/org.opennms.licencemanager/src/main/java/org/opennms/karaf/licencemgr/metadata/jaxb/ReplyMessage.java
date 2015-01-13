package org.opennms.karaf.licencemgr.metadata.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Replay message used to wrap all correct replies from rest service
 * @author cgallen
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ReplyMessage {
	
	@XmlElement()
	private String replyComment=null;

	@XmlElement()
	private String licence=null;

	@XmlElement()
	private String systemId=null;
		
	@XmlElement()
	private String productId=null;
	
	@XmlElement()
	private LicenceSpecification licenceSpec=null;
	
	@XmlElement()
	private LicenceMetadata licenceMetadata=null;
	
	@XmlElement()
	private ProductMetadata productMetadata=null;
	
	@XmlElement()
	private String checksum=null;

	/**
	 * @return the replyComment
	 */
	public String getReplyComment() {
		return replyComment;
	}

	/**
	 * @param replyComment the replyComment to set
	 */
	public void setReplyComment(String replyComment) {
		this.replyComment = replyComment;
	}

	/**
	 * @return the licence
	 */
	public String getLicence() {
		return licence;
	}

	/**
	 * @param licence the licence to set
	 */
	public void setLicence(String licence) {
		this.licence = licence;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the licenceSpec
	 */
	public LicenceSpecification getLicenceSpec() {
		return licenceSpec;
	}

	/**
	 * @param licenceSpec the licenceSpec to set
	 */
	public void setLicenceSpec(LicenceSpecification licenceSpec) {
		this.licenceSpec = licenceSpec;
	}

	/**
	 * @return the licenceMetadata
	 */
	public LicenceMetadata getLicenceMetadata() {
		return licenceMetadata;
	}

	/**
	 * @param licenceMetadata the licenceMetadata to set
	 */
	public void setLicenceMetadata(LicenceMetadata licenceMetadata) {
		this.licenceMetadata = licenceMetadata;
	}

	/**
	 * @return the productMetadata
	 */
	public ProductMetadata getProductMetadata() {
		return productMetadata;
	}

	/**
	 * @param productMetadata the productMetadata to set
	 */
	public void setProductMetadata(ProductMetadata productMetadata) {
		this.productMetadata = productMetadata;
	}

	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	
}
