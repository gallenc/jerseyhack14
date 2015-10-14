package org.opennms.features.pluginmgr.vaadin.pluginmanager;

import org.opennms.features.pluginmgr.SessionPluginManager;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class InstanceListEditorWindow extends Window {
	


	private static final long serialVersionUID = 1L;
	
	private SessionPluginManager sessionPluginManager=null;
	

	public InstanceListEditorWindow(SessionPluginManager sessionPluginManager) {
        super("Subs on Sale"); // Set window caption
        
		this.sessionPluginManager=sessionPluginManager;
		
        center();
        // Disable the close button
        setClosable(false);

        // Some basic content for the window
        VerticalLayout content1 = new VerticalLayout();
        setContent(content1);
        
        InstanceListEditor content = new SimpleInstanceListEditor();
        content.setSessionPluginManager(sessionPluginManager);
        content.setParentWindow(this);
        
        content1.addComponent(content);


    }


}