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
import java.io.PrintWriter;
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
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.opennms.xmlns.xsd.eventconf.Event;
import org.opennms.xmlns.xsd.eventconf.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;


/**
 * Event importer imports events from xcel spreadsheet
 */
public class SpreadsheetEventConfMain {
	private static final Logger LOG = LoggerFactory.getLogger(SpreadsheetEventConfMain.class);

	private String workbookFilePath=null;

	private String propertiesFilePath=null;

	private String eventsFilePath=null;

	private String spreadsheetName=null;

	private String snmpTrapScriptFilePath=null;

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

	public String getSnmpTrapScriptFilePath() {
		return snmpTrapScriptFilePath;
	}

	public void setSnmpTrapScriptFilePath(String snmpTrapScriptFilePath) {
		this.snmpTrapScriptFilePath = snmpTrapScriptFilePath;
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
			
			if(LOG.isDebugEnabled()){
				LOG.debug("Event Rows Retreived from Sheet:");
				for (EventRowConfigObject eventRowConfigObject: eventRows){
					LOG.debug(eventRowConfigObject.toString());
				}
				LOG.debug("End of Event Rows Retreived from Sheet:");
			}

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
			LOG.error("spreadsheetToEvents(): problem converting spreadsheet to events file. Exception:", e);
		} finally{
			if (workbookTranslator!=null){
				workbookTranslator.close();
			}
		}

	}


	/** 
	 * converts an opennms events file to snmp trap tests
	 * 
	 */
	public void eventsToSnmpTrapScript() {
		// TODO Auto-generated method stub
		if (eventsFilePath==null || "".equals(eventsFilePath) ) throw new IllegalStateException("eventsToSpreadsheet() eventsFilePath cannot be null or empty string.");
		if (snmpTrapScriptFilePath==null || "".equals(snmpTrapScriptFilePath) ) throw new IllegalStateException("eventsToSpreadsheet() snmpTrapScriptFilePath cannot be null or empty string.");
		
		LOG.info("eventsToSnmpTrapScript() converting events file to snmtrap test script ");

		String marshalledEvents=null;
		Events initialEventsConfig=null;
		PrintWriter snmpTestOut=null;

		try{
			// marshal events file
			File eventsFile=null;
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource(eventsFilePath);
			if (resource==null){
				LOG.debug("eventsToSnmpTrapScript() eventsFilePath resource is not on class path. Checking raw location");
				eventsFile = new File(eventsFilePath);
			} else {
				eventsFile= new File(resource.getFile());
			}
			LOG.debug("eventsToSnmpTrapScript() using events file eventsFile.getPath()"+eventsFile.getAbsolutePath());

			StringBuilder fileContents = new StringBuilder((int)eventsFile .length());

			Scanner scanner=null;
			try {
				scanner = new Scanner(eventsFile );
				String lineSeparator = System.getProperty("line.separator");
				while(scanner.hasNextLine()) {        
					fileContents.append(scanner.nextLine() + lineSeparator);
				}

				marshalledEvents=fileContents.toString();
				LOG.debug("eventsToSnmpTrapScript() Config file contents marshalledEvents: \n"+marshalledEvents);

				EventsMarshaller eventsMarshaller = new EventsMarshaller();
				initialEventsConfig = eventsMarshaller.eventsFromXml(marshalledEvents);

			} catch (Exception e) {
				throw new IllegalStateException("eventsToSnmpTrapScript() problem marshalling events configuration file "+eventsFilePath, e);
			} finally {
				if (scanner != null) scanner.close();
			}
			// convert events to rowConfigObjects

			//loading and translating file
			List<EventRowConfigObject> eventRowConfigObjectList = new ArrayList<EventRowConfigObject>();

			LOG.debug("eventsToSnmpTrapScript()  converting Events to eventRowConfigObjects");
			List<Object> eventsContent = initialEventsConfig.getContent();
			for( Object obj: eventsContent){
				if (obj instanceof org.opennms.xmlns.xsd.eventconf.Event){
					Event event = (Event) obj;
					EventRowConfigObject eventRowConfigObject = ConfigRowTranslator.rowFromJxbEvent(event);
					eventRowConfigObjectList.add(eventRowConfigObject);
					LOG.debug("      eventRowConfigObject.toString()="+eventRowConfigObject.toString());
				}
			}

			// create test script
			snmpTestOut = new PrintWriter(snmpTrapScriptFilePath);
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("#!/bin/bash");
			sb.append("\n# TEST FILE "+snmpTrapScriptFilePath);
			
			for (EventRowConfigObject eventRowConfigObject :eventRowConfigObjectList){
				sb.append("\n");
				sb.append("\n# eventLabel "+eventRowConfigObject.getEventLabel());
				sb.append("\n# event eui  "+eventRowConfigObject.getEventUei());
				sb.append("\n# test script:  ");
				// basic trap string
				String trapOid=eventRowConfigObject.getMaskOid()+"."+eventRowConfigObject.getMaskSpecific();
				sb.append("\nsnmptrap -v 2c -c public host \"\" "+trapOid);
				
				try{
				// find positions of varbinds
				String vb1numStr=eventRowConfigObject.getMaskVarbind_1_number();
				String vb1valStr=eventRowConfigObject.getMaskVarbind_1_value();
				Integer vb1num=null;
				Integer maxvbnum= 0;
				if (vb1numStr!=null && ! "".equals(vb1numStr)){
					vb1num = Integer.parseInt(vb1numStr);
					maxvbnum=vb1num;
				}
				String vb2numStr=eventRowConfigObject.getMaskVarbind_2_number();
				String vb2valStr=eventRowConfigObject.getMaskVarbind_2_value();
				Integer vb2num=null;
				if (vb2numStr!=null && ! "".equals(vb2numStr)){
					vb2num = Integer.parseInt(vb2numStr);
					if (vb2num>maxvbnum) maxvbnum=vb2num;
				}
				String[] varbindstrings= new String[maxvbnum];
				for(int x=0; x<maxvbnum; x++){
					// fill with dummy varbinds
					varbindstrings[x]= " "+trapOid+" i "+1+" ";
				}
				varbindstrings[vb1num-1] = " "+trapOid+" i "+vb1valStr+" ";
				if(vb2numStr!=null){
					varbindstrings[vb2num-1] = " "+trapOid+" i "+vb2valStr+" ";
				};
				for(int x=0; x<maxvbnum; x++){
					sb.append(varbindstrings[x]);
				}
				sb.append("");
				
				} catch (Exception e){
					LOG.error("eventsToSnmpTrapScript() Exception when convsrting a file", e);
				}
				
			}
			LOG.debug("Test File to be written:\n"+sb.toString());
			
			snmpTestOut.println(sb.toString());
			
			LOG.info("CONVERSION COMPLETE - converted events file="+eventsFilePath
					+ " to snmp test file ="+snmpTrapScriptFilePath);

		} catch (Exception e){
			LOG.error("eventsToSnmpTrapScript() Exception", e);
		} finally{
			if (snmpTestOut!=null) snmpTestOut.close();
		}
	}


	/**
	 * sets underlying log4j2 to debug mode
	 */
	public static void debug(){
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
		loggerConfig.setLevel(Level.DEBUG);
		ctx.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.
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

		options.addOption("h", "help", false, "Display this Help Message");
		options.addOption("d", "debug", false, "Run with debug messages");
		options.addOption("s", "sheetname", true, "Set the name of worksheet in workbook");
		options.addOption("b", "workbookfile", true, "Set the path to excel workbook.xlsx file");
		options.addOption("e", "eventfile", true, "Set the path to event.xml file");
		options.addOption("x", "toxmlevents", false, "Use option to parse worksheet into events. Cannot be used with toworksheet ");
		options.addOption("w", "toworksheet", false, "Use option to parse events into worksheet. Cannot be used with toxmlevents.");
		options.addOption("p", "propertiesfile", false, "Set the path to properties file (optional - currently not used)");

		String header = "Utility to convert between EXCEL workbooks and OpenNMS Event definitions  \n\n";
		String footer = "\nOpenNMS Spreadsheet to event config utility";

		HelpFormatter formatter = new HelpFormatter();
		CommandLineParser parser = new DefaultParser();

		if (args.length==0) {
			formatter.printHelp("java -jar <this program's jar file>", header, options, footer, true);
		} else {
			try {
				CommandLine cmd = parser.parse( options, args);

				if( cmd.hasOption( "debug" ) ) {
					debug();
					LOG.debug("Debugging log enabled = option value debug="+ cmd.getOptionValue( "debug" ) );
				}

				if( cmd.hasOption( "help" ) ) {
					formatter.printHelp("java -jar <this program's jar file>", header, options, footer, true);
				} else {
					if( cmd.hasOption( "propertiesfile" ) ) {
						propertiesFilePath=cmd.getOptionValue( "propertiesfile");
						LOG.debug("option value propertiesfile = "+ propertiesFilePath );
						if ( "".equals(propertiesFilePath))throw new ParseException("propertiesfile cannot be an empty string");
					}
					if( cmd.hasOption( "workbookfile" ) ) {
						workbookFilePath=cmd.getOptionValue( "workbookfile" );
						LOG.debug("option value workbook="+ workbookFilePath );
						if (workbookFilePath==null || "".equals(workbookFilePath))throw new ParseException("workbookfile cannot be null or empty string");
					}
					if( cmd.hasOption( "sheetname" ) ) {
						spreadsheetName=cmd.getOptionValue( "sheetname" );
						LOG.debug("option value sheet="+ spreadsheetName );
						if (spreadsheetName==null || "".equals(spreadsheetName))throw new ParseException("sheetname cannot be null or empty string");
					}
					if( cmd.hasOption( "eventfile" ) ) {
						eventsFilePath=cmd.getOptionValue( "eventfile" );
						LOG.debug("option value eventfile ="+ eventsFilePath );
						if (eventsFilePath==null || "".equals(eventsFilePath))throw new ParseException("eventsfile cannot be null or empty string");
					}

					if( cmd.hasOption( "toxmlevents" ) && cmd.hasOption( "toworksheet" ) ) {
						throw new ParseException("You cannot use both toxmlevents and toworksheet options together ");
					}

					if( cmd.hasOption( "toworksheet" ) ) {
						LOG.debug("option value toworksheet="+ cmd.getOptionValue( "toworksheet" ) );
						xmltospreadsheet=true; // CONVERTING TO SPREADSHEET

					} else if(! cmd.hasOption( "toxmlevents" ) ) {
						throw new ParseException("You must specifiy a conversion operation (-x / --toxmlevents or -w / --toworksheet)"); 
					} else {
						LOG.debug("option value toxmlevents="+ cmd.getOptionValue( "toworksheet" ) );
						xmltospreadsheet=false; // CONVERTING TO XMLEVENTS
					}

				}
			} catch (ParseException e) {
				LOG.error("Problem parsing command line. "+ e.getMessage());
				formatter.printHelp("USAGE : java -jar <this program's jar file>", header, options, footer, true);
			}
		}

		if(xmltospreadsheet==null){
			System.out.println("No conversion specified. Do nothing");
		} else try {
			SpreadsheetEventConfMain spreadsheetEvtConfMain = new SpreadsheetEventConfMain();

			LOG.info("Conversion parameters:"
					+ "\n    workbook=" +workbookFilePath 
					+ "\n    propertiesfile=" +propertiesFilePath
					+ "\n    eventfile=" +eventsFilePath
					+ "\n    sheet=" +spreadsheetName);

			spreadsheetEvtConfMain.setWorkbookFilePath(workbookFilePath);
			spreadsheetEvtConfMain.setPropertiesFilePath(propertiesFilePath);
			spreadsheetEvtConfMain.setSpreadsheetName(spreadsheetName);
			spreadsheetEvtConfMain.setEventsFilePath(eventsFilePath);

			if(xmltospreadsheet){
				LOG.info("CONVERTING TO SPREADSHEET (please note - loading the converter classes may take some time)");
				spreadsheetEvtConfMain.eventsToSpreadsheet();

			} else {
				LOG.info("CONVERTING TO XMLEVENTS (please note - loading the converter classes may take some time)");
				spreadsheetEvtConfMain.spreadsheetToEvents();
			}
		} catch (Exception e){
			LOG.error("Problem performing conversion: EXception: ", e);
			e.printStackTrace();
		}

	}











}
