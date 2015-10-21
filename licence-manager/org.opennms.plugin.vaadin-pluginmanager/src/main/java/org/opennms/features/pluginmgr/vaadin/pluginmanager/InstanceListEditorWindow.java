package org.opennms.features.pluginmgr.vaadin.pluginmanager;

import org.opennms.features.pluginmgr.SessionPluginManager;
import org.opennms.features.pluginmgr.vaadin.config.SimpleStackTrace;
import org.osgi.service.blueprint.container.BlueprintContainer;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class InstanceListEditorWindow extends Window {
	


	private static final long serialVersionUID = 1L;
	
	private SessionPluginManager sessionPluginManager=null;
	

	public InstanceListEditorWindow(SessionPluginManager sessionPluginManager) {
        super("Karaf List Editor"); // Set window caption
        
		this.sessionPluginManager=sessionPluginManager;
		
        center();
        // Disable the close button
        setClosable(false);

        // get editor from blueprint or use SimpleInstanceListEditor
        InstanceListEditor instanceListEditor= new SimpleInstanceListEditor();
        try {
        	BlueprintContainer container = sessionPluginManager.getBlueprintContainer();
        	InstanceListEditor factoryInstanceListEditor = (InstanceListEditor) container.getComponentInstance("instanceListEditor");
        	if (factoryInstanceListEditor!=null ) {
        		instanceListEditor = factoryInstanceListEditor;
        	} else {
        		//TODO LOG MESSAGE
        		System.out.println("instanceListEditor not defined in blueprintContainer. Using SimpleInstanceListEditor");
        	}
        } catch (Exception e) {
            throw new RuntimeException("problem loading InstanceListEditor from blueprintContainer: ", e);
        }
        
        instanceListEditor.setSessionPluginManager(sessionPluginManager);
        instanceListEditor.setParentWindow(this);
        
        VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout1.addComponent(instanceListEditor);
        setContent(verticalLayout1);

    }


}