/* ******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2010-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.eventgateway.test.manual;


import java.io.File;
import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;
import org.opennms.features.eventgateway.jaxb.EventGatewayAuthKeyConfig;
import org.opennms.features.eventgateway.jaxb.EventGatewayConfig;
import org.opennms.features.eventgateway.jaxb.EventGatewayConfigurations;
import org.opennms.features.eventgateway.jaxb.NameValuePair;
import org.opennms.features.eventgateway.jaxb.RestEventMessage;
import org.opennms.features.eventgateway.jaxb.RestEventReplyMessage;
import org.opennms.netmgt.model.events.EventBuilder;
import org.opennms.netmgt.xml.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

/**
 * Test of marshaling and unmarshaling messages and configuration classes
 * for eventgateway
 * 
 * @author Craig Gallen cgallen@opennms.org
 *
 */
public class RestMarshalTest1  {
	//private static final Logger LOG = LoggerFactory.getLogger(RestMarshalTest1.class);

	private static JAXBContext jaxbContext=null;
	
    @Before
	public void testCreateJaxbContext(){
		System.out.println("start of test:testCreateJaxbContext()");
		try {
			// check slf4j settings
			//LOG.debug("debug message");
			//LOG.warn("warn message");
			//LOG.info("info message");
			
			jaxbContext = JAXBContext.newInstance("org.opennms.netmgt.xml.event:org.opennms.features.eventgateway.jaxb");
			System.out.println("end of test:testCreateJaxbContext()");
		} catch (JAXBException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Test for marshalling and unmarshalling RestEventMessage
	 */
    @Test
	public void testMarshalUnmarshalRestEventMessage(){
		System.out.println("start of test:testMarshalUnmarshalRestEventMessage()");
		
		try {
			// create test file
			String testFileName=this.getClass().getSimpleName()+"_EventMessage_File.xml";
			File file = new File("target/"+testFileName);
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.close();
			System.out.println("file location:" + file.getAbsolutePath());

			// *****************
			// create RestEventMessage
			// *****************
			
			RestEventMessage restEventMessage = new RestEventMessage();
			
			// add events
			for(int i=0;i<10;i++){
				//Event evt;
				//EventBuilder eb = new EventBuilder(evt);
				Event onmsXmlEvent=new Event();
				onmsXmlEvent.setUei("testUEI:"+i);
				restEventMessage.getEventList().add(onmsXmlEvent);
			}

			NameValuePair replyValue=new NameValuePair();
			replyValue.setName("username");
			replyValue.setValue("uservalue");
			restEventMessage.getReplyValues().add(replyValue);
			// **********************
			// marshal test file
			// **********************

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://xmlns.opennms.org/xsd/config/pmatrix pmatrixConfig.xsd");

			//jaxbMarshaller.marshal(restEventMessage, file);
			jaxbMarshaller.marshal(restEventMessage, file);
			
			//jaxbMarshaller.marshal(pmatrixSpecificationList_context, file);

			// **********************
			// unmarshal test file
			// **********************

			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();

			//Object o = jaxbUnMarshaller.unmarshal( new StringReader( marshalledXml )  );

			Object o = jaxbUnMarshaller.unmarshal( file  );

			System.out.println("o.tostring:"+o.toString());
			if (o instanceof RestEventMessage){
				System.out.println("unmarshalled RestEventMessage:");
				System.out.println( (RestEventMessage) o);
			} else System.out.println("cant unmarshal object:");



		} catch (JAXBException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("end of test:testMarshalUnmarshalRestEventMessage()");

	}

	/**
	 * Test for marshalling and unmarshalling RestEventReplyMessage
	 */
    @Test
	public void testMarshalUnmarshalRestEventReplyMessage(){
		System.out.println("start of test:testMarshalUnmarshalRestEventReplyMessage()");
		
		try {
			// create test file
			String testFileName=this.getClass().getSimpleName()+"_EventReplyMessage_File.xml";
			File file = new File("target/"+testFileName);
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.close();
			System.out.println("file location:" + file.getAbsolutePath());

			// *****************
			// create RestEventReplyMessage
			// *****************
			
			RestEventReplyMessage restEventReplyMessage = new RestEventReplyMessage();
			
			// add reply values
			for(int i=0;i<=5;i++){
				NameValuePair replyValuePair= new NameValuePair();
				replyValuePair.setName("name:"+i);
				replyValuePair.setValue("value:"+i);
				restEventReplyMessage.getReplyValues().add(replyValuePair);
			}

			// **********************
			// marshal test file
			// **********************

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://xmlns.opennms.org/xsd/config/pmatrix pmatrixConfig.xsd");

			//jaxbMarshaller.marshal(RestEventReplyMessage, file);
			jaxbMarshaller.marshal(restEventReplyMessage, file);
			
			//jaxbMarshaller.marshal(pmatrixSpecificationList_context, file);

			// **********************
			// unmarshal test file
			// **********************

			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();

			//Object o = jaxbUnMarshaller.unmarshal( new StringReader( marshalledXml )  );

			Object o = jaxbUnMarshaller.unmarshal( file  );

			System.out.println("o.tostring:"+o.toString());
			if (o instanceof RestEventReplyMessage){
				System.out.println("unmarshalled list:");
				System.out.println( (RestEventReplyMessage) o);
			} else System.out.println("cant unmarshal object:");

		} catch (JAXBException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("end of test:testMarshalUnmarshalRestEventReplyMessage()");

	}
    
    
	/**
	 * Test for marshalling and unmarshalling EventGatewayConfigurations
	 */
    @Test
	public void testMarshalUnmarshalEventGatewayConfigurations(){
		System.out.println("start of test:testMarshalUnmarshalEventGatewayConfigurations()");
		
		try {
			// create test file
			String testFileName=this.getClass().getSimpleName()+"_EventGatewayConfigurations_File.xml";
			File file = new File("target/"+testFileName);
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.close();
			System.out.println("file location:" + file.getAbsolutePath());

			// *****************
			// create EventGatewayConfigurations
			// *****************
			
			EventGatewayConfigurations eventGatewayConfigurations = new EventGatewayConfigurations();
			
			EventGatewayConfig eventGatewayConfig1= new EventGatewayConfig() ;
			eventGatewayConfigurations.getEventGatewayConfigsList().add(eventGatewayConfig1);
			
			eventGatewayConfig1.setReference("gateway1");
			eventGatewayConfig1.setUsername("username1");
			eventGatewayConfig1.setPassword("username1pass");
			
			EventGatewayAuthKeyConfig authKeyConfig=new EventGatewayAuthKeyConfig();
			authKeyConfig.setAuthKeylength(5);
			authKeyConfig.setMaxCurrentAuthKeyAge(1000*60*60 );   // 1 hr
			authKeyConfig.setMaxTimeKeepOldAuthKeys(1000*60*90 ); // 1.5 hr
			
			eventGatewayConfig1.setAuthKeyConfig(authKeyConfig);
			
			// add name value pairs
			for(int i=0;i<=5;i++){
				NameValuePair replyValuePair= new NameValuePair();
				replyValuePair.setName("name:"+i);
				replyValuePair.setValue("value:"+i);
				eventGatewayConfig1.getStaticReplyValues().add(replyValuePair);
			}
			
			// add event ueis
			for(int i=0;i<=5;i++){
				String uei="uei:"+i;
				eventGatewayConfig1.getUeiList().add(uei);
			}
			
			// **********************
			// marshal test file
			// **********************

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://xmlns.opennms.org/xsd/config/pmatrix pmatrixConfig.xsd");

			//jaxbMarshaller.marshal(EventGatewayConfigurations, file);
			jaxbMarshaller.marshal(eventGatewayConfigurations, file);
			
			//jaxbMarshaller.marshal(pmatrixSpecificationList_context, file);

			// **********************
			// unmarshal test file
			// **********************

			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();

			//Object o = jaxbUnMarshaller.unmarshal( new StringReader( marshalledXml )  );

			Object o = jaxbUnMarshaller.unmarshal( file  );

			System.out.println("o.tostring:"+o.toString());
			if (o instanceof EventGatewayConfigurations){
				System.out.println("unmarshalled EventGatewayConfigurations:");
				System.out.println( (EventGatewayConfigurations) o);
			} else System.out.println("cant unmarshal object:");



		} catch (JAXBException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("end of test:testMarshalUnmarshalEventGatewayConfigurations()");

	}
}
