package org.opennms.karaf.licencemgr.metadata;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="LicenceMetadata")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType (propOrder={"productId","licensee","licensor","systemId","startDate","expiryDate","options"})
public class LicenceMetadata {

	/**
	 * productId is expected to contain the concatenated <groupId>/<artifactId>/<version>
     * such that mvn:<groupId>/<artifactId>/<version> will resolve an object
	 */
	String productId=null;
	
	/**
	 * systemId is expected to contain the unique identifier of the system on which which the licensed artifact 
	 * will be installed. The systemId is terminated with a CRC32 checksum seperated by a - symbol <systemId>-<CRC32>
	 */
	String systemId=null;
	
	/**
	 * startDate - the date from which the licence will be valid
	 */
	Date startDate=new Date();
	
	/**
	 * expiryDate - the date on which teh licencce will expire. If Null there is no expiry date.
	 */
	Date expiryDate=null;

	/**
	 * (Definition licensee n. a person (organisation) given a license by 
	 * government or under private agreement)
	 * 
	 * the name / address of the person / organisation who is granted the licence
	 */
	String licensee=null;
	
	/**
	 * (Definition: licensor n. a person who gives another a license, particularly 
	 *  a private party doing so, such as a business giving someone a license to sell its product)
	 * 
	 * the name / address of the person / organisation granting the licence
	 */
	String licensor=null;
	
	/**
	 * licence options. Each option contains a name/value pair and a description field.
	 * This is intended to grant/restrict access to particular features
	 */
	Set<OptionMetadata> options=new HashSet<OptionMetadata>();
	
	/**
	 * sets LicenceMetadata values of this object from another licenceMetadata object
	 * @param licenceMetadata
	 */
	public void setLicenceMetadata(LicenceMetadata licenceMetadata){
		this.productId=licenceMetadata.productId;
		this.systemId=licenceMetadata.systemId;
		this.startDate=licenceMetadata.startDate;
		this.expiryDate=licenceMetadata.expiryDate;
		this.licensee=licenceMetadata.licensee;
		this.licensor=licenceMetadata.licensor;
		this.options.clear();
		if (licenceMetadata.options!=null) this.options.addAll(licenceMetadata.options);
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
	@XmlElement(name="productId")
	public void setProductId(String productId) {
		this.productId = productId;
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
	@XmlElement(name="systemId")
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	@XmlElement(name="startDate")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	@XmlElement(name="expiryDate")
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the licensee
	 */
	public String getLicensee() {
		return licensee;
	}

	/**
	 * @param licensee the licensee to set
	 */
	@XmlElement(name="licensee")
	public void setLicensee(String licensee) {
		this.licensee = licensee;
	}

	/**
	 * @return the licensor
	 */
	public String getLicensor() {
		return licensor;
	}

	/**
	 * @param licensor the licensor to set
	 */
	@XmlElement(name="licensor")
	public void setLicensor(String licensor) {
		this.licensor = licensor;
	}

	/**
	 * @return the options
	 */
	public Set<OptionMetadata> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	@XmlElementWrapper(name="options")
	@XmlElement(name="option")
	public void setOptions(Set<OptionMetadata> options) {
		this.options = options;
	}

	
	/**
	 * @return XML encoded version of LicenceMetadata
	 */
	public String toXml(){

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(org.opennms.karaf.licencemgr.metadata.ObjectFactory.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(this,stringWriter);
			return stringWriter.toString();

		} catch (JAXBException e) {
			throw new RuntimeException("Problem marshalling LicenceMetadata:",e);
		}
	}

	/**
	 * load this object with data from xml string
	 * @parm XML encoded version of LicenceMetadata
	 */
	public void fromXml(String xmlStr){

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(org.opennms.karaf.licencemgr.metadata.ObjectFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xmlStr);
			LicenceMetadata licenceMetadata= (LicenceMetadata) jaxbUnmarshaller.unmarshal(reader);
			this.productId=licenceMetadata.productId;
			this.systemId=licenceMetadata.systemId;
			this.startDate=licenceMetadata.startDate;
			this.expiryDate=licenceMetadata.expiryDate;
			this.licensee=licenceMetadata.licensee;
			this.licensor=licenceMetadata.licensor;
			this.options.clear();
			if (licenceMetadata.options!=null) this.options.addAll(licenceMetadata.options);
		} catch (JAXBException e) {
			throw new RuntimeException("Problem unmarshalling LicenceMetadata:",e);
		}
	}

	/**
	 * @return Hex encoded string of XML version of Metadata
	 * @throws UnsupportedEncodingException
	 */
	public String toHexString(){
		try {
			String xmlStr = toXml();
			byte[] array = xmlStr.getBytes("UTF-8");
			return DatatypeConverter.printHexBinary(array);
		} catch ( Exception e) {
			throw new RuntimeException("problem converting LicenceMetadata to hexString:",e);
		}
	}

	/**
	 * imports licence metadata from hex encoded version of XML metadata
	 * @param hexString
	 * 
	 */
	public void fromHexString(String hexString){
		try {
			byte[] array = DatatypeConverter.parseHexBinary(hexString);
			String xmlStr = new String(array, "UTF-8");
			this.fromXml(xmlStr);
		} catch ( Exception e) {
			throw new RuntimeException("problem importing LicenceMetadata from hexString:",e);
		}
	}

	/**
	 * Returns sha256 hash of XML encoded LicenceMetadata
	 * @return digest sha256 hash of XML encoded LicenceMetadata
	 */
	public String sha256Hash() {

		try {
			String xmlStr = toXml();
			byte[] array = xmlStr.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(array);
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest);
		} catch (Exception e) {
			throw new RuntimeException("problem calculating sha-256 hash for LicenceMetadata:",e);
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result
				+ ((licensee == null) ? 0 : licensee.hashCode());
		result = prime * result
				+ ((licensor == null) ? 0 : licensor.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result
				+ ((systemId == null) ? 0 : systemId.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LicenceMetadata other = (LicenceMetadata) obj;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		if (licensee == null) {
			if (other.licensee != null)
				return false;
		} else if (!licensee.equals(other.licensee))
			return false;
		if (licensor == null) {
			if (other.licensor != null)
				return false;
		} else if (!licensor.equals(other.licensor))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (systemId == null) {
			if (other.systemId != null)
				return false;
		} else if (!systemId.equals(other.systemId))
			return false;
		return true;
	}

}
