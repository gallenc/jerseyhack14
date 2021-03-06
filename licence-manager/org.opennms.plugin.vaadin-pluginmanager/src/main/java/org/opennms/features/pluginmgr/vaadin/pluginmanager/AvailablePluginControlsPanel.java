package org.opennms.features.pluginmgr.vaadin.pluginmanager;

import org.opennms.features.pluginmgr.SessionPluginManager;
import org.opennms.features.pluginmgr.vaadin.config.SimpleStackTrace;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class AvailablePluginControlsPanel extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;

	@AutoGenerated
	private Button addToManifestButton;

	@AutoGenerated
	private Button installPluginButton;

	private static final long serialVersionUID = 1L;

	private SessionPluginManager sessionPluginManager=null;

	private ProductDescriptorTablePanel productDescriptorTablePanel=null;

	private SystemMessages systemMessages;
	
	private boolean remoteUpdateControlsEnabled=true;

	public void setRemoteUpdateControlsEnabled(boolean remoteUpdateControlsEnabled) {
		this.remoteUpdateControlsEnabled = remoteUpdateControlsEnabled;
		// set state of update control buttons to remoteUpdateControlsEnabled 
		installPluginButton.setEnabled(remoteUpdateControlsEnabled);
	}


	public void setSessionPluginManager(SessionPluginManager sessionPluginManager) {
		this.sessionPluginManager = sessionPluginManager;
	}


	public void setProductDescriptorTablePanel(
			ProductDescriptorTablePanel productDescriptorTablePanel) {
		this.productDescriptorTablePanel = productDescriptorTablePanel;
	}

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public AvailablePluginControlsPanel() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here

		// Handle the installPluginButton events with an anonymous class
		installPluginButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				try{
					String selectedProductId = productDescriptorTablePanel.getSelectedProductId();
					sessionPluginManager.installPlugin(selectedProductId);
					systemMessages.setValue("installed product Id "+selectedProductId);
				} catch (Exception e){
					systemMessages.setValue(SimpleStackTrace.errorToString(e));
				}
			}
		});
		
		// Handle the addToManifestButton events with an anonymous class
		addToManifestButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				try{
					String selectedProductId = productDescriptorTablePanel.getSelectedProductId();
					sessionPluginManager.addPluginToManifest(selectedProductId);
					systemMessages.setValue("added product Id "+selectedProductId+" to karaf Manifest for karaf instance "+ sessionPluginManager.getKarafInstance());
				} catch (Exception e){
					systemMessages.setValue(SimpleStackTrace.errorToString(e));
				}
			}
		});
	}

	public void setSystemMessages(SystemMessages systemMessages) {
		this.systemMessages=systemMessages;

	}


	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(true);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// installPluginButton
		installPluginButton = new Button();
		installPluginButton.setCaption("Install Selected Plugin");
		installPluginButton.setImmediate(true);
		installPluginButton.setWidth("-1px");
		installPluginButton.setHeight("-1px");
		mainLayout.addComponent(installPluginButton);
		
		// addToManifestButton
		addToManifestButton = new Button();
		addToManifestButton.setCaption("Add Selected Plugin to Manifest");
		addToManifestButton.setImmediate(true);
		addToManifestButton
				.setDescription("Adds the selected plugin to Manifest for selected karaf instance");
		addToManifestButton.setWidth("-1px");
		addToManifestButton.setHeight("-1px");
		mainLayout.addComponent(addToManifestButton);
		
		return mainLayout;
	}


}
