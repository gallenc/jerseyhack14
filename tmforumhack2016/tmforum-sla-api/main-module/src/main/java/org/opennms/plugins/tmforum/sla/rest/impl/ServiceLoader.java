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

package org.opennms.plugins.tmforum.sla.rest.impl;

import org.opennms.plugins.tmforum.sla.SlaNotificationService;


/** used to statically pass service references to Jersey ReST classes
 * 
 * @author cgallen
 *
 */
public class ServiceLoader {


	private static SlaNotificationService slaNotificationService= null;

	public ServiceLoader(){
		super();
	}

	public ServiceLoader(SlaNotificationService slaNotificationService ){
		super();
		setSlaNotificationService(slaNotificationService);

	}

	public static synchronized SlaNotificationService getSlaNotificationService() {
		return slaNotificationService;
	}

	public static synchronized void setSlaNotificationService(
			SlaNotificationService slaNotificationService) {
		ServiceLoader.slaNotificationService = slaNotificationService;
	}

}
