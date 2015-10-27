package org.opennms.features.pluginmgr.vaadin.pluginmanager;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ErrorMessageWindow  extends Window {
	
	private static final long serialVersionUID = 1L;

		public ErrorMessageWindow(SystemMessages systemMessages) {
	        super("Full Log Message"); // Set window caption
	        center();
	        setHeight("75%");
	        setWidth("75%");
	        
	        // Some basic content for the window
	        VerticalLayout content = new VerticalLayout();
	        content.setSizeFull();

	        TextArea ta= new TextArea();
	        ta.setWordwrap(true);
			ta.setImmediate(true);
			ta.setWidth("100%");
			ta.setHeight("100%");
	        content.addComponent(ta);
			content.setExpandRatio(ta, 1.0f);
	        
	        if (systemMessages!=null) {
	        	ta.setValue(systemMessages.getMessage());
	        } else {
	        	ta.setValue("Error: systemMessages should not be null");
	        }
	        ta.setReadOnly(false);
	        
	        content.setMargin(true);
	        setContent(content);
	        
	        // Disable the close button
	        setClosable(false);

	        // Trivial logic for closing the sub-window
	        Button ok = new Button("OK");
	        ok.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
	                close(); // Close the sub-window
	            }
	        });
	        content.addComponent(ok);
	    }
	}