package org.opennms.karaf.licencemgr;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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


@XmlRootElement(name="LicenceMetadata")
@XmlAccessorType(XmlAccessType.NONE)
public class LicenceMetadata {


	String productId=null;
	String systemId=null;
	Date startDate=new Date();
	Date endDate=null;

	String liscencee=null;
	String liscencor=null;
	Map<String,String> options=new HashMap<String, String>();
	
	/**
	 * sets LicenceMetadata values of this object from another licenceMetadata object
	 * @param licenceMetadata
	 */
	public void setLicenceMetadata(LicenceMetadata licenceMetadata){
		this.productId=licenceMetadata.productId;
		this.systemId=licenceMetadata.systemId;
		this.startDate=licenceMetadata.startDate;
		this.endDate=licenceMetadata.endDate;
		this.liscencee=licenceMetadata.liscencee;
		this.liscencor=licenceMetadata.liscencor;
		this.options.clear();
		if (licenceMetadata.options!=null) this.options.putAll(licenceMetadata.options);
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
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	@XmlElement(name="endDate")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public Map<String, String> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	@XmlElementWrapper(name="options")
	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	
	/**
	 * @return XML encoded version of LicenceMetadata
	 */
	public String toXml(){

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(LicenceMetadata.class);
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
			JAXBContext jaxbContext = JAXBContext.newInstance(LicenceMetadata.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xmlStr);
			LicenceMetadata licenceMetadata= (LicenceMetadata) jaxbUnmarshaller.unmarshal(reader);
			this.productId=licenceMetadata.productId;
			this.systemId=licenceMetadata.systemId;
			this.startDate=licenceMetadata.startDate;
			this.endDate=licenceMetadata.endDate;
			this.liscencee=licenceMetadata.liscencee;
			this.liscencor=licenceMetadata.liscencor;
			this.options.clear();
			if (licenceMetadata.options!=null) this.options.putAll(licenceMetadata.options);
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
