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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WorkbookTranslatorBasicImpl implements WorkbookTranslator {
	private static final Logger LOG = LoggerFactory.getLogger(WorkbookTranslatorBasicImpl.class);

	private Workbook importedWorkbook=null;
	private Properties workbookTranslatorProperties=null;
	private File workbookFile=null;
	private File workbookOutFile=null;
	private String filename=null;

	public WorkbookTranslatorBasicImpl(String workbookFilePath , Properties workbookTranslatorProperties  ){
		super();
		if (workbookFilePath ==null) throw new IllegalArgumentException("workbookFilePath cannot be null");
		this.workbookTranslatorProperties=workbookTranslatorProperties;

		LOG.debug("workbookTranslatorBasicImpl workbookFilePath="+ workbookFilePath);

		try {

			// checking if we can see file on class path
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource(workbookFilePath);
			if (resource==null){
				LOG.debug("workbookTranslatorBasicImpl resource is not on class path. Checking raw location");
				workbookFile = new File(workbookFilePath);
			} else {
				workbookFile = new File(classLoader.getResource(workbookFilePath).getFile());
			}
			LOG.debug("workbookTranslatorBasicImpl workbookFile.getPath()"+workbookFile.getAbsolutePath());

			if (workbookFile.exists()){
				LOG.debug("workbookTranslatorBasicImpl Workbook File Exists. Loading Workbook from path "+workbookFile.getPath());
			} else {
				LOG.debug("workbookTranslatorBasicImpl Workbook File Does not exist. Creating New empty Workbook at path "+ workbookFile.getAbsolutePath());
				Workbook wb = new XSSFWorkbook();
				FileOutputStream fileOut = new FileOutputStream(workbookFile);
				wb.write(fileOut);
				fileOut.close();
			}

			// creating  new temporary workbook for save
			filename=workbookFile.getName();
			String tempfileName = filename+".tmp";
			File parentDirectory = workbookFile.getParentFile();
			workbookOutFile = new File(parentDirectory, tempfileName);

			// importing new workbook
			importedWorkbook = WorkbookFactory.create(workbookFile);

		} catch (Exception e) {
			throw new IllegalArgumentException("workbookTranslatorBasicImpl cannot import workbook from workbook File Path="+workbookFilePath , e);
		} 


	}

	@Override
	public List<EventRowConfigObject> retreiveEventRows(String sheetName) { 
		if(importedWorkbook==null) throw new IllegalStateException("WorkbookTranslatorBasicImpl importedWorkbook must not be null");
		if(sheetName==null|| "".equals(sheetName)) throw new IllegalArgumentException("sheetName must not be null or empty string '' ");

		Sheet wbSheet = importedWorkbook.getSheet(sheetName);
		if(wbSheet==null) throw new IllegalArgumentException("sheetName '"+sheetName +"' does not exist in this workbook. workbookFile="+ workbookFile.getPath());

		List<EventRowConfigObject> eventRowConfigObjectList = new ArrayList<EventRowConfigObject>();

		// get header row headings
		wbSheet.getFirstRowNum();
		Row headerRow = wbSheet.getRow(0);

		StringBuffer sb=new StringBuffer();

		short headerRowMinColIx = headerRow.getFirstCellNum();
		short headerRowMaxColIx = headerRow.getLastCellNum();
		for(short colIx=headerRowMinColIx; colIx<headerRowMaxColIx; colIx++) {
			Cell cell = headerRow.getCell(colIx);
			if(cell == null) {
				continue;
			}
			sb.append(" ("+cell.getColumnIndex()+") "+cellValue(cell));
		}
		LOG.debug("column headers: "+sb.toString());

		// populate the event row config list
		for (int rowNo=1; rowNo<= wbSheet.getLastRowNum();rowNo++){
			Row row = wbSheet.getRow(rowNo);
			EventRowConfigObject eventRowConfig= new EventRowConfigObject();

			// converting row to config values
			try{
				Cell cell=null;
				cell = row.getCell(0);
				if (cell!=null) eventRowConfig.setMaskOid(cellValue(cell));
				cell = row.getCell(1);
				if (cell!=null) eventRowConfig.setMaskGeneric(cellValue(cell)); 
				cell = row.getCell(2);
				if (cell!=null) eventRowConfig.setMaskSpecific(cellValue(cell));
				cell = row.getCell(3);
				if (cell!=null) eventRowConfig.setMaskVarbind_1_number(cellValue(cell)); 
				cell = row.getCell(4);
				if (cell!=null) eventRowConfig.setMaskVarbind_1_value(cellValue(cell)); 
				cell = row.getCell(5);
				if (cell!=null) eventRowConfig.setMaskVarbind_2_number(cellValue(cell)); 
				cell = row.getCell(6);
				if (cell!=null) eventRowConfig.setMaskVarbind_2_value(cellValue(cell)); 
				cell = row.getCell(7);
				if (cell!=null) eventRowConfig.setEventUei(cellValue(cell));
				cell = row.getCell(8);
				if (cell!=null) eventRowConfig.setAlarmReductionKey(cellValue(cell)); 
				cell = row.getCell(9);
				if (cell!=null && cellValue(cell)!=null){ 
					Double cellDouble = Double.parseDouble(cellValue(cell));
					eventRowConfig.setAlarmType(cellDouble.intValue()); 
				}
				cell = row.getCell(10);
				if (cell!=null) eventRowConfig.setAlarmClearKey(cellValue(cell)); 
				cell = row.getCell(11);
				if (cell!=null) eventRowConfig.setAlarmAutoClean(Boolean.valueOf(cellValue(cell))); 
				cell = row.getCell(12);
				if (cell!=null) eventRowConfig.setEventLabel(cellValue(cell));
				cell = row.createCell(13);
				if (cell!=null) eventRowConfig.setEventDescr(cellValue(cell));
				cell = row.getCell(14);
				if (cell!=null) eventRowConfig.setEventMouseOverText(cellValue(cell)); 
				cell = row.getCell(15);
				if (cell!=null) eventRowConfig.setEventOperInstruct(cellValue(cell)); 
				cell = row.getCell(16);
				if (cell!=null) eventRowConfig.setEventSeverity(cellValue(cell)); 
				cell = row.getCell(17);
				if (cell!=null) eventRowConfig.setEventLoggroup(cellValue(cell)); 
				cell = row.getCell(18);
				if (cell!=null) eventRowConfig.setEventLogmsgDest(cellValue(cell)); 
				cell = row.getCell(19);
				if (cell!=null) eventRowConfig.setEventLogmsgValue(cellValue(cell)); 
				cell = row.getCell(20);
				if (cell!=null) eventRowConfig.setEventLogmsgNotify(Boolean.valueOf(cellValue(cell))); 

				eventRowConfigObjectList.add(eventRowConfig);

			} catch (Exception e){
				LOG.error("problem parsing data in row:"+ rowNo	+ "Exception:",e);
			}

		}

		return eventRowConfigObjectList ;
	}

	@Override
	public void createEventRows( List<EventRowConfigObject> eventRowConfigObjectList, 	String sheetName) {
		if(importedWorkbook==null) throw new IllegalStateException("WorkbookTranslatorBasicImpl importedWorkbook must not be null");
		if(sheetName==null|| "".equals(sheetName)) throw new IllegalArgumentException("sheetName must not be null or empty string '' ");
		if(eventRowConfigObjectList==null) throw new IllegalArgumentException("eventRowConfigObjectList must not be null");

		Sheet wbSheet = importedWorkbook.getSheet(sheetName);
		if(wbSheet==null) throw new IllegalArgumentException("sheetName '"+sheetName +"' does not exist in this workbook.");

		List<String> headers = new ArrayList<String>(Arrays.asList(
				"MaskOid",
				"MaskGeneric",
				"MaskSpecific",
				"MaskVarbind_1_number",
				"MaskVarbind_1_value",
				"MaskVarbind_2_number",
				"MaskVarbind_2_value",
				"EventUei",
				"AlarmReductionKey",
				"AlarmType",
				"AlarmClearKey",
				"AlarmAutoClean",
				"EventLabel",
				"EventDescr",
				"EventMouseOverText",
				"EventOperInstruct",
				"EventSeverity",
				"EventLoggroup",
				"EventLogmsgDest",
				"EventLogmsgValue",
				"EventLogmsgNotify"
				));

		// create header row headings
		Row headerRow = wbSheet.createRow(0);
		for (int colno=0; colno< headers.size(); colno++){
			Cell cell = headerRow.createCell(colno);
			cell.setCellValue(headers.get(colno));
		}

		// fill in values
		for(EventRowConfigObject eventRowConfig: eventRowConfigObjectList){
			int rowNum = wbSheet.getLastRowNum()+1;
			LOG.debug("creating new row: "+rowNum);

			Row row = wbSheet.createRow(rowNum);
			Cell cell=null;

			cell = row.createCell(0);
			if( eventRowConfig.getMaskOid() !=null ) cell.setCellValue(eventRowConfig.getMaskOid());
			cell = row.createCell(1);
			if( eventRowConfig.getMaskGeneric() !=null ) cell.setCellValue(eventRowConfig.getMaskGeneric()); 
			cell = row.createCell(2);
			if( eventRowConfig.getMaskSpecific() !=null ) cell.setCellValue(eventRowConfig.getMaskSpecific());
			cell = row.createCell(3);
			if( eventRowConfig.getMaskVarbind_1_number() !=null ) cell.setCellValue(eventRowConfig.getMaskVarbind_1_number()); 
			cell = row.createCell(4);
			if( eventRowConfig.getMaskVarbind_1_value() !=null ) cell.setCellValue(eventRowConfig.getMaskVarbind_1_value()); 
			cell = row.createCell(5);
			if( eventRowConfig.getMaskVarbind_2_number() !=null ) cell.setCellValue(eventRowConfig.getMaskVarbind_2_number()); 
			cell = row.createCell(6);
			if( eventRowConfig.getMaskVarbind_2_value() !=null ) cell.setCellValue(eventRowConfig.getMaskVarbind_2_value()); 
			cell = row.createCell(7);
			if( eventRowConfig.getEventUei() !=null ) cell.setCellValue(eventRowConfig.getEventUei());
			cell = row.createCell(8);
			if( eventRowConfig.getAlarmReductionKey() !=null ) cell.setCellValue(eventRowConfig.getAlarmReductionKey()); 
			cell = row.createCell(9);
			if( eventRowConfig.getAlarmType() !=null ) cell.setCellValue(eventRowConfig.getAlarmType()); 
			cell = row.createCell(10);
			if( eventRowConfig.getAlarmClearKey() !=null ) cell.setCellValue(eventRowConfig.getAlarmClearKey()); 
			cell = row.createCell(11);
			if( eventRowConfig.getAlarmAutoClean() !=null ) cell.setCellValue(eventRowConfig.getAlarmAutoClean()); 
			cell = row.createCell(12);
			if( eventRowConfig.getEventLabel() !=null ) cell.setCellValue(eventRowConfig.getEventLabel()); 
			cell = row.createCell(13);
			if( eventRowConfig.getEventDescr() !=null ) cell.setCellValue(eventRowConfig.getEventDescr()); 
			cell = row.createCell(14);
			if( eventRowConfig.getEventMouseOverText() !=null ) cell.setCellValue(eventRowConfig.getEventMouseOverText()); 
			cell = row.createCell(15);
			if( eventRowConfig.getEventOperInstruct() !=null ) cell.setCellValue(eventRowConfig.getEventOperInstruct()); 
			cell = row.createCell(16);
			if( eventRowConfig.getEventSeverity() !=null ) cell.setCellValue(eventRowConfig.getEventSeverity()); 
			cell = row.createCell(17);
			if( eventRowConfig.getEventLoggroup()!=null ) cell.setCellValue(eventRowConfig.getEventLoggroup()); 
			cell = row.createCell(18);
			if( eventRowConfig.getEventLogmsgDest() !=null ) cell.setCellValue(eventRowConfig.getEventLogmsgDest()); 
			cell = row.createCell(19);
			if( eventRowConfig.getEventLogmsgValue() !=null ) cell.setCellValue(eventRowConfig.getEventLogmsgValue()); 
			cell = row.createCell(20);
			if( eventRowConfig.getEventLogmsgNotify() !=null ) cell.setCellValue(eventRowConfig.getEventLogmsgNotify()); 
		}



	}

	@Override
	public List<String> getSheetNames() {
		if(importedWorkbook==null) throw new IllegalStateException("WorkbookTranslatorBasicImpl importedWorkbook must not be null");

		List<String> sheetNames=new ArrayList<String> ();
		for( int i=0; i<importedWorkbook.getNumberOfSheets(); i++){
			sheetNames.add(importedWorkbook.getSheetName(i));
		}
		return sheetNames;
	}

	@Override
	public void createSheet(String sheetName) {
		if(importedWorkbook==null) throw new IllegalStateException("WorkbookTranslatorBasicImpl importedWorkbook must not be null");
		if(sheetName==null|| "".equals(sheetName)) throw new IllegalArgumentException("sheetName must not be null or empty string '' ");

		importedWorkbook.createSheet(sheetName);

	}

	@Override
	public void close() {
		if(importedWorkbook==null) throw new IllegalStateException("WorkbookTranslatorBasicImpl importedWorkbook must not be null");
		try {

			// write to temp file, close file and then change file name
			FileOutputStream stream = new FileOutputStream(workbookOutFile);
			importedWorkbook.write(stream);
			stream.close();	

			importedWorkbook.close();

			// delete the old version before renaming new version
			workbookFile.delete();
			Files.move(workbookOutFile.toPath(), workbookFile.toPath());


		} catch (IOException e) {
			throw new IllegalStateException("WorkbookTranslatorBasicImpl cannot close worksheet.",e);
		}

	}

	/** 
	 * helper method to evaluate and return a cell value as a string
	 * @param cell
	 * @return
	 */
	private String cellValue(Cell cell){
		if(importedWorkbook==null) throw new IllegalStateException("WorkbookTranslatorBasicImpl importedWorkbook must not be null");

		FormulaEvaluator evaluator = importedWorkbook.getCreationHelper().createFormulaEvaluator();

		String cellValue=null;
		//cellValue = cell.getStringCellValue();

		if (cell!=null) {
			//switch (evaluator.evaluateFormulaCell(cell)) { TODO EVALUATE CELL??
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = Boolean.toString(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				cellValue = Double.toString(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_ERROR:
				cellValue = "Cell Error Code:"+Byte.toString(cell.getErrorCellValue());
				break;
				// CELL_TYPE_FORMULA will never occur
			case Cell.CELL_TYPE_FORMULA: 
				break;
			}

		}
		return cellValue;
	}

}
