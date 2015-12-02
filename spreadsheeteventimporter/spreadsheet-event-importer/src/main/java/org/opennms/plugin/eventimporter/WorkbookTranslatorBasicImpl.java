package org.opennms.plugin.eventimporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WorkbookTranslatorBasicImpl implements WorkbookTranslator {
	private static final Logger LOG = LoggerFactory.getLogger(WorkbookTranslatorBasicImpl.class);
	
	private Workbook importedWorkbook=null;
	private Properties workbookTranslatorProperties=null;

	public WorkbookTranslatorBasicImpl(Workbook importedWorkbook, Properties workbookTranslatorProperties ){
		super();
		this.importedWorkbook=importedWorkbook;
		this.workbookTranslatorProperties=workbookTranslatorProperties;
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
		if(sheetName==null|| "".equals(sheetName)) new IllegalArgumentException("sheetName must not be null or empty string '' ");
		if(eventRowConfigObjectList==null) new IllegalArgumentException("eventRowConfigObjectList must not be null");
		// TODO Auto-generated method stub

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
			
			importedWorkbook.close();
		} catch (IOException e) {
			throw new IllegalStateException("WorkbookTranslatorBasicImpl cannot close worksheet.",e);
		}

	}

}
