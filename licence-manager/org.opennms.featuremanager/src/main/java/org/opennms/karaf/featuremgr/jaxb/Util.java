package org.opennms.karaf.featuremgr.jaxb;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * convenicence methods
 * @author admin
 *
 */
public class Util {

	/**
	 * convenience method to marshal jaxb classes for logs etc
	 * @param jaxbElement
	 * @return
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
	
}
