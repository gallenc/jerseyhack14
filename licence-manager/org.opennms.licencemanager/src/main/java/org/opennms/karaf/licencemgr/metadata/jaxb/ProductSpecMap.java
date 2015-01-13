package org.opennms.karaf.licencemgr.metadata.jaxb;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="product-specifications")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductSpecMap  {

	@XmlElementWrapper(name="product-spec-map")
	private SortedMap<String, ProductMetadata> productSpecMap = new TreeMap<String, ProductMetadata>();

	/**
	 * @return the productSpecMap
	 */
	public SortedMap<String, ProductMetadata> getProductSpecMap() {
		return productSpecMap;
	}

	/**
	 * @param productSpecMap the productSpecMap to set
	 */
	public void setProductSpecMap(SortedMap<String, ProductMetadata> productSpecMap) {
		this.productSpecMap = productSpecMap;
	}


}