/* Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
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

import java.util.List;

/**
 * Interface for populating spreadsheets in a given workbook from an EventRowConfigObject list
 * and reading an EventRowConfigObject list from a given spreadsheet
 * @author admin
 *
 */
public interface WorkbookTranslator {
	
	/**
	 * returns the event row list from a given sheetName in a Workbook
	 * @param sheetName
	 * @return List of EventRowConfigObject's corresponding to spreadsheet content
	 */
	public List<EventRowConfigObject> retreiveEventRows(String sheetName);
	
	/** 
	 * sets the event row contents in the sheet corresponding to the contents of the eventRowConfigObjectList
	 * @param eventRowConfigObjectList 
	 * @param sheetName which sheet to populate with data
	 * throws error if sheetName does not exist
	 */
	public void createEventRows(List<EventRowConfigObject> eventRowConfigObjectList, String sheetName);
	
	/**
	 * gets the list of named sheets in the workbook
	 * @return
	 */
	public List<String> getSheetNames();
	
	/**
	 * creates a new sheet in the workbook. throws error if sheetName alreads exists
	 * @param sheetName
	 */
	public void createSheet(String sheetName);
	
	/**
	 * closes workbook and cleans up resources. Must be called at end of session
	 */
	public void close();

}
