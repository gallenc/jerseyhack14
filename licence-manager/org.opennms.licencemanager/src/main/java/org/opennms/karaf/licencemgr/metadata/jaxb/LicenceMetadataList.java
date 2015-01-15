package org.opennms.karaf.licencemgr.metadata.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="licenceMetadata")
@XmlAccessorType(XmlAccessType.NONE)
public class LicenceMetadataList  {

	@XmlElementWrapper(name="licenceMetadataList")
	@XmlElement(name="licenceMetadata")
	private List<LicenceMetadata> licenceMetadataList = new ArrayList<LicenceMetadata>();

	/**
	 * @return the licenceMetadataList
	 */
	public List<LicenceMetadata> getLicenceMetadataList() {
		return licenceMetadataList;
	}

	/**
	 * @param licenceMetadataList the licenceMetadataList to set
	 */
	public void setLicenceMetadataList(List<LicenceMetadata> licenceMetadataList) {
		this.licenceMetadataList = licenceMetadataList;
	}

}