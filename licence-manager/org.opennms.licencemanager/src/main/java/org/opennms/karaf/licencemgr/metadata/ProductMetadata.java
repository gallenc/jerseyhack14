package org.opennms.karaf.licencemgr.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="option")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductMetadata {

	//productId is expected to contain the concatenated <groupId>/<artifactId>/<version>
	//such that mvn:<groupId>/<artifactId>/<version> will resolve an object
	String productId=null;
	
	//product name - plain text name of product- usually maven <name> field
	String productName=null;
	
	// description of product usually maven <description>
	String productDescription=null;
	
	// URL for more product details usually matches maven <url> field
	String productUrl=null;
	
	// Organization which created the product. Usually matchers the maven <organization> field
	String organization=null;

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	@XmlElement(name="productId")
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	@XmlElement(name="productName")
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}

	/**
	 * @param productDescription the productDescription to set
	 */
	@XmlElement(name="productDescription")
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/**
	 * @return the productUrl
	 */
	public String getProductUrl() {
		return productUrl;
	}

	/**
	 * @param productUrl the productUrl to set
	 */
	@XmlElement(name="productUrl")
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	@XmlElement(name="organization")
	public void setOrganization(String organization) {
		this.organization = organization;
	}
			
	
	
}
