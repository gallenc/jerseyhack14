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

package org.opennms.features.pluginmgr.vaadin.pluginmanager;

import org.opennms.features.pluginmgr.SessionPluginManager;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ManualManifestEditor extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private HorizontalLayout mainLayout;

	@AutoGenerated
	private VerticalLayout newManifestVerticalLayout;

	@AutoGenerated
	private VerticalLayout editManifestControlsVerticalLayout;

	@AutoGenerated
	private TextArea messageTextArea;

	@AutoGenerated
	private Button cancelAndExitButton;

	@AutoGenerated
	private Button saveAndExitButton;

	private static final long serialVersionUID = 1L;

	private SessionPluginManager sessionPluginManager=null;

	private Window parentWindow=null;

	private ProductMetadata productMetadata = new ProductMetadata();


	/**
	 * @return the sessionPluginManager
	 */
	public SessionPluginManager getSessionPluginManager() {
		return sessionPluginManager;
	}


	/**
	 * @param sessionPluginManager the sessionPluginManager to set
	 */
	public void setSessionPluginManager(SessionPluginManager sessionPluginManager) {
		this.sessionPluginManager = sessionPluginManager;
	}


	public void setParentWindow(Window window) {
		this.parentWindow=window;
	}

	public Window getParentWindow() {
		return this.parentWindow;
	}

	public Layout getMainLayout(){
		return mainLayout;
	}


	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ManualManifestEditor() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		productMetadata.setProductDescription("USER ADDED PLUGIN MANIFEST");
		
		ProductDescriptorPanel productDescriptorPanel = new ProductDescriptorPanel();
		productDescriptorPanel.setNoUpdate(false);
		productDescriptorPanel.setProductMetadata(productMetadata);
		newManifestVerticalLayout.addComponent(productDescriptorPanel);

		messageTextArea.setReadOnly(false);
		messageTextArea.setValue("enter product manifest values and save");
		messageTextArea.setReadOnly(true);

		// save and exit button
		saveAndExitButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {

				productMetadata=productDescriptorPanel.getProductMetadata();
				messageTextArea.setReadOnly(false);
				messageTextArea.setValue("enter product manifest values and save");
				if(productMetadata.getProductId()==null ||"".equals(productMetadata.getProductId())) {
					messageTextArea.setValue("you must set a productId of the form <artifactId>[/<version>] (where version is optional)");
				} else if(productMetadata.getFeatureRepository()==null ||
						"".equals(productMetadata.getFeatureRepository()) ||
						! productMetadata.getFeatureRepository().startsWith("mvn:")){
					messageTextArea.setValue("you must set a feature repository url of the form\n"
							+ "mvn:<groupId>/<artifactId>/<version>/xml/features");
				} else {
					try{
						sessionPluginManager.addUserDefinedPluginToManifest(productMetadata);
						if(getParentWindow()!=null) getParentWindow().close(); // Close the sub-window
					} catch (Exception e){
						messageTextArea.setValue("problem adding manifest: "+ e.getMessage());
					}
				}
				messageTextArea.setReadOnly(true);

			}
		});

		// exit button
		cancelAndExitButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				if(getParentWindow()!=null) getParentWindow().close(); // Close the sub-window
			}
		});
	}

	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// editManifestControlsVerticalLayout
		editManifestControlsVerticalLayout = buildEditManifestControlsVerticalLayout();
		mainLayout.addComponent(editManifestControlsVerticalLayout);

		// newManifestVerticalLayout
		newManifestVerticalLayout = new VerticalLayout();
		newManifestVerticalLayout.setImmediate(false);
		newManifestVerticalLayout.setWidth("-1px");
		newManifestVerticalLayout.setHeight("-1px");
		newManifestVerticalLayout.setMargin(true);
		newManifestVerticalLayout.setSpacing(true);
		mainLayout.addComponent(newManifestVerticalLayout);

		return mainLayout;
	}


	@AutoGenerated
	private VerticalLayout buildEditManifestControlsVerticalLayout() {
		// common part: create layout
		editManifestControlsVerticalLayout = new VerticalLayout();
		editManifestControlsVerticalLayout.setImmediate(false);
		editManifestControlsVerticalLayout.setWidth("-1px");
		editManifestControlsVerticalLayout.setHeight("-1px");
		editManifestControlsVerticalLayout.setMargin(true);
		editManifestControlsVerticalLayout.setSpacing(true);

		// saveAndExitButton
		saveAndExitButton = new Button();
		saveAndExitButton.setCaption("Save Manifest and Exit");
		saveAndExitButton.setImmediate(true);
		saveAndExitButton.setWidth("-1px");
		saveAndExitButton.setHeight("-1px");
		editManifestControlsVerticalLayout.addComponent(saveAndExitButton);

		// cancelAndExitButton
		cancelAndExitButton = new Button();
		cancelAndExitButton.setCaption("Cancel and Exit");
		cancelAndExitButton.setImmediate(true);
		cancelAndExitButton.setWidth("-1px");
		cancelAndExitButton.setHeight("-1px");
		editManifestControlsVerticalLayout.addComponent(cancelAndExitButton);

		// messageTextArea
		messageTextArea = new TextArea();
		messageTextArea.setImmediate(false);
		messageTextArea.setWidth("-1px");
		messageTextArea.setHeight("-1px");
		editManifestControlsVerticalLayout.addComponent(messageTextArea);

		return editManifestControlsVerticalLayout;
	}



}
