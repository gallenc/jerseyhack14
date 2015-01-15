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

@XmlRootElement(name="licenceSpecifications")
@XmlAccessorType(XmlAccessType.NONE)
public class LicenceSpecList  {

	@XmlElementWrapper(name="licenceSpecificationList")
	@XmlElement(name="licenceSpecification")
	private List<LicenceSpecification> licenceSpecList = new ArrayList<LicenceSpecification>();

	/**
	 * @return the licenceSpecList
	 */
	public List<LicenceSpecification> getLicenceSpecList() {
		return licenceSpecList;
	}

	/**
	 * @param licenceSpecList the licenceSpecList to set
	 */
	public void setLicenceSpecList(List<LicenceSpecification> licenceSpecMap) {
		this.licenceSpecList = licenceSpecMap;
	}
	
}