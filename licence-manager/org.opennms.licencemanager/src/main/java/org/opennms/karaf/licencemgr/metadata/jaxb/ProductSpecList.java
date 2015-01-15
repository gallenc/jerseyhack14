package org.opennms.karaf.licencemgr.metadata.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="productSpecifications")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductSpecList  {

	
	@XmlElementWrapper(name="productSpecList")
	@XmlElement(name="productMetadata")
	private List<ProductMetadata> productSpecList = new ArrayList<ProductMetadata>();

	/**
	 * @return the productSpecList
	 */
	public List<ProductMetadata> getProductSpecList() {
		return productSpecList;
	}

	/**
	 * @param productSpecList the productSpecList to set
	 */
	public void setProductSpecList(List<ProductMetadata> productSpecList) {
		this.productSpecList = productSpecList;
	}

}