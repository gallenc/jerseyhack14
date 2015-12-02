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

	public WorkbookTranslatorBasicImpl(String workbookFilePath , String workbookTranslatorPropertiesFilePath ){
		super();
		if (workbookFilePath ==null) throw new IllegalArgumentException("workbookFilePath cannot be null");

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

		if (workbookTranslatorPropertiesFilePath!=null){
			try {
				workbookTranslatorProperties = new Properties();
				InputStream    is = this.getClass().getClassLoader().getResourceAsStream(workbookTranslatorPropertiesFilePath);
				workbookTranslatorProperties.load(is);
			} catch (Exception e) {
				throw new IllegalArgumentException("workbookTranslatorBasicImpl cannot import workbookTranslatorPropertiesFilePath="+workbookTranslatorPropertiesFilePath , e);
			} 

		}

	}

	@Override
	public List<EventRowConfigObject> retreiveEventRows(String sheetName) { 
		if(importedWorkbook==null) throw new IllegalStateException("WorkbookTranslatorBasicImpl importedWorkbook must not be null");
		if(sheetName==null|| "".equals(sheetName)) throw new IllegalArgumentException("sheetName must not be null or empty string '' ");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createEventRows( List<EventRowConfigObject> eventRowConfigObjectList, 	String sheetName) {
		if(importedWorkbook==null) throw new IllegalStateException("WorkbookTranslatorBasicImpl importedWorkbook must not be null");
		if(sheetName==null|| "".equals(sheetName)) throw new IllegalArgumentException("sheetName must not be null or empty string '' ");
		if(eventRowConfigObjectList==null) throw new IllegalArgumentException("eventRowConfigObjectList must not be null");

		// TODO Auto-generated method stub
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
			cell.setCellValue(eventRowConfig.getMaskOid());
			cell = row.createCell(1);
			cell.setCellValue(eventRowConfig.getMaskGeneric()); 
			cell = row.createCell(2);
			cell.setCellValue(eventRowConfig.getMaskSpecific());
			cell = row.createCell(3);
			cell.setCellValue(eventRowConfig.getMaskVarbind_1_number()); 
			cell = row.createCell(4);
			cell.setCellValue(eventRowConfig.getMaskVarbind_1_value()); 
			cell = row.createCell(5);
			cell.setCellValue(eventRowConfig.getMaskVarbind_2_number()); 
			cell = row.createCell(6);
			cell.setCellValue(eventRowConfig.getMaskVarbind_2_value()); 
			cell = row.createCell(7);
			cell.setCellValue(eventRowConfig.getEventUei());
			cell = row.createCell(8);
			cell.setCellValue(eventRowConfig.getAlarmReductionKey()); 
			cell = row.createCell(9);
			cell.setCellValue(eventRowConfig.getAlarmType()); 
			cell = row.createCell(10);
			cell.setCellValue(eventRowConfig.getAlarmClearKey()); 
			cell = row.createCell(11);
			cell.setCellValue(eventRowConfig.getAlarmAutoClean()); 
			cell = row.createCell(12);
			cell.setCellValue(eventRowConfig.getEventLabel()); 
			cell = row.createCell(13);
			cell.setCellValue(eventRowConfig.getEventDescr()); 
			cell = row.createCell(14);
			cell.setCellValue(eventRowConfig.getEventMouseOverText()); 
			cell = row.createCell(15);
			cell.setCellValue(eventRowConfig.getEventOperInstruct()); 
			cell = row.createCell(16);
			cell.setCellValue(eventRowConfig.getEventSeverity()); 
			cell = row.createCell(17);
			cell.setCellValue(eventRowConfig.getEventLoggroup()); 
			cell = row.createCell(18);
			cell.setCellValue(eventRowConfig.getEventLogmsgDest()); 
			cell = row.createCell(19);
			cell.setCellValue(eventRowConfig.getEventLogmsgValue()); 
			cell = row.createCell(20);
			cell.setCellValue(eventRowConfig.getEventLogmsgNotify()); 
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

}
