package org.opennms.features.vaadin.pluginmanager;

import org.opennms.features.vaadin.config.model.SessionPluginModel;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class InstalledPluginControlsPanel extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = 1L;

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Button uninstallPluginButton;

	private SessionPluginModel sessionPluginModel=null;

	private ProductDescriptorTablePanel productDescriptorTablePanel=null;

	private TextArea systemMessages;


	public void setSessionPluginModel(SessionPluginModel sessionPluginModel) {
		this.sessionPluginModel = sessionPluginModel;
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
	public InstalledPluginControlsPanel() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		// Handle the installPluginButton events with an anonymous class
		uninstallPluginButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				try{
					String selectedProductId = productDescriptorTablePanel.getSelectedProductId();
					sessionPluginModel.unInstallPlugin(selectedProductId);
					systemMessages.setValue("uninstalled product Id "+selectedProductId);
				} catch (Exception e){
					systemMessages.setValue(e.getMessage());
				}
			}
		});
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(true);

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// uninstallPluginButton
		uninstallPluginButton = new Button();
		uninstallPluginButton.setCaption("Uninstall Selected Plugin");
		uninstallPluginButton.setImmediate(false);
		uninstallPluginButton.setWidth("-1px");
		uninstallPluginButton.setHeight("-1px");
		mainLayout.addComponent(uninstallPluginButton);

		return mainLayout;
	}

	public void setSystemMessages(TextArea systemMessages) {
		this.systemMessages=systemMessages;

	}
}
