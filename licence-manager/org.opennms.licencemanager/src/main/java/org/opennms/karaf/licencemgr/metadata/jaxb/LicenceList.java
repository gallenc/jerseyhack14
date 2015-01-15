package org.opennms.karaf.licencemgr.metadata.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="licences")
@XmlAccessorType(XmlAccessType.NONE)
public class LicenceList  {

	@XmlElementWrapper(name="licenceList")
	@XmlElement(name="licenceEntry")
	private List<LicenceEntry> licenceList = new ArrayList<LicenceEntry>();

	/**
	 * @return the licenceList
	 */
	public List<LicenceEntry> getLicenceList() {
		return licenceList;
	}

	/**
	 * @param licenceList the licenceList to set
	 */
	public void setLicenceList(List<LicenceEntry> licenceList) {
		this.licenceList = licenceList;
	}


}