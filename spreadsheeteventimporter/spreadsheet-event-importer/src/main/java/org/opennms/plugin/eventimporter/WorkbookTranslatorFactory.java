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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * factory for creating a workbooktranslator
 * @author admin
 *
 */
public class WorkbookTranslatorFactory {
	private static final Logger LOG = LoggerFactory.getLogger(WorkbookTranslatorFactory.class);


	/**
	 * creates a new workbook translator
	 * @param workbookFilePath path to workbook file
	 * @param workbookTranslatorPropertiesFilePath  path to translator properties file
	 * @return WorkbookTranslator with associated spreadsheet
	 */
	public WorkbookTranslator createWorkbookTranslator(String workbookFilePath , String workbookTranslatorPropertiesFilePath   ){

		if (workbookFilePath ==null) throw new IllegalArgumentException("workbookFilePath cannot be null");
		LOG.debug(" workbookFilePath="+ workbookFilePath);

		File workbookFile=null;
		Workbook importedWorkbook=null;
		Properties workbookTranslatorProperties = null;

		try {
			
			// checking if we can see file on class path
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource(workbookFilePath);
			if (resource==null){
				LOG.debug("createWorkbookTranslator resource is not on class path. Checking raw location");
				workbookFile = new File(workbookFilePath);
			} else {
				workbookFile = new File(classLoader.getResource(workbookFilePath).getFile());
			}
			LOG.debug("@Test - createWorkbookTranslator workbookFile.getPath()"+workbookFile.getAbsolutePath());

			if (workbookFile.exists()){
				LOG.debug("createWorkbookTranslator Workbook File Exists. Loading Workbook from path "+workbookFile.getPath());
			} else {
				LOG.debug("createWorkbookTranslator Workbook File Does not exist. Creating New empty Workbook at path "+ workbookFile.getAbsolutePath());
			    Workbook wb = new XSSFWorkbook();
			    FileOutputStream fileOut = new FileOutputStream(workbookFile);
			    wb.write(fileOut);
			    fileOut.close();
			}
			// importing new workbook
			importedWorkbook = WorkbookFactory.create(workbookFile);
			
		} catch (Exception e) {
			throw new IllegalArgumentException("cannot import workbook from workbook File Path="+workbookFilePath , e);
		} 

		if (workbookTranslatorPropertiesFilePath!=null){
			try {
				workbookTranslatorProperties = new Properties();
				InputStream    is = this.getClass().getClassLoader().getResourceAsStream(workbookTranslatorPropertiesFilePath);
				workbookTranslatorProperties.load(is);
			} catch (Exception e) {
				throw new IllegalArgumentException("cannot import workbookTranslatorPropertiesFilePath="+workbookTranslatorPropertiesFilePath , e);
			} 

		}

		WorkbookTranslator workbookTranslator= new WorkbookTranslatorBasicImpl(importedWorkbook, workbookTranslatorProperties );

		return workbookTranslator;
	}


}
