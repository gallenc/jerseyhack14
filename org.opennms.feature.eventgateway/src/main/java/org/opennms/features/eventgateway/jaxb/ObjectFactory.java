/* ******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2011-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.eventgateway.jaxb;

import javax.xml.bind.annotation.XmlRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.opennms.features.eventgateway.jaxb package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(ObjectFactory.class);



    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.opennms.xmlns.xsd.event
     * 
     */
    public ObjectFactory() {
    	LOG.debug("ObjectFactory initialized");
    }

    /**
     * Create an instance of {@link RestEventMessage }
     * 
     */
    public RestEventMessage createRestEventMessage() {
    	LOG.debug("createRestEventMessage");
        return new RestEventMessage();
    }
    
    /**
     * Create an instance of {@link RestEventMessage }
     * 
     */
    public RestEventReplyMessage createRestEventReplyMessage() {
    	LOG.debug("createRestEventReplyMessage");
        return new RestEventReplyMessage();
    }
    
 
    /**
     * Create an instance of {@link NameValuePair }
     * 
     */
    public NameValuePair createNameValuePair() {
    	LOG.debug("createNameValuePair");
        return new NameValuePair();
    }

    /**
     * Create an instance of {@link  EventGatewayConfig }
     * 
     */
    public  EventGatewayConfig createEventGatewayConfig() {
    	LOG.debug("createEventGatewayConfig");
        return new  EventGatewayConfig();
    }
    
    /**
     * Create an instance of {@link  EventGatewayConfigurations  }
     * 
     */
    public  EventGatewayConfigurations createEventGatewayConfigurations() {
    	LOG.debug("createEventGatewayConfigurations ");
        return new EventGatewayConfigurations();
    }
    
    /**
     * Create an instance of {@link  EventGatewayAuthKeyConfig  }
     * 
     */
    public  EventGatewayAuthKeyConfig createEventGatewayAuthKeyConfig() {
    	LOG.debug("createEventGatewayAuthKeyConfig ");
        return new EventGatewayAuthKeyConfig();
    }
    
    /**
     * Create an instance of {@link  EventGatewayErrorMessage  }
     * 
     */
    public  EventGatewayErrorMessage createEventGatewayErrorMessage() {
    	LOG.debug("createErrorMessageErrorMessage ");
        return new EventGatewayErrorMessage();
    }
   

}
