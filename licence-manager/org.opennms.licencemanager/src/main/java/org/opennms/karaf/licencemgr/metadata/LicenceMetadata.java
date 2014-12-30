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
@XmlType (propOrder={"productId","liscencee","liscencor","systemId","startDate","expiryDate","options"})
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
	 * the name / address of the person / organisation granted the licence
	 */
	String liscencee=null;
	
	/**
	 * the name / address of the person / organisation granting the licence
	 */
	String liscencor=null;
	
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
		this.liscencee=licenceMetadata.liscencee;
		this.liscencor=licenceMetadata.liscencor;
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
	 * @return the liscencee
	 */
	public String getLiscencee() {
		return liscencee;
	}

	/**
	 * @param liscencee the liscencee to set
	 */
	@XmlElement(name="liscencee")
	public void setLiscencee(String liscencee) {
		this.liscencee = liscencee;
	}

	/**
	 * @return the liscencor
	 */
	public String getLiscencor() {
		return liscencor;
	}

	/**
	 * @param liscencor the liscencor to set
	 */
	@XmlElement(name="liscencor")
	public void setLiscencor(String liscencor) {
		this.liscencor = liscencor;
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
			this.liscencee=licenceMetadata.liscencee;
			this.liscencor=licenceMetadata.liscencor;
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
	 * importes licence metadata from hex encoded version of xML metadata
	 * @param hexString
	 * @throws UnsupportedEncodingException
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

}
