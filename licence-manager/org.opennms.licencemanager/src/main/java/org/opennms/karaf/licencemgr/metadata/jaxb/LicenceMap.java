package org.opennms.karaf.licencemgr.metadata.jaxb;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="licences")
@XmlAccessorType(XmlAccessType.NONE)
public class LicenceMap  {

	@XmlElementWrapper(name="licence-map")
	private SortedMap<String, String> licenceMap = new TreeMap<String, String>();

	/**
	 * @return the licenceMap
	 */
	public SortedMap<String, String> getLicenceMap() {
		return licenceMap;
	}

	/**
	 * @param licenceMap the licenceMap to set
	 */
	public void setLicenceMap(SortedMap<String, String> licenceMap) {
		this.licenceMap = licenceMap;
	}
	
}