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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.opennms.xmlns.xsd.eventconf.Event;
import org.opennms.xmlns.xsd.eventconf.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Event importer imports events from xcel spreadsheet
 */
public class SpreadsheetEventConfMain {
	private static final Logger LOG = LoggerFactory.getLogger(SpreadsheetEventConfMain.class);

	private String workbookFilePath=null;

	private String propertiesFilePath=null;

	private String eventsFilePath=null;

	private String spreadsheetName=null;

	public String getWorkbookFilePath() {
		return workbookFilePath;
	}

	public void setWorkbookFilePath(String workbookFilePath) {
		this.workbookFilePath = workbookFilePath;
	}

	public String getPropertiesFilePath() {
		return propertiesFilePath;
	}

	public void setPropertiesFilePath(String propertiesFilePath) {
		this.propertiesFilePath = propertiesFilePath;
	}

	public String getEventsFilePath() {
		return eventsFilePath;
	}

	public void setEventsFilePath(String eventsFilePath) {
		this.eventsFilePath = eventsFilePath;
	}

	public String getSpreadsheetName() {
		return spreadsheetName;
	}

	public void setSpreadsheetName(String spreadsheetName) {
		this.spreadsheetName = spreadsheetName;
	}

	/**
	 * converts an events config file to a spreadsheet
	 */
	public void eventsToSpreadsheet(){
		if (eventsFilePath==null || "".equals(eventsFilePath) ) throw new IllegalStateException("eventsToSpreadsheet() eventsFilePath canot be null or empty string.");
		
		LOG.info("eventsToSpreadsheet() converting events file to spreadsheet ");
		
		String marshalledEvents=null;
		Events initialEventsConfig=null;
		WorkbookTranslator workbookTranslator=null;

		try{
			// marshal events file
			File eventsFile=null;
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource(eventsFilePath);
			if (resource==null){
				LOG.debug("eventsToSpreadsheet() eventsFilePath resource is not on class path. Checking raw location");
				eventsFile = new File(eventsFilePath);
			} else {
				eventsFile= new File(resource.getFile());
			}
			LOG.debug("eventsToSpreadsheet() using events file eventsFile.getPath()"+eventsFile.getAbsolutePath());

			StringBuilder fileContents = new StringBuilder((int)eventsFile .length());

			Scanner scanner=null;
			try {
				scanner = new Scanner(eventsFile );
				String lineSeparator = System.getProperty("line.separator");
				while(scanner.hasNextLine()) {        
					fileContents.append(scanner.nextLine() + lineSeparator);
				}

				marshalledEvents=fileContents.toString();
				LOG.debug("eventsToSpreadsheet() Config file contents marshalledEvents: \n"+marshalledEvents);

				EventsMarshaller eventsMarshaller = new EventsMarshaller();
				initialEventsConfig = eventsMarshaller.eventsFromXml(marshalledEvents);

			} catch (Exception e) {
				throw new IllegalStateException("eventsToSpreadsheet() problem marshalling events configuration file "+eventsFilePath, e);
			} finally {
				if (scanner != null) scanner.close();
			}
			// convert events to rowConfigObjects

			//loading and translating file
			List<EventRowConfigObject> eventRowConfigObjectList = new ArrayList<EventRowConfigObject>();

			LOG.debug("eventsToSpreadsheet()  converting Events to eventRowConfigObjects");
			List<Object> eventsContent = initialEventsConfig.getContent();
			for( Object obj: eventsContent){
				if (obj instanceof org.opennms.xmlns.xsd.eventconf.Event){
					Event event = (Event) obj;
					EventRowConfigObject eventRowConfigObject = ConfigRowTranslator.rowFromJxbEvent(event);
					eventRowConfigObjectList.add(eventRowConfigObject);
					LOG.debug("      eventRowConfigObject.toString()="+eventRowConfigObject.toString());
				}
			}

			// create workbook translator
			WorkbookTranslatorFactory workbookTxFactory= new WorkbookTranslatorFactory();
			workbookTranslator = workbookTxFactory.createWorkbookTranslator(workbookFilePath, propertiesFilePath);

			//create new spreadsheet
			workbookTranslator.createSheet(spreadsheetName);

			workbookTranslator.createEventRows(eventRowConfigObjectList , spreadsheetName);

			String snames="";
			snames="";
			for(String sheetname: workbookTranslator.getSheetNames()){
				snames=snames+sheetname+" ";
			}
			LOG.debug("eventsToSpreadsheet(): workbook sheet names after conversion:"+snames);

			LOG.info("CONVERSION COMPLETE - converted events file="+eventsFilePath
					+ " to spreadsheet="+spreadsheetName
					+ " in workbook="+workbookFilePath);

		} catch (Exception e){
			LOG.error("eventsToSpreadsheet() Exception", e);
		} finally{
			if (workbookTranslator!=null){
				workbookTranslator.close();
			}
		}


	}

