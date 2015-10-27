package org.opennms.features.pluginmgr.vaadin.pluginmanager;

import org.opennms.features.pluginmgr.SessionPluginManager;
import org.osgi.service.blueprint.container.BlueprintContainer;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ManualManifestEditorWindow extends Window {
	
	private static final long serialVersionUID = 1L;
	
	private SessionPluginManager sessionPluginManager=null;
	

	public ManualManifestEditorWindow(SessionPluginManager sessionPluginManager) {
        super("New Manifest Editor"); // Set window caption
        
		this.sessionPluginManager=sessionPluginManager;
		
        center();
        // Disable the close button
        setClosable(false);

        // get editor from blueprint or use SimpleManualManifestEditor
        ManualManifestEditor manualManifestEditor= new ManualManifestEditor();

        manualManifestEditor.setSessionPluginManager(sessionPluginManager);
        manualManifestEditor.setParentWindow(this);
        
        VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout1.addComponent(manualManifestEditor);
        setContent(verticalLayout1);

    }


}