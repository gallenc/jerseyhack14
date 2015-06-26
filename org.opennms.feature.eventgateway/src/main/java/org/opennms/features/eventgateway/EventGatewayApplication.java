/* ******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2010-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.eventgateway;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.opennms.features.eventgateway.rest.EventGatewayAuthKeyRest;
import org.opennms.features.eventgateway.rest.EventGatewayRest;

public class EventGatewayApplication extends Application {

	// doing this because the com.sun.ws.rest.api.core.PackagesResourceConfig 
	// class contains OSGi unfriendly classloader code
	@Override
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(EventGatewayRest.class);
		s.add(EventGatewayAuthKeyRest.class);
		return s;
	}

	// Enable LoggingFilter & output entity.   
	// only for java logging - no use :(
	// registerInstances(new LoggingFilter(Logger.getLogger(EventGatewayApplication.class.getName()), true));
	//	}

}