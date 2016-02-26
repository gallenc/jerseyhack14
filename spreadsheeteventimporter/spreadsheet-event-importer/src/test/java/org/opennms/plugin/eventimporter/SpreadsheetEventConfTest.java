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

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Test of jersey web client implementation
 * @author admin
 *
 */
public class SpreadsheetEventConfTest {
	private static final Logger LOG = LoggerFactory.getLogger(SpreadsheetEventConfTest.class);

	private static String TEST_PROPERTIES_FILE="eventImporter.properties";
	private static String TEST_WORKBOOK_IN_FILE= "eventsTestWorkBookIN.xlsx";
	private static String TEST_WORKBOOK_OUT_FILE= "./target/eventsTestWorkBookOUT.xlsx";
	private static String TEST_EVENTCONF_IN_FILE= "testeventfileIN.xml";
	private static String TEST_EVENTCONF_OUT_FILE= "./target/testeventfileOUT.xml";
	private static String TEST_SPREADSHEET_NAME="testSpreadheet1";
	private static String TEST_TRAPSCRIPT_OUT_FILE="./target/snmptraptest.sh";



	@Test
	public void eventsToSpreadsheetTest(){
		LOG.info("@Test - eventsToSpreadsheetTest.START");

		LOG.info("@Test - eventsToSpreadsheetTest. Configuration:"
				+ "\n    TEST_PROPERTIES_FILE="+TEST_PROPERTIES_FILE
				+ "\n    TEST_EVENTCONF_IN_FILE="+TEST_EVENTCONF_IN_FILE
				+ "\n    TEST_WORKBOOK_OUT_FILE="+TEST_WORKBOOK_OUT_FILE
				+ "\n    TEST_SPREADSHEET_NAME="+TEST_SPREADSHEET_NAME);

		// delete spreadsheet TEST_WORKBOOK_OUT_FILE if already exists in target directory for test
		File workbookFile=null;
		try {
			
			// checking if we can see file on class path
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource(TEST_WORKBOOK_OUT_FILE);
			if (resource==null){
				LOG.debug("workbookTranslatorBasicImpl resource is not on class path. Checking raw location");
				workbookFile = new File(TEST_WORKBOOK_OUT_FILE);
			} else {
				workbookFile = new File(classLoader.getResource(TEST_WORKBOOK_OUT_FILE).getFile());
			}
			LOG.debug("TEST_WORKBOOK_OUT_FILE workbookFile.getPath()"+workbookFile.getAbsolutePath());

			if (workbookFile.exists()){
				LOG.debug("deleting TEST_WORKBOOK_OUT_FILE from path "+workbookFile.getPath());
				workbookFile.delete();
			} 
		} catch (Exception e){
			LOG.debug("problem testing in TEST_WORKBOOK_OUT_FILE exists and deleting before test", e);
		} 

		SpreadsheetEventConfMain spreadsheetEvtConfMain = new SpreadsheetEventConfMain();

		spreadsheetEvtConfMain.setWorkbookFilePath(TEST_WORKBOOK_OUT_FILE);
		spreadsheetEvtConfMain.setPropertiesFilePath(TEST_PROPERTIES_FILE);
		spreadsheetEvtConfMain.setSpreadsheetName(TEST_SPREADSHEET_NAME);
		spreadsheetEvtConfMain.setEventsFilePath(TEST_EVENTCONF_IN_FILE);

		spreadsheetEvtConfMain.eventsToSpreadsheet();

		LOG.info("@Test - eventsToSpreadsheetTest.FINISH");
	}


	@Test
	public void spreadsheetToEventsTest(){
		LOG.info("@Test - spreadsheetToEventsTest.START");

		LOG.info("@Test - spreadsheetToEventsTest. Configuration:"
				+ "\n    TEST_PROPERTIES_FILE="+TEST_PROPERTIES_FILE
				+ "\n    TEST_EVENTCONF_OUT_FILE="+TEST_EVENTCONF_OUT_FILE
				+ "\n    TEST_WORKBOOK_IN_FILE="+TEST_WORKBOOK_IN_FILE
				+ "\n    TEST_SPREADSHEET_NAME="+TEST_SPREADSHEET_NAME);


		SpreadsheetEventConfMain spreadsheetEvtConfMain = new SpreadsheetEventConfMain();

		spreadsheetEvtConfMain.setWorkbookFilePath(TEST_WORKBOOK_IN_FILE);
		spreadsheetEvtConfMain.setPropertiesFilePath(TEST_PROPERTIES_FILE);
		spreadsheetEvtConfMain.setSpreadsheetName(TEST_SPREADSHEET_NAME);
		spreadsheetEvtConfMain.setEventsFilePath(TEST_EVENTCONF_OUT_FILE);

		spreadsheetEvtConfMain.spreadsheetToEvents();

		LOG.info("@Test - spreadsheetToEventsTest.FINISH");
	}


	@Test
	public void eventsToSnmpTrapScriptTest(){
		LOG.info("@Test - eventsToSnmpTrapScriptTest.START");

		LOG.info("@Test - eventsToSnmpTrapScriptTest. Configuration:"
				+ "\n    TEST_PROPERTIES_FILE="+TEST_PROPERTIES_FILE
				+ "\n    TEST_EVENTCONF_IN_FILE="+TEST_EVENTCONF_IN_FILE
				+ "\n    TEST_WORKBOOK_OUT_FILE="+TEST_WORKBOOK_OUT_FILE
				+ "\n    TEST_SPREADSHEET_NAME="+TEST_SPREADSHEET_NAME
				+ "\n    TEST_TRAPSCRIPT_OUT_FILE="+TEST_TRAPSCRIPT_OUT_FILE);


		SpreadsheetEventConfMain spreadsheetEvtConfMain = new SpreadsheetEventConfMain();

		spreadsheetEvtConfMain.setWorkbookFilePath(TEST_WORKBOOK_OUT_FILE);
		spreadsheetEvtConfMain.setPropertiesFilePath(TEST_PROPERTIES_FILE);
		spreadsheetEvtConfMain.setSpreadsheetName(TEST_SPREADSHEET_NAME);
		spreadsheetEvtConfMain.setEventsFilePath(TEST_EVENTCONF_IN_FILE);
		spreadsheetEvtConfMain.setSnmpTrapScriptFilePath(TEST_TRAPSCRIPT_OUT_FILE);

		spreadsheetEvtConfMain.eventsToSnmpTrapScript();

		LOG.info("@Test - eventsToSnmpTrapScriptTest.FINISH");
	}





}
