package org.opennms.karaf.licencemgr.metadata.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="licenceEntry")
@XmlAccessorType(XmlAccessType.NONE)
public class LicenceEntry {

	@XmlElement(name="productId")
	String productId=null;
	
	@XmlElement(name="licenceStr")
	String licenceStr=null;

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
	 * @return the licenceStr
	 */
	public String getLicenceStr() {
		return licenceStr;
	}

	/**
	 * @param licenceStr the licenceStr to set
	 */
	public void setLicenceStr(String licenceStr) {
		this.licenceStr = licenceStr;
	}
}
