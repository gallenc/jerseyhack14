package org.opennms.features.vaadin.pluginmanager;

import org.opennms.features.vaadin.config.model.SessionPluginModel;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class AddLicencePanel extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */


	@AutoGenerated
	private HorizontalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout verticalLayout_3;
	@AutoGenerated
	private LicenceDescriptorPanel licenceDescriptorPanel;
	@AutoGenerated
	private VerticalLayout controlsVerticalLayout;
	@AutoGenerated
	private Button installLicenceButton;
	@AutoGenerated
	private Button clearLicenceStringButton;
	@AutoGenerated
	private Button verifyLicenceButton;
	private static final long serialVersionUID = 1L;

	private SessionPluginModel sessionPluginModel;

	private TextArea systemMessages;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public AddLicencePanel() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here

		// Handle the verifyLicenceButton events with an anonymous class
		verifyLicenceButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				try{
					String licenceStr = licenceDescriptorPanel.getLicenceString();
					if (!licenceDescriptorPanel.setLicenceString(licenceStr)) throw new RuntimeException("invalid licence string");
					systemMessages.setValue("licence string verified");
				} catch (Exception e){
					systemMessages.setValue(e.getMessage());
				}
				licenceDescriptorPanel.markAsDirty();
			}
		});
		
		clearLicenceStringButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				try{
					licenceDescriptorPanel.setLicenceString("");
					systemMessages.setValue("licence string cleared");
				} catch (Exception e){
					systemMessages.setValue(e.getMessage());
				}
				licenceDescriptorPanel.markAsDirty();
			}
		});

		// Handle the installLicenceButton events with an anonymous class
		installLicenceButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				try{
					String licenceStr = licenceDescriptorPanel.getLicenceString();
					if (!licenceDescriptorPanel.setLicenceString(licenceStr)) throw new RuntimeException("cannot install licence - invalid licence string");
					sessionPluginModel.addLicence(licenceStr);
					systemMessages.setValue("licence added to installed licence list");
				} catch (Exception e){
					systemMessages.setValue(e.getMessage());
				}
				licenceDescriptorPanel.markAsDirty();
			}
		});
	}

	public SessionPluginModel getSessionPluginModel() {
		return sessionPluginModel;
	}

	public void setSessionPluginModel(SessionPluginModel sessionPluginModel) {
		this.sessionPluginModel = sessionPluginModel;
	}

	/**
	 * @return the systemMessages
	 */
	public TextArea getSystemMessages() {
		return systemMessages;
	}

	/**
	 * @param systemMessages the systemMessages to set
	 */
	public void setSystemMessages(TextArea systemMessages) {
		this.systemMessages = systemMessages;
	}

	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(true);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// controlsVerticalLayout
		controlsVerticalLayout = buildControlsVerticalLayout();
		mainLayout.addComponent(controlsVerticalLayout);
		
		// verticalLayout_3
		verticalLayout_3 = buildVerticalLayout_3();
		mainLayout.addComponent(verticalLayout_3);
		
		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildControlsVerticalLayout() {
		// common part: create layout
		controlsVerticalLayout = new VerticalLayout();
		controlsVerticalLayout.setImmediate(true);
		controlsVerticalLayout.setWidth("-1px");
		controlsVerticalLayout.setHeight("-1px");
		controlsVerticalLayout.setMargin(true);
		controlsVerticalLayout.setSpacing(true);
		
		// verifyLicenceButton
		verifyLicenceButton = new Button();
		verifyLicenceButton.setCaption("Verify Licence");
		verifyLicenceButton.setImmediate(true);
		verifyLicenceButton
				.setDescription("Verifies that the licence string metadata can be read");
		verifyLicenceButton.setWidth("-1px");
		verifyLicenceButton.setHeight("-1px");
		controlsVerticalLayout.addComponent(verifyLicenceButton);
		
		// clearLicenceStringButton
		clearLicenceStringButton = new Button();
		clearLicenceStringButton.setCaption("Clear Licence");
		clearLicenceStringButton.setImmediate(true);
		clearLicenceStringButton
				.setDescription("Clears the licence string entry in this panel");
		clearLicenceStringButton.setWidth("-1px");
		clearLicenceStringButton.setHeight("-1px");
		controlsVerticalLayout.addComponent(clearLicenceStringButton);
		
		// installLicenceButton
		installLicenceButton = new Button();
		installLicenceButton.setCaption("Install Licence");
		installLicenceButton.setImmediate(true);
		installLicenceButton
				.setDescription("Installs the licence in selected karaf container");
		installLicenceButton.setWidth("-1px");
		installLicenceButton.setHeight("-1px");
		controlsVerticalLayout.addComponent(installLicenceButton);
		
		return controlsVerticalLayout;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_3() {
		// common part: create layout
		verticalLayout_3 = new VerticalLayout();
		verticalLayout_3.setImmediate(true);
		verticalLayout_3.setWidth("-1px");
		verticalLayout_3.setHeight("-1px");
		verticalLayout_3.setMargin(false);
		
		// licenceDescriptorPanel
		licenceDescriptorPanel = new LicenceDescriptorPanel();
		licenceDescriptorPanel.setImmediate(true);
		licenceDescriptorPanel.setWidth("-1px");
		licenceDescriptorPanel.setHeight("-1px");
		verticalLayout_3.addComponent(licenceDescriptorPanel);
		
		return verticalLayout_3;
	}

}
