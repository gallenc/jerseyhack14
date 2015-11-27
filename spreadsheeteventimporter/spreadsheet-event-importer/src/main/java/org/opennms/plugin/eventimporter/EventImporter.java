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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Event importer imports events from xcel spreadsheet
 */
public class EventImporter {

	private String worksheetFileName=null;

	public void importEvents() {
		System.out.println("importing events");
		
		File worksheetFile=null;
		Workbook importedWorkbook = null;

		try{

			// import worksheet
			try {
				ClassLoader classLoader = getClass().getClassLoader();
				worksheetFile = new File(classLoader.getResource(worksheetFileName).getFile());
				importedWorkbook = WorkbookFactory.create(worksheetFile);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} 

			// test process worksheet
			for (Sheet sheet : importedWorkbook ) {
				System.out.println("sheet: "+sheet.getSheetName().toString());
				for (Row row : sheet) {
					System.out.println("   row rowNum: "+Integer.valueOf(row.getRowNum()).toString());
					for (Cell cell : row) {
						System.out.println("      cell columnIndex: "+Integer.valueOf(cell.getColumnIndex()).toString()+" value="+cell.toString());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("Main class not written");
	}

	/**
	 * @return the worksheetFileName
	 */
	public String getWorksheetFileName() {
		return worksheetFileName;
	}

	/**
	 * @param worksheetFileName the worksheetFileName to set
	 */
	public void setWorksheetFileName(String worksheetFileName) {
		this.worksheetFileName = worksheetFileName;
	}
}
