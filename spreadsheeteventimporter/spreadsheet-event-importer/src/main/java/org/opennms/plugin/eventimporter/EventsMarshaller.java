/*
 * Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opennms.plugin.eventimporter;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.opennms.xmlns.xsd.eventconf.Events;

public class EventsMarshaller {
	
	/**
	 * @return XML encoded version of org.opennms.xmlns.xsd.eventconf.Events
	 */
	public String eventsToXml(Events eventsConfig){

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(org.opennms.xmlns.xsd.eventconf.ObjectFactory.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // pretty output
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(eventsConfig,stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			throw new RuntimeException("Problem marshalling event config data:",e);
		}
	}

	/**
	 * returns org.opennms.xmlns.xsd.eventconf.Events object marshalled from from xml string
	 * @parm XML encoded version of org.opennms.xmlns.xsd.eventconf.Events
	 */
	public Events eventsFromXml(String xmlStr){

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(org.opennms.xmlns.xsd.eventconf.ObjectFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xmlStr);
			Events eventsConfig= (Events) jaxbUnmarshaller.unmarshal(reader);
			return eventsConfig;
		} catch (JAXBException e) {
			throw new RuntimeException("Problem unmarshalling ProductMetadata:",e);
		}
	}
}
