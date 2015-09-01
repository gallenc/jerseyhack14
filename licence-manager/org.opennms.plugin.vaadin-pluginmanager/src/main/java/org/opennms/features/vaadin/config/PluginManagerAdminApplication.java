/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012-2014 The OpenNMS Group, Inc.
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

package org.opennms.features.vaadin.config;

//import org.opennms.features.vaadin.datacollection.SnmpCollectionPanel;
//import org.opennms.netmgt.config.api.DataCollectionConfigDao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.opennms.features.vaadin.pluginmanager.PluginManagerUIMainPanel;
import org.opennms.features.vaadin.pluginmanager.internal.HttpServletRequestVaadinImpl;
import org.opennms.web.api.OnmsHeaderProvider;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Plugin Manager Administration Application.
 */
@Theme("opennms")
@Title("Plugin Manager Administration")
@SuppressWarnings("serial")
public class PluginManagerAdminApplication extends UI {
    private static final Logger LOG = LoggerFactory.getLogger(PluginManagerAdminApplication.class);
	
	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */



    
    
    private OnmsHeaderProvider m_headerProvider;
    private String m_headerHtml;
	private VaadinRequest m_request;
	private VerticalLayout m_rootLayout;

//    /** The OpenNMS Data Collection Configuration DAO. */
//    private DataCollectionConfigDao dataCollectionDao;
//
//    /**
//     * Sets the OpenNMS Data Collection Configuration DAO.
//     *
//     * @param dataCollectionDao the new OpenNMS data collection DAO
//     */
//    public void setDataCollectionDao(DataCollectionConfigDao dataCollectionDao) {
//        this.dataCollectionDao = dataCollectionDao;
//    }

    
    // imported ideas from org.opennms.features.vaadin.nodemaps.internal.NodeMapsApplication
    
    public OnmsHeaderProvider getHeaderProvider() {
		return m_headerProvider;
	}

	public void setHeaderProvider(OnmsHeaderProvider headerProvider) {
		this.m_headerProvider = headerProvider;
	}
	
    public void setHeaderHtml(final String headerHtml) {
        m_headerHtml = headerHtml;
    }
	
	   private void addHeader() {
	        if (m_headerProvider != null) {
	            try {
	                URL pageUrl = Page.getCurrent().getLocation().toURL();
	                setHeaderHtml(m_headerProvider.getHeaderHtml(new HttpServletRequestVaadinImpl(m_request, pageUrl)));
	            } catch (final Exception e) {
	                LOG.error("failed to get header HTML for request " + m_request.getPathInfo(), e.getCause());
	            }
	        }
	        if (m_headerHtml != null) {
	            InputStream is = null;
	            try {
	                is = new ByteArrayInputStream(m_headerHtml.getBytes());
	                final CustomLayout headerLayout = new CustomLayout(is);
	                headerLayout.setWidth("100%");
	                headerLayout.addStyleName("onmsheader");
	                m_rootLayout.addComponent(headerLayout);
	            } catch (final IOException e) {
	                closeQuietly(is);
	                LOG.debug("failed to get header layout data", e);
	            }
	        }
	    }

	    private void closeQuietly(InputStream is) {
	        if (is != null) {
	            try {
	                is.close();
	            } catch (final IOException closeE) {
	                LOG.debug("failed to close HTML input stream", closeE);
	            }
	        }
	    }
	    


	/* (non-Javadoc)
     * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
     */
    @Override
    public void init(VaadinRequest request) {
    	
    	m_request = request;

    	m_rootLayout= new VerticalLayout();
    	
    	CustomComponent pluginManagerUIMainPanel = new PluginManagerUIMainPanel();
    	
        m_rootLayout = new VerticalLayout();
        m_rootLayout.setSizeFull();
        m_rootLayout.addStyleName("root-layout");
        setContent(m_rootLayout);
        addHeader();
        
        m_rootLayout.addComponent(pluginManagerUIMainPanel);
        
        // this forces the UI panel to use up all the available space below the header
        m_rootLayout.setExpandRatio(pluginManagerUIMainPanel, 1.0f);

    }
}
