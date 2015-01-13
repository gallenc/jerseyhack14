package org.opennms.karaf.licencemgr.metadata.jaxb;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="licence-specifications")
@XmlAccessorType(XmlAccessType.NONE)
public class LicenceSpecMap  {

	@XmlElementWrapper(name="licence-spec-map")
	private SortedMap<String, LicenceSpecification> licenceSpecMap = new TreeMap<String, LicenceSpecification>();

	/**
	 * @return the licenceSpecMap
	 */
	public SortedMap<String, LicenceSpecification> getLicenceSpecMap() {
		return licenceSpecMap;
	}

	/**
	 * @param licenceSpecMap the licenceSpecMap to set
	 */
	public void setLicenceSpecMap(SortedMap<String, LicenceSpecification> licenceSpecMap) {
		this.licenceSpecMap = licenceSpecMap;
	}
	
}