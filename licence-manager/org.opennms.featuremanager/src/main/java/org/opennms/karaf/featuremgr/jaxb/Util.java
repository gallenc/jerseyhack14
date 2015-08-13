package org.opennms.karaf.featuremgr.jaxb;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 * convenicence methods
 * @author admin
 *
 */
public class Util {

	/**
	 * convenience method to marshal jaxb classes for logs etc
	 * @param jaxbElement
	 * @return xml string version of supplied jaxbElement
	 */
	public static String toXml(Object jaxbElement) {
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(org.opennms.karaf.featuremgr.jaxb.ObjectFactory.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(jaxbElement, stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			throw new RuntimeException("Problem marshalling jaxbElement:"
					+ jaxbElement.getClass().getName(), e);
		}
	}
	
	/**
	 * convenience method to unmarshal jaxb classes for logs and rest interface etc
	 * @param xml string version of jaxbElement
	 * @return jaxbElement
	 */
	public static Object fromXml(String xmlStr){

		Object jaxbElement;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(org.opennms.karaf.featuremgr.jaxb.ObjectFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xmlStr);
			jaxbElement = jaxbUnmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException("Problem unmarshalling xmlStr:",e);
		}
		return jaxbElement;
	}
	
}
