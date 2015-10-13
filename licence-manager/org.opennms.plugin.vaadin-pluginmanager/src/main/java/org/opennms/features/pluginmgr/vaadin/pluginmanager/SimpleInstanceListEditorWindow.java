package org.opennms.features.pluginmgr.vaadin.pluginmanager;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SimpleInstanceListEditorWindow extends Window {
	
	private static final long serialVersionUID = 1L;

	public SimpleInstanceListEditorWindow() {
        super("Subs on Sale"); // Set window caption
        center();
        // Disable the close button
        setClosable(false);

        // Some basic content for the window
        VerticalLayout content1 = new VerticalLayout();
        setContent(content1);
        
        SimpleInstanceListEditorWindow2 content = new SimpleInstanceListEditorWindow2();
        content1.addComponent(content);

        // Trivial logic for closing the sub-window
        Button ok = new Button("OK");
        ok.addClickListener(new ClickListener() {
 			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
                close(); // Close the sub-window
            }
        });
        content1.addComponent(ok);
    }
}