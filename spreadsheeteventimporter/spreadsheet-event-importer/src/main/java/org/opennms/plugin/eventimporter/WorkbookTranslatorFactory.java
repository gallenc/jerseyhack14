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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

	private static final String WORKBOOK_TRANSLATOR_KEY="org.opennms.plugin.eventimporter.workbooktranslator";


	/**
	 * creates a new workbook translator
	 * @param workbookFilePath path to workbook file
	 * @param workbookTranslatorPropertiesFilePath  path to translator properties file
	 * @return WorkbookTranslator with associated spreadsheet
	 */
	public WorkbookTranslator createWorkbookTranslator(String workbookFilePath , String workbookTranslatorPropertiesFilePath   ){
		if (workbookFilePath ==null) throw new IllegalArgumentException("workbookFilePath cannot be null");

		LOG.info("Workbook Translator using workbook file path="+workbookFilePath );

		WorkbookTranslator workbookTranslator=null;
		Properties workbookTranslatorProperties=null; 

		if (workbookTranslatorPropertiesFilePath==null){
			LOG.debug("WorkbookTranslatorFactory workbookTranslatorPropertiesFilePath set to null. Using default values.");
		} else {
			try {
				LOG.info("Workbook Translator importing properties from workbookTranslatorPropertiesFilePath="+workbookTranslatorPropertiesFilePath ); 
				workbookTranslatorProperties = new Properties();
				InputStream    is = this.getClass().getClassLoader().getResourceAsStream(workbookTranslatorPropertiesFilePath);
				workbookTranslatorProperties.load(is);
			} catch (Exception e) {
				throw new IllegalArgumentException("WorkbookTranslatorFactory cannot import workbookTranslatorPropertiesFilePath="+workbookTranslatorPropertiesFilePath , e);
			} 

		}

		if (workbookTranslatorProperties ==null ){
			LOG.info("workbookTranslatorProperties file not set. using default class. Using default class WorkbookTranslatorBasicImpl");
			workbookTranslator= new WorkbookTranslatorBasicImpl(workbookFilePath , workbookTranslatorProperties  );
			return workbookTranslator;
		} else {
			if (!workbookTranslatorProperties.containsKey(WORKBOOK_TRANSLATOR_KEY)){
				throw new IllegalArgumentException(""+WORKBOOK_TRANSLATOR_KEY+" not defined in the properties file");
			}

			String translatorClass = (String)workbookTranslatorProperties.get(WORKBOOK_TRANSLATOR_KEY);
			LOG.info("Using workbooktranslator class defined by "+WORKBOOK_TRANSLATOR_KEY+"="+
					translatorClass + " defined in the properties file.");
			try {
				Constructor<?> translatorConstructor=null;
				translatorConstructor = Class.forName(translatorClass).getConstructor(String.class, Properties.class);
				workbookTranslator = (WorkbookTranslator) translatorConstructor.newInstance(workbookFilePath, workbookTranslatorProperties);
			} catch (NoSuchMethodException | SecurityException
					| ClassNotFoundException | InstantiationException 
					| IllegalAccessException | IllegalArgumentException 
					| InvocationTargetException e) {

				throw new IllegalStateException("Cannot instantiate workbooktranslator class for "+WORKBOOK_TRANSLATOR_KEY+"="+
						translatorClass + " defined in the properties file. Exception:", e);
			}
			return workbookTranslator;
		}

	}


}