	/**
	 * converts the spreadsheet to an events config file
	 */
	public void spreadsheetToEvents(){
		LOG.info("spreadsheetToEvents() converting spreadsheet to events file");

		WorkbookTranslator workbookTranslator=null;
		try{
			WorkbookTranslatorFactory workbookTxFactory= new WorkbookTranslatorFactory();

			//loading and translating file
			workbookTranslator = workbookTxFactory.createWorkbookTranslator(workbookFilePath, propertiesFilePath);

			List<EventRowConfigObject> eventRows = workbookTranslator.retreiveEventRows(spreadsheetName);

			EventsMarshaller eventsMarshaller = new EventsMarshaller();

			Events sheetEventsConfig = new Events();

			for (EventRowConfigObject eventRowConfigObject: eventRows){
				Event event = ConfigRowTranslator.jaxbEventFromRow(eventRowConfigObject);
				sheetEventsConfig.getContent().add(event);
			}

			String sheetEvents = eventsMarshaller.eventsToXml(sheetEventsConfig);
			LOG.debug("spreadsheetToEvents() sheetEvents=\n"+sheetEvents);

			//write events file
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter( new FileWriter(eventsFilePath));
				writer.write(sheetEvents);
			}
			catch ( IOException e){
				throw new IllegalStateException("spreadsheetToEvents() problem writing events configuration file "+eventsFilePath, e);
			}
			finally	{
				if ( writer != null) writer.close( );
			}

			LOG.info("CONVERSION COMPLETE - converted spreadsheet="+spreadsheetName
					+ " in workbook="+workbookFilePath
					+ " to events file="+eventsFilePath	);

		} catch (Exception e){
			LOG.debug("spreadsheetToEvents(): problem converting spreadsheet to events file. Exception:", e);
		} finally{
			if (workbookTranslator!=null){
				workbookTranslator.close();
			}
		}

	}




	public static void main(String[] args) {
		LOG.info("OpenNMS Spreadsheet / Event Config converter");

		String workbookFilePath=null;
		String propertiesFilePath=null;
		String eventsFilePath=null;
		String spreadsheetName=null;

		// if null do nothing
		// if true convert xml to spreadsheet
		// if false convert spreadsheet to xml
		Boolean xmltospreadsheet= null;

		// use apache cli to parse options from command line
		Options options = new Options();

		options.addOption("h", "help", false, "Display Help Message");
		options.addOption("d", "debug", false, "Run with debug messages");
		options.addOption("s", "sheetname", true, "Set the name of worksheet in workbook");
		options.addOption("b", "workbookfile", true, "Set the path to excel workbook file");
		options.addOption("e", "eventfile", true, "Set the path to event.xml file");
		options.addOption("x", "toxmlevents", false, "Use option to parse worksheet into events. Cannot be used with toworksheet ");
		options.addOption("w", "toworksheet", false, "Use option to parse events into worksheet. Cannot be used with toxmlevents.");
		options.addOption("p", "propertiesfile", false, "Set the path to properties file");

		String header = "Utility to convert between EXCEL workbooks and OpenNMS Event definitions  \n\n";
		String footer = "\nOpenNMS Utility Spreadsheet to events utility";

		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar <this program's jar file>", header, options, footer, true);

		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse( options, args);

			if( cmd.hasOption( "debug" ) ) {
				System.out.println("option value debug="+ cmd.getOptionValue( "debug" ) );
				//TODO SET DEBUG
			}

			if( cmd.hasOption( "help" ) ) {
				formatter.printHelp("java -jar <this program's jar file>", header, options, footer, true);
			} else {
				if( cmd.hasOption( "propertiesfile" ) ) {
					System.out.println("option value propertiesfile = "+ cmd.getOptionValue( "propertiesfile" ) );
					propertiesFilePath=cmd.getOptionValue( "propertiesfile");
					if ( "".equals(propertiesFilePath))throw new ParseException("propertiesfile cannot be an empty string");

				}
				if( cmd.hasOption( "workbookfile" ) ) {
					System.out.println("option value workbook="+ cmd.getOptionValue( "workbook" ) );
					workbookFilePath=cmd.getOptionValue( "workbookfile" );
					if (workbookFilePath==null || "".equals(workbookFilePath))throw new ParseException("workbookfile cannot be null or empty string");
				}
				if( cmd.hasOption( "sheetname" ) ) {
					System.out.println("option value sheet="+ cmd.getOptionValue( "sheet" ) );
					spreadsheetName=cmd.getOptionValue( "sheetname" );
					if (spreadsheetName==null || "".equals(spreadsheetName))throw new ParseException("sheetname cannot be null or empty string");
				}
				if( cmd.hasOption( "eventfile" ) ) {
					System.out.println("option value eventfile ="+ cmd.getOptionValue( "eventfile" ) );
					eventsFilePath=cmd.getOptionValue( "eventfile" );
					if (eventsFilePath==null || "".equals(eventsFilePath))throw new ParseException("eventsfile cannot be null or empty string");
				}

				if( cmd.hasOption( "toxmlevents" ) && cmd.hasOption( "toworksheet" ) ) {
					throw new ParseException("You cannot use both toxmlevents and toworksheet options together ");
				}

				if( cmd.hasOption( "toworksheet" ) ) {
					System.out.println("option value toworksheet="+ cmd.getOptionValue( "toworksheet" ) );
					xmltospreadsheet=true; // CONVERTING TO SPREADSHEET

				} else if(! cmd.hasOption( "toxmlevents" ) ) {
					throw new ParseException("You must specifiy a conversion operation (-x / --toxmlevents or -w / --toworksheet)"); 
				} else {
					System.out.println("option value toxmlevents="+ cmd.getOptionValue( "toworksheet" ) );
					xmltospreadsheet=false; // CONVERTING TO XMLEVENTS
				}

			}
		} catch (ParseException e) {
			LOG.error("Problem parsing command line. "+ e.getMessage());
			formatter.printHelp("USAGE : java -jar <this program's jar file>", header, options, footer, true);
		}

		if(xmltospreadsheet==null){
			System.out.println("No conversion specified. Do nothing");
		} else try {
			SpreadsheetEventConfMain spreadsheetEvtConfMain = new SpreadsheetEventConfMain();

			System.out.println("Conversion parameters:"
					+ "\n    workbook=" +workbookFilePath 
					+ "\n    propertiesfile=" +propertiesFilePath
					+ "\n    eventfile=" +eventsFilePath
					+ "\n    sheet=" +spreadsheetName);

			spreadsheetEvtConfMain.setWorkbookFilePath(workbookFilePath);
			spreadsheetEvtConfMain.setPropertiesFilePath(propertiesFilePath);
			spreadsheetEvtConfMain.setSpreadsheetName(spreadsheetName);
			spreadsheetEvtConfMain.setEventsFilePath(eventsFilePath);

			if(xmltospreadsheet){
				System.out.println("CONVERTING TO SPREADSHEET");
				spreadsheetEvtConfMain.eventsToSpreadsheet();
				
			} else {
				System.out.println("CONVERTING TO XMLEVENTS");
				spreadsheetEvtConfMain.spreadsheetToEvents();
			}
		} catch (Exception e){
			System.out.println("Problem performing conversion: EXception: ");
			e.printStackTrace();
		}

	}




}
