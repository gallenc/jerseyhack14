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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementRef;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.opennms.xmlns.xsd.eventconf.Event;
import org.opennms.xmlns.xsd.eventconf.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarshallerTest2 {
	private static final Logger LOG = LoggerFactory.getLogger(MarshallerTest2.class);

	private String eventFileName = "testeventfileIN.xml";

	private String marshalledEvents=null;

	private Events initialEventsConfig=null;

	private Events finalEventsConfig=null;

	private List<EventRowConfigObject> eventRowConfigObjectList = new ArrayList<EventRowConfigObject>();

	@Test 
	public void allTestsInOrder(){
		LOG.debug("@Test - allTestsInOrder().START");
		marshallerTest();
		unmarshallerTest();
		eventsConfigToRowsObjectTest();
		rowsObjectToEventsConfigTest();

		eventsConfigToWorkbookTest();
		workbookToEventsConfigTest();
		LOG.debug("@Test - allTestsInOrder().FINISH");
	}

	//@Test
	public void marshallerTest(){
		LOG.debug("@Test - marshallerTest().START");

		ClassLoader classLoader = getClass().getClassLoader();
		File eventsFile = new File(classLoader.getResource(eventFileName).getFile());
		LOG.debug("@Test - marshallerTest() eventsFile.getPath()"+eventsFile.getPath());

		StringBuilder fileContents = new StringBuilder((int)eventsFile .length());

		Scanner scanner=null;
		try {
			scanner = new Scanner(eventsFile );
			String lineSeparator = System.getProperty("line.separator");
			while(scanner.hasNextLine()) {        
				fileContents.append(scanner.nextLine() + lineSeparator);
			}

			marshalledEvents=fileContents.toString();
			LOG.debug("Config file contents marshalledEvents: "+marshalledEvents);

			EventsMarshaller eventsMarshaller = new EventsMarshaller();
			initialEventsConfig = eventsMarshaller.eventsFromXml(marshalledEvents);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (scanner != null) scanner.close();
		}
		LOG.debug("@Test - marshallerTest().FINISH");
	}

	//@Test
	public void unmarshallerTest(){
		LOG.debug("@Test - unmarshallerTest().START");

		EventsMarshaller eventsMarshaller = new EventsMarshaller();
		String unmarshalledEvents = eventsMarshaller.eventsToXml(initialEventsConfig);

		LOG.debug("umarshalledEvents: "+unmarshalledEvents);

		LOG.debug("@Test - unmarshallerTest().FINISH");
	}


	public void eventsConfigToRowsObjectTest(){
		LOG.debug("@Test - eventsConfigToRowsObjectTest().START");

		eventRowConfigObjectList.clear();

		List<Object> eventsContent = initialEventsConfig.getContent();
		for( Object obj: eventsContent){
			LOG.debug ("***************************************");
			LOG.debug (obj.toString());
			if (obj instanceof org.opennms.xmlns.xsd.eventconf.Event){
				Event event = (Event) obj;
				EventRowConfigObject eventRowConfigObject = ConfigRowTranslator.rowFromJxbEvent(event);
				eventRowConfigObjectList.add(eventRowConfigObject);
				LOG.debug("@Test - eventsConfigToRowsObjectTest() eventRowConfigObject.toString()="+eventRowConfigObject.toString());
			}
		}

		LOG.debug("@Test - eventsConfigToRowsObjectTest().FINISH");
	}

	public void rowsObjectToEventsConfigTest(){
		LOG.debug("@Test - rowsObjectToEventsConfigTest().START");

		finalEventsConfig = new Events();

		for (EventRowConfigObject eventRowConfigObject: eventRowConfigObjectList){
			Event event = ConfigRowTranslator.jaxbEventFromRow(eventRowConfigObject);
			finalEventsConfig.getContent().add(event);
		}

		EventsMarshaller eventsMarshaller = new EventsMarshaller();

		String finalEvents = eventsMarshaller.eventsToXml(finalEventsConfig);
		LOG.debug("@Test - rowsObjectToEventsConfigTest() finalEvents=\n"+finalEvents);

		LOG.debug("@Test - rowsObjectToEventsConfigTest().FINISH");
	}

	public void eventsConfigToWorkbookTest(){
		LOG.debug("@Test - eventsConfigToWorkbookTest().START");
		WorkbookTranslator workbookTranslator=null;
		try{
			WorkbookTranslatorFactory workbookTxFactory= new WorkbookTranslatorFactory();
			String workbookTranslatorPropertiesFilePath=null;
			String workbookFilePath = "eventsTestWorkBook1.xlsx";

			//loading and translating file
			workbookTranslator = workbookTxFactory.createWorkbookTranslator(workbookFilePath, workbookTranslatorPropertiesFilePath);
			String snames="";
			for(String sheetname: workbookTranslator.getSheetNames()){
				snames=snames+sheetname+" ";
			}
			LOG.debug("eventsConfigToWorkbookTest(): workbook sheet names before:"+snames);

			// will fail if sheet already exists
			try{
				workbookTranslator.createSheet("testSheet1");
			} catch (Exception e){
				LOG.debug("eventsConfigToWorkbookTest(): could not create sheet:"+e.getMessage());
			}

			snames="";
			for(String sheetname: workbookTranslator.getSheetNames()){
				snames=snames+sheetname+" ";
			}
			LOG.debug("eventsConfigToWorkbookTest(): workbook sheet names after:"+snames);

			workbookTranslator.createEventRows(eventRowConfigObjectList, "testSheet1");

		} catch (Exception e){
			LOG.debug("eventsConfigToWorkbookTest(): Exception", e);
		} finally{
			if (workbookTranslator!=null){
				workbookTranslator.close();
			}
		}
		LOG.debug("@Test - eventsConfigToWorkbookTest().FINISH");
	}


	public void workbookToEventsConfigTest(){
		LOG.debug("@Test - workbookToEventsConfigTest.START");
		
		WorkbookTranslator workbookTranslator=null;
		try{
			WorkbookTranslatorFactory workbookTxFactory= new WorkbookTranslatorFactory();
			String workbookTranslatorPropertiesFilePath="eventImporter.properties";
			String workbookFilePath = "eventsTestWorkBook1.xlsx";

			//loading and translating file
			workbookTranslator = workbookTxFactory.createWorkbookTranslator(workbookFilePath, workbookTranslatorPropertiesFilePath);
			String snames="";
			for(String sheetname: workbookTranslator.getSheetNames()){
				snames=snames+sheetname+" ";
			}
			LOG.debug("workbookToEventsConfigTest(): workbook sheetnames in sheet:"+snames);
			
			List<EventRowConfigObject> eventRows = workbookTranslator.retreiveEventRows("testSheet1");
			
			EventsMarshaller eventsMarshaller = new EventsMarshaller();
			
			Events sheetEventsConfig = new Events();

			for (EventRowConfigObject eventRowConfigObject: eventRows){
				Event event = ConfigRowTranslator.jaxbEventFromRow(eventRowConfigObject);
				sheetEventsConfig.getContent().add(event);
			}

			String sheetEvents = eventsMarshaller.eventsToXml(sheetEventsConfig);
			LOG.debug("@Test - workbookToEventsConfigTest() sheetEvents=\n"+sheetEvents);
			

		} catch (Exception e){
			LOG.debug("workbookToEventsConfigTest(): Exception", e);
		} finally{
			if (workbookTranslator!=null){
				workbookTranslator.close();
			}
		}
		LOG.debug("@Test - workbookToEventsConfigTest.FINISH");

	}


}
