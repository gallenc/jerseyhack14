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

package org.opennms.features.pluginmgr.vaadin.config.karaf;

//import org.opennms.features.vaadin.datacollection.SnmpCollectionPanel;
//import org.opennms.netmgt.config.api.DataCollectionConfigDao;


import java.util.Map;

import org.opennms.features.pluginmgr.SessionPluginManager;
import org.opennms.features.pluginmgr.vaadin.pluginmanager.PluginManagerUIMainPanel;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Plugin Manager Administration Application.
 */
//@Theme("opennms")
@Theme("valo")
@Title("Plugin Manager Administration")
@SuppressWarnings("serial")
public class PluginManagerAdminApplication extends UI {

	private static final Logger LOG = LoggerFactory.getLogger(PluginManagerAdminApplication.class);

	//headerLinks map of key= name and value=url for links to be placed in header of page
    private Map<String,String> headerLinks;
    
	private VaadinRequest m_request;
	
	private VerticalLayout m_rootLayout;
	
	private Component headerComponent=null;

	private SessionPluginManager sessionPluginManager;
	
	public SessionPluginManager getSessionPluginManager() {
		return sessionPluginManager;
	}

	public void setSessionPluginManager(SessionPluginManager sessionPluginManager) {
		this.sessionPluginManager = sessionPluginManager;
	}

	/**
	 * headerLinks map of key= name and value=url for links to be placed in header of page
	 * @return
	 */
	public Map<String,String> getHeaderLinks() {
		return headerLinks;
	}

	/**
	 * @param headerLinks map of key= name and value=url for links to be placed in header of page
	 */
	public void setHeaderLinks(Map<String,String> headerLinks) {
		this.headerLinks = headerLinks;
	}
	
	/**
	 * @return the headerComponent
	 */
	public Component getHeaderComponent() {
		return headerComponent;
	}

	/**
	 * @param headerComponent the headerComponent to set
	 */
	public void setHeaderComponent(Component headerComponent) {
		this.headerComponent = headerComponent;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
	 */
	@Override
	public void init(VaadinRequest request) {

		m_request = request;

		m_rootLayout = new VerticalLayout();
		m_rootLayout.setSizeFull();
		m_rootLayout.addStyleName("root-layout");
		setContent(m_rootLayout);
		
		// add header if provided
		if(headerComponent!=null) m_rootLayout.addComponent(headerComponent);
		
		//add additional header page links if provided
		if(headerLinks!=null) {
			// defining 2 horizontal layouts to force links to stay together
			HorizontalLayout horizontalLayout1= new HorizontalLayout();
			horizontalLayout1.setWidth("100%");
			horizontalLayout1.setDefaultComponentAlignment(Alignment.TOP_RIGHT);
			HorizontalLayout horizontalLayout2= new HorizontalLayout();
			horizontalLayout1.addComponent(horizontalLayout2);

			for(String name: headerLinks.keySet()){
				String urlStr=headerLinks.get(name);
				ExternalResource urlResource=new ExternalResource(urlStr);
				Link link = new Link(name, urlResource);
				Label label= new Label("&nbsp;&nbsp;&nbsp;", ContentMode.HTML); // adds space between links
				horizontalLayout2.addComponent(link);
				horizontalLayout2.addComponent(label);
			}
			m_rootLayout.addComponent(horizontalLayout1);
		}

		PluginManagerUIMainPanel pluginManagerUIMainPanel = new PluginManagerUIMainPanel(sessionPluginManager);
		
		m_rootLayout.addComponent(pluginManagerUIMainPanel);

		// this forces the UI panel to use up all the available space below the header
		m_rootLayout.setExpandRatio(pluginManagerUIMainPanel, 1.0f);

	}
}