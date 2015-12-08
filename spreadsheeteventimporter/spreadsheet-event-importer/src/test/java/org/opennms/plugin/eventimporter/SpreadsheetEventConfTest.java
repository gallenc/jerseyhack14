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

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;
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



	@Test
	public void eventsToSpreadsheetTest(){
		LOG.info("@Test - eventsToSpreadsheetTest.START");

		LOG.info("@Test - eventsToSpreadsheetTest. Configuration:"
				+ "\n    TEST_PROPERTIES_FILE="+TEST_PROPERTIES_FILE
				+ "\n    TEST_EVENTCONF_IN_FILE="+TEST_EVENTCONF_IN_FILE
				+ "\n    TEST_WORKBOOK_OUT_FILE="+TEST_WORKBOOK_OUT_FILE
				+ "\n    TEST_SPREADSHEET_NAME="+TEST_SPREADSHEET_NAME);


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





}
