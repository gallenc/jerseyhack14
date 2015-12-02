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

	private String eventFileName = "testeventfile.xml";

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
		LOG.debug("@Test - allTestsInOrder().END");
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

		LOG.debug("@Test - eventsConfigToRowsObjectTest().End");
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

		LOG.debug("@Test - rowsObjectToEventsConfigTest().End");
	}

	public void eventsConfigToWorkbookTest(){
		try{
			WorkbookTranslatorFactory workbookTxFactory= new WorkbookTranslatorFactory();
			String workbookTranslatorPropertiesFilePath=null;
			String workbookFilePath = "eventsTestWorkBook1.xlsx";

			//loading and translating file
			WorkbookTranslator workbookTranslator = workbookTxFactory.createWorkbookTranslator(workbookFilePath, workbookTranslatorPropertiesFilePath);
			LOG.debug("eventsConfigToWorkbookTest(): workbook sheet names before:");
			for(String sheetname: workbookTranslator.getSheetNames()){
				LOG.debug("       sheetname: "+sheetname);
			}
			
			workbookTranslator.createSheet("testSheet1");
			
			LOG.debug("eventsConfigToWorkbookTest(): workbook sheet names after:");
			for(String sheetname: workbookTranslator.getSheetNames()){
				LOG.debug("       sheetname: "+sheetname);
			}
			workbookTranslator.createEventRows(eventRowConfigObjectList, "testSheet1");
			
			
			workbookTranslator.close();
		} catch (Exception e){
			LOG.debug("eventsConfigToWorkbookTest(): Exception", e);
		}
	}


	public void workbookToEventsConfigTest(){

	}


}
