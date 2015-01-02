package org.opennms.karaf.licencemgr;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;

import org.opennms.karaf.licencemgr.metadata.Licence;
import org.opennms.karaf.licencemgr.metadata.LicenceMetadata;

@XmlRootElement(name="LicenceServiceData")
@XmlAccessorType(XmlAccessType.NONE)
public class LicenceServiceImpl implements LicenceService {

	// used to store location of persistence file
	private String fileUri=null;

	@XmlElementWrapper(name="licenceMap")
	private SortedMap<String, String> licenceMap = new TreeMap<String, String>();

	@XmlElement
	private String systemId="NOT_SET";

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	@Override
	public synchronized void addLicence(String licenceStrPlusCrc) {
		if (licenceStrPlusCrc==null) throw new RuntimeException("licenceStrPlusCrc cannot be null");
		LicenceMetadata unverifiedMetadata = Licence.getUnverifiedMetadata(licenceStrPlusCrc);
		String productId = unverifiedMetadata.getProductId();
		licenceMap.put(productId, licenceStrPlusCrc);
		persist();
	}

	@Override
	public synchronized boolean removeLicence(String productId) {
		if (productId==null) throw new RuntimeException("productID cannot be null");
		if (! licenceMap.containsKey(productId)) {
			return false;
		} else{
			licenceMap.remove(productId);
			persist();
			return true;
		}
	}

	@Override
	public synchronized String getLicence(String productId) {
		if (productId==null) throw new RuntimeException("productID cannot be null");
		return licenceMap.get(productId);
	}


	@Override
	public synchronized Map<String, String> getLicenceMap() {
		// returns an instance of the map because it may change after returned.
		Map<String, String> map = new TreeMap<String, String>(licenceMap);
		return map;
	}

	@Override
	public synchronized String getSystemId() {
		return systemId;
	}

	@Override
	public synchronized void setSystemId(String systemId) {
		StringCrc32Checksum stringCrc32Checksum = new StringCrc32Checksum();
		if (! stringCrc32Checksum.checkCRC(systemId)){
			throw new RuntimeException("Incorrect checksum or format for systemId="+systemId);
		};

		this.systemId=systemId;
		persist();
	}

	/**
	 * Makes a rendom system instance value
	 */
	@Override
	public synchronized String makeSystemInstance() {

		// create random object
		Random randomgen = new Random();

		// get next long value 
		long systemIdValue = randomgen.nextLong();

		// make hex string
		String hexSystemIdString=Long.toHexString(systemIdValue);
		StringCrc32Checksum stringCrc32Checksum = new StringCrc32Checksum();
		String systemId = stringCrc32Checksum.addCRC(hexSystemIdString);
		this.setSystemId(systemId);
		return systemId;
	}

	/**
	 * adds checksum onto end of given string separated by - character
	 */
	@Override
	public String checksumForString(String valueString) {
		if (valueString==null) throw new RuntimeException("valueString cannot be null");
		StringCrc32Checksum stringCrc32Checksum = new StringCrc32Checksum();
		return stringCrc32Checksum.addCRC(valueString);
	}

	public synchronized void persist(){
		if (fileUri==null) throw new RuntimeException("fileUri must be set for licence manager");

		try {

			File licenceManagerFile = new File(fileUri);
			JAXBContext jaxbContext = JAXBContext.newInstance(LicenceServiceImpl.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.marshal(this, file);
			jaxbMarshaller.marshal(this, licenceManagerFile);

		} catch (JAXBException e) {
			throw new RuntimeException("Problem persisting Licence Manager Data",e);
		}
	}

	public synchronized void load(){
		if (fileUri==null) throw new RuntimeException("fileUri must be set for licence manager");

		try {

			File licenceManagerFile = new File(fileUri);

			if (licenceManagerFile.exists()) {
				JAXBContext jaxbContext = JAXBContext.newInstance(LicenceServiceImpl.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				LicenceServiceImpl licenceServiceImpl = (LicenceServiceImpl) jaxbUnmarshaller.unmarshal(licenceManagerFile);

				this.licenceMap.clear();
				this.licenceMap.putAll(licenceServiceImpl.getLicenceMap());
				this.systemId= licenceServiceImpl.getSystemId();
			}

		} catch (JAXBException e) {
			throw new RuntimeException("Problem loading Licence Manager Data",e);
		}
	}

	@Override
	public synchronized void deleteLicences() {
		licenceMap.clear();
		
	}



}
