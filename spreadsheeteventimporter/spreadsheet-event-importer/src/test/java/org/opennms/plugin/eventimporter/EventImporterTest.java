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

import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;


/**
 * Test of jersey web client implementation
 * @author admin
 *
 */
public class EventImporterTest {
	
	private static String TEST_PROPERTIES_FILE="/eventImporter.properties";
	

	
	// constructor loads test properties file if exists
	public EventImporterTest(){
		super();
		
		System.out.println("LOADING PROPERTIES: EventImporterTest() from "+TEST_PROPERTIES_FILE);
		
		Properties prop = null;
        InputStream is = null;
        try {
            prop = new Properties();
            is = this.getClass().getResourceAsStream(TEST_PROPERTIES_FILE);
            prop.load(is);
 
        } catch (Exception e) {
        	System.out.println("     Using default values. Problem loading TEST_PROPERTIES_FILE:"+TEST_PROPERTIES_FILE+" Exception:"+e);
        }
		
	}


	
	
	@Test
	public void eventImporterTest(){
		System.out.println("@Test - eventImporterTest.START");
		
		EventImporter eventImporter = new EventImporter();
		eventImporter.importEvents();
			
		
		System.out.println("@Test - eventImporterTest.FINISH");
	}





}
