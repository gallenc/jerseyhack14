package org.opennms.features.vaadin.pluginmanager;

import java.text.Format;
import java.text.SimpleDateFormat;

import org.opennms.features.vaadin.config.model.SessionPluginModel;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Link;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PluginManagerUIMainPanelImpl2 extends CustomComponent {



	/*- VaadinEditorProperties={"grid":"RegularGrid,5","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;

	@AutoGenerated
	private HorizontalSplitPanel pluginMgrHorSplitPanel_1;

	@AutoGenerated
	private VerticalLayout featureSettingsPanel;

	@AutoGenerated
	private TextArea systemMessages;

	@AutoGenerated
	private TabSheet featuresTabSheet_1;

	@AutoGenerated
	private VerticalLayout addLicenceTab;

	@AutoGenerated
	private AddLicencePanel addLicencePanel;

	@AutoGenerated
	private VerticalLayout installedLicencesTab;

	@AutoGenerated
	private LicenceDescriptorTablePanel licenceDescriptorTablePanel;

	@AutoGenerated
	private VerticalLayout availablePluginsTab;

	@AutoGenerated
	private ProductDescriptorTablePanel availablePluginsPanel;

	@AutoGenerated
	private VerticalLayout installedPluginsTab;

	@AutoGenerated
	private ProductDescriptorTablePanel installedPluginsPanel;

	@AutoGenerated
	private VerticalLayout generalSettingsPanel;

	@AutoGenerated
	private VerticalLayout systemIdPanel;

	@AutoGenerated
	private VerticalLayout verticalLayout_3;

	@AutoGenerated
	private Button generateRandomSystemIdButton;

	@AutoGenerated
	private Button updateSystemIdButton;

	@AutoGenerated
	private TextField systemIdTxtField;

	@AutoGenerated
	private HorizontalLayout horizontalLayout_4;

	@AutoGenerated
	private VerticalLayout verticalLayout_2;

	@AutoGenerated
	private Link openShoppingCartLink;

	@AutoGenerated
	private Button updatePluginServerButton;

	@AutoGenerated
	private VerticalLayout pluginServerConfigPanel;

	@AutoGenerated
	private TextField licenceShoppingCartUrlTxtField;

	@AutoGenerated
	private TextField pluginServerPasswordTxtField;

	@AutoGenerated
	private TextField pluginServerUsernameTxtField;

	@AutoGenerated
	private TextField pluginServerUrlTxtField;

	@AutoGenerated
	private HorizontalLayout horizontalLayout_1;

	@AutoGenerated
	private TextField availablepluginsLastUpdatedTextField;

	@AutoGenerated
	private Button reloadAvailablePluginsButton;

	@AutoGenerated
	private HorizontalLayout horizontalLayout_3;

	@AutoGenerated
	private VerticalLayout verticalLayout_4;

	@AutoGenerated
	private Button reloadKarafDataButton;

	@AutoGenerated
	private TextField karafLastUpdatedTextField;

	@AutoGenerated
	private TextField karafInstanceSelectedTextField;

	@AutoGenerated
	private ListSelect karafListSelect;

	private SessionPluginModel sessionPluginModel;

	private static final long serialVersionUID = 1L;


	public SessionPluginModel getSessionPluginModel() {
		return sessionPluginModel;
	}

	public void setSessionPluginModel(SessionPluginModel sessionPluginModel) {
		this.sessionPluginModel = sessionPluginModel;
	}

	private void updateDisplayValues(){

		String karafInstanceSelected= (sessionPluginModel.getKarafInstance()==null) ? "" : sessionPluginModel.getKarafInstance().toString();
		karafInstanceSelectedTextField.setValue(karafInstanceSelected);

		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String karaflastupdate= (sessionPluginModel.getKarafInstanceLastUpdated()==null) ? "never updated" : formatter.format(sessionPluginModel.getKarafInstanceLastUpdated());
		karafLastUpdatedTextField.setValue(karaflastupdate);

		String pluginslastupdate = (sessionPluginModel.getAvailablePluginsLastUpdated()==null) ? "never updated" : formatter.format(sessionPluginModel.getAvailablePluginsLastUpdated());
		availablepluginsLastUpdatedTextField.setValue(pluginslastupdate);

		ProductSpecList availablePluginsList = sessionPluginModel.getAvailablePlugins();
		if (availablePluginsList!=null) availablePluginsPanel.addProductList(availablePluginsList);
		ProductSpecList installedPluginsList = sessionPluginModel.getInstalledPlugins();
		if (installedPluginsList!=null) installedPluginsPanel.addProductList(installedPluginsList);
		LicenceList licencelist = sessionPluginModel.getInstalledLicenceList();
		if (licencelist!=null) licenceDescriptorTablePanel.addLicenceList(licencelist);

		systemIdTxtField.setValue(sessionPluginModel.getSystemId());
		pluginServerPasswordTxtField.setValue(sessionPluginModel.getPluginServerPassword());
		pluginServerUsernameTxtField.setValue(sessionPluginModel.getPluginServerUsername());
		pluginServerUrlTxtField.setValue(sessionPluginModel.getPluginServerUrl());
		licenceShoppingCartUrlTxtField.setValue(sessionPluginModel.getLicenceShoppingCartUrl());

		ExternalResource resource= new ExternalResource(sessionPluginModel.getLicenceShoppingCartUrl());
		openShoppingCartLink.setResource(resource);

		mainLayout.markAsDirty();

	}

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */

	public PluginManagerUIMainPanelImpl2(SessionPluginModel sessionPluginModel) {
		this.sessionPluginModel=sessionPluginModel;

		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here

		// SystemID Panel
		systemIdTxtField.setValue(sessionPluginModel.getSystemId());

		updateSystemIdButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				// update systemId
				try{
					sessionPluginModel.setSystemId(systemIdTxtField.getValue());
					systemMessages.setValue("systemId updated to "+systemIdTxtField.getValue());
					updateDisplayValues();
				} catch (Exception e){
					systemMessages.setValue("problem updating systemId : "+e.getMessage());
				}
			}
		});

		generateRandomSystemIdButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				// generate random systemId
				try{
					sessionPluginModel.generateRandomSystemId();
					systemMessages.setValue("random systemId generated "+systemIdTxtField.getValue());
					updateDisplayValues();
				} catch (Exception e){
					systemMessages.setValue("problem generating random systemId : "+e.getMessage());
				}

			}
		});

		// plugin Server Panel
		pluginServerPasswordTxtField.setValue(sessionPluginModel.getPluginServerPassword());
		pluginServerUsernameTxtField.setValue(sessionPluginModel.getPluginServerUsername());
		pluginServerUrlTxtField.setValue(sessionPluginModel.getPluginServerUrl());

		licenceShoppingCartUrlTxtField.setValue(sessionPluginModel.getLicenceShoppingCartUrl());
		ExternalResource resource= new ExternalResource(sessionPluginModel.getLicenceShoppingCartUrl());
		openShoppingCartLink.setResource(resource);
		openShoppingCartLink.setTargetName("_blank"); // Open the URL in a new window/tab

		updatePluginServerButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				// update plugin server
				try{

					String pluginServerUsername=pluginServerUsernameTxtField.getValue();
					String pluginServerPassword = pluginServerPasswordTxtField.getValue();
					String pluginServerUrl= pluginServerUrlTxtField.getValue();
					String licenceShoppingCartUrl=licenceShoppingCartUrlTxtField.getValue();

					sessionPluginModel.setPluginModelBasicData(pluginServerUsername, pluginServerPassword, pluginServerUrl, licenceShoppingCartUrl);	

					systemMessages.setValue("plugin server password updated to "+pluginServerPasswordTxtField.getValue()
							+"\nplugin server username updated to "+pluginServerUsernameTxtField.getValue()
							+ "\nplugin server URL updated to "+pluginServerUrlTxtField.getValue()
							+ "\nlicence Shopping Cart URL updated to "+licenceShoppingCartUrlTxtField.getValue()
							+ "\n");

					updateDisplayValues();
				} catch (Exception e){
					systemMessages.setValue("problem updating plugin server data : "+e.getMessage());
				}

			}
		});


		// karaf select panel

		karafListSelect.setRows(5); 	// Show 5 items and a scrollbar if there are more
		karafListSelect.setNullSelectionAllowed(false);
		karafListSelect.setImmediate(true);

		// add available instances from opennms
		for (String instanceName: sessionPluginModel.getKarafInstances().keySet()){
			karafListSelect.addItem(instanceName);
		};


		// Feedback on value changes
		karafListSelect.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				systemMessages.setValue("");
				String karafInstanceSelected=null;
				try {
					if (karafListSelect.getValue()!=null) {
						karafInstanceSelected = karafListSelect.getValue().toString();
						sessionPluginModel.setKarafInstance(karafInstanceSelected);
					}
					systemMessages.setValue("karaf instance changed to "+karafInstanceSelected);
				} catch (Exception e){
					systemMessages.setValue("problem changing karaf instance : "+e.getMessage());
				}
				updateDisplayValues();
			}
		});

		// Handle the reloadKarafDataButton events with an anonymous class
		reloadKarafDataButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				//reload data from plugin model
				try{
					sessionPluginModel.refreshKarafEntry();
					systemMessages.setValue("karaf data for karaf instance successfully reloaded");
				} catch (Exception e){
					systemMessages.setValue("problem reloading data for karaf instance : "+e.getMessage());
				}
				updateDisplayValues();

			}
		});

		// Handle the reloadAvailablePluginsButton events with an anonymous class
		reloadAvailablePluginsButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				//reload data from plugin model
				try{
					sessionPluginModel.refreshAvailablePlugins();
					systemMessages.setValue("available plugin data successfully reloaded");
				} catch (Exception e){
					systemMessages.setValue("problem retrieving available plugin data : "+e.getMessage());
				}
				updateDisplayValues();
			}
		});


		// Plugins Detail tabs panel

		// update tab content dynamically when tab is selected
		featuresTabSheet_1.addSelectedTabChangeListener( new TabSheet.SelectedTabChangeListener() {
			private static final long serialVersionUID = 1L;

			public void selectedTabChange(SelectedTabChangeEvent event){
				updateDisplayValues();
			}
		});

		// Available Plugins Tab

		ProductSpecList availablePluginsList = sessionPluginModel.getAvailablePlugins();
		if (availablePluginsList!=null) availablePluginsPanel.addProductList(availablePluginsList);
		AvailablePluginControlsPanel  availablePluginsControlsPanel= new AvailablePluginControlsPanel();
		availablePluginsControlsPanel.setSessionPluginModel(sessionPluginModel);
		availablePluginsControlsPanel.setProductDescriptorTablePanel(availablePluginsPanel);
		availablePluginsControlsPanel.setSystemMessages(systemMessages);
		availablePluginsPanel.getControlsVerticalLayout().addComponent( availablePluginsControlsPanel);

		// Installed Plugins Tab

		ProductSpecList installedPluginsList = sessionPluginModel.getInstalledPlugins();
		if (installedPluginsList!=null) installedPluginsPanel.addProductList(installedPluginsList);
		InstalledPluginControlsPanel  installedPluginsControlsPanel= new InstalledPluginControlsPanel();
		installedPluginsControlsPanel.setSessionPluginModel(sessionPluginModel);
		installedPluginsControlsPanel.setProductDescriptorTablePanel(installedPluginsPanel);
		installedPluginsControlsPanel.setSystemMessages(systemMessages);
		installedPluginsPanel.getControlsVerticalLayout().addComponent( installedPluginsControlsPanel);

		// Installed Licences Tab

		LicenceList licencelist = sessionPluginModel.getInstalledLicenceList();
		if (licencelist!=null) licenceDescriptorTablePanel.addLicenceList(licencelist);

		InstalledLicencesControlsPanel  installedLicencesControlsPanel= new InstalledLicencesControlsPanel();
		installedLicencesControlsPanel.setSessionPluginModel(sessionPluginModel);		
		installedLicencesControlsPanel.setLicenceDescriptorTablePanel(licenceDescriptorTablePanel);

		installedLicencesControlsPanel.setSystemMessages(systemMessages);
		licenceDescriptorTablePanel.getControlsVerticalLayout().addComponent( installedLicencesControlsPanel);

		// Add Licences Tab

		addLicencePanel.setSessionPluginModel(sessionPluginModel);
		addLicencePanel.setSystemMessages(systemMessages);

		// update the display
		updateDisplayValues();

	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(true);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// pluginMgrHorSplitPanel_1
		pluginMgrHorSplitPanel_1 = buildPluginMgrHorSplitPanel_1();
		mainLayout.addComponent(pluginMgrHorSplitPanel_1);

		return mainLayout;
	}

	@AutoGenerated
	private HorizontalSplitPanel buildPluginMgrHorSplitPanel_1() {
		// common part: create layout
		pluginMgrHorSplitPanel_1 = new HorizontalSplitPanel();
		pluginMgrHorSplitPanel_1.setCaption("Plugin Manager Settings");
		pluginMgrHorSplitPanel_1.setImmediate(true);
		pluginMgrHorSplitPanel_1.setWidth("100.0%");
		pluginMgrHorSplitPanel_1.setHeight("100.0%");

		// generalSettingsPanel
		generalSettingsPanel = buildGeneralSettingsPanel();
		pluginMgrHorSplitPanel_1.addComponent(generalSettingsPanel);

		// featureSettingsPanel
		featureSettingsPanel = buildFeatureSettingsPanel();
		pluginMgrHorSplitPanel_1.addComponent(featureSettingsPanel);

		return pluginMgrHorSplitPanel_1;
	}

	@AutoGenerated
	private VerticalLayout buildGeneralSettingsPanel() {
		// common part: create layout
		generalSettingsPanel = new VerticalLayout();
		generalSettingsPanel.setCaption("General Settings");
		generalSettingsPanel.setImmediate(true);
		generalSettingsPanel.setWidth("-1px");
		generalSettingsPanel.setHeight("-1px");
		generalSettingsPanel.setMargin(true);

		// horizontalLayout_3
		horizontalLayout_3 = buildHorizontalLayout_3();
		generalSettingsPanel.addComponent(horizontalLayout_3);

		// horizontalLayout_1
		horizontalLayout_1 = buildHorizontalLayout_1();
		generalSettingsPanel.addComponent(horizontalLayout_1);

		// horizontalLayout_4
		horizontalLayout_4 = buildHorizontalLayout_4();
		generalSettingsPanel.addComponent(horizontalLayout_4);

		// systemIdPanel
		systemIdPanel = buildSystemIdPanel();
		generalSettingsPanel.addComponent(systemIdPanel);

		return generalSettingsPanel;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_3() {
		// common part: create layout
		horizontalLayout_3 = new HorizontalLayout();
		horizontalLayout_3.setImmediate(true);
		horizontalLayout_3.setWidth("-1px");
		horizontalLayout_3.setHeight("-1px");
		horizontalLayout_3.setMargin(false);
		horizontalLayout_3.setSpacing(true);

		// karafListSelect
		karafListSelect = new ListSelect();
		karafListSelect.setCaption("Select Karaf Instance");
		karafListSelect.setImmediate(true);
		karafListSelect
		.setDescription("Selects which Karaf instance you are looking at");
		karafListSelect.setWidth("-1px");
		karafListSelect.setHeight("-1px");
		horizontalLayout_3.addComponent(karafListSelect);

		// verticalLayout_4
		verticalLayout_4 = buildVerticalLayout_4();
		horizontalLayout_3.addComponent(verticalLayout_4);

		return horizontalLayout_3;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_4() {
		// common part: create layout
		verticalLayout_4 = new VerticalLayout();
		verticalLayout_4.setImmediate(true);
		verticalLayout_4.setWidth("-1px");
		verticalLayout_4.setHeight("-1px");
		verticalLayout_4.setMargin(true);
		verticalLayout_4.setSpacing(true);

		// karafInstanceSelectedTextField
		karafInstanceSelectedTextField = new TextField();
		karafInstanceSelectedTextField.setCaption("Karaf Instance Selected");
		karafInstanceSelectedTextField.setImmediate(true);
		karafInstanceSelectedTextField.setWidth("-1px");
		karafInstanceSelectedTextField.setHeight("-1px");
		verticalLayout_4.addComponent(karafInstanceSelectedTextField);

		// karafLastUpdatedTextField
		karafLastUpdatedTextField = new TextField();
		karafLastUpdatedTextField.setCaption("Karaf  Last Updated");
		karafLastUpdatedTextField.setImmediate(true);
		karafLastUpdatedTextField.setWidth("-1px");
		karafLastUpdatedTextField.setHeight("-1px");
		verticalLayout_4.addComponent(karafLastUpdatedTextField);

		// reloadKarafDataButton
		reloadKarafDataButton = new Button();
		reloadKarafDataButton.setCaption("Reload Karaf Data");
		reloadKarafDataButton.setImmediate(true);
		reloadKarafDataButton.setWidth("-1px");
		reloadKarafDataButton.setHeight("-1px");
		verticalLayout_4.addComponent(reloadKarafDataButton);

		return verticalLayout_4;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_1() {
		// common part: create layout
		horizontalLayout_1 = new HorizontalLayout();
		horizontalLayout_1.setImmediate(true);
		horizontalLayout_1.setWidth("-1px");
		horizontalLayout_1.setHeight("-1px");
		horizontalLayout_1.setMargin(true);
		horizontalLayout_1.setSpacing(true);

		// reloadAvailablePluginsButton
		reloadAvailablePluginsButton = new Button();
		reloadAvailablePluginsButton.setCaption("Reload Available Plugins");
		reloadAvailablePluginsButton.setImmediate(true);
		reloadAvailablePluginsButton.setWidth("-1px");
		reloadAvailablePluginsButton.setHeight("-1px");
		horizontalLayout_1.addComponent(reloadAvailablePluginsButton);
		horizontalLayout_1.setComponentAlignment(reloadAvailablePluginsButton,
				new Alignment(9));

		// availablepluginsLastUpdatedTextField
		availablepluginsLastUpdatedTextField = new TextField();
		availablepluginsLastUpdatedTextField
		.setCaption("Available Pugins Last Updated");
		availablepluginsLastUpdatedTextField.setImmediate(true);
		availablepluginsLastUpdatedTextField.setWidth("-1px");
		availablepluginsLastUpdatedTextField.setHeight("-1px");
		horizontalLayout_1.addComponent(availablepluginsLastUpdatedTextField);

		return horizontalLayout_1;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_4() {
		// common part: create layout
		horizontalLayout_4 = new HorizontalLayout();
		horizontalLayout_4.setImmediate(true);
		horizontalLayout_4.setWidth("-1px");
		horizontalLayout_4.setHeight("-1px");
		horizontalLayout_4.setMargin(false);

		// pluginServerConfigPanel
		pluginServerConfigPanel = buildPluginServerConfigPanel();
		horizontalLayout_4.addComponent(pluginServerConfigPanel);

		// verticalLayout_2
		verticalLayout_2 = buildVerticalLayout_2();
		horizontalLayout_4.addComponent(verticalLayout_2);
		horizontalLayout_4.setComponentAlignment(verticalLayout_2,
				new Alignment(48));

		return horizontalLayout_4;
	}

	@AutoGenerated
	private VerticalLayout buildPluginServerConfigPanel() {
		// common part: create layout
		pluginServerConfigPanel = new VerticalLayout();
		pluginServerConfigPanel.setCaption("Plugin Server");
		pluginServerConfigPanel.setImmediate(true);
		pluginServerConfigPanel
		.setDescription("External server listing plugins");
		pluginServerConfigPanel.setWidth("-1px");
		pluginServerConfigPanel.setHeight("-1px");
		pluginServerConfigPanel.setMargin(true);

		// pluginServerUrlTxtField
		pluginServerUrlTxtField = new TextField();
		pluginServerUrlTxtField.setCaption("Plugin Server URL");
		pluginServerUrlTxtField.setImmediate(true);
		pluginServerUrlTxtField.setWidth("-1px");
		pluginServerUrlTxtField.setHeight("-1px");
		pluginServerConfigPanel.addComponent(pluginServerUrlTxtField);

		// pluginServerUsernameTxtField
		pluginServerUsernameTxtField = new TextField();
		pluginServerUsernameTxtField.setCaption("Plugin Server Username");
		pluginServerUsernameTxtField.setImmediate(true);
		pluginServerUsernameTxtField.setWidth("-1px");
		pluginServerUsernameTxtField.setHeight("-1px");
		pluginServerConfigPanel.addComponent(pluginServerUsernameTxtField);

		// pluginServerPasswordTxtField
		pluginServerPasswordTxtField = new TextField();
		pluginServerPasswordTxtField.setCaption("Plugin Server Password");
		pluginServerPasswordTxtField.setImmediate(true);
		pluginServerPasswordTxtField.setWidth("-1px");
		pluginServerPasswordTxtField.setHeight("-1px");
		pluginServerConfigPanel.addComponent(pluginServerPasswordTxtField);

		// licenceShoppingCartUrlTxtField
		licenceShoppingCartUrlTxtField = new TextField();
		licenceShoppingCartUrlTxtField.setCaption("Licence Shopping Cart URL");
		licenceShoppingCartUrlTxtField.setImmediate(true);
		licenceShoppingCartUrlTxtField.setWidth("-1px");
		licenceShoppingCartUrlTxtField.setHeight("-1px");
		pluginServerConfigPanel.addComponent(licenceShoppingCartUrlTxtField);

		return pluginServerConfigPanel;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_2() {
		// common part: create layout
		verticalLayout_2 = new VerticalLayout();
		verticalLayout_2.setImmediate(true);
		verticalLayout_2.setWidth("-1px");
		verticalLayout_2.setHeight("-1px");
		verticalLayout_2.setMargin(false);

		// updatePluginServerButton
		updatePluginServerButton = new Button();
		updatePluginServerButton.setCaption("Update Plugin Server");
		updatePluginServerButton.setImmediate(true);
		updatePluginServerButton.setWidth("-1px");
		updatePluginServerButton.setHeight("-1px");
		verticalLayout_2.addComponent(updatePluginServerButton);

		// openShoppingCartLink
		openShoppingCartLink = new Link();
		openShoppingCartLink.setCaption("Open Shopping Cart");
		openShoppingCartLink.setImmediate(true);
		openShoppingCartLink.setWidth("-1px");
		openShoppingCartLink.setHeight("-1px");
		verticalLayout_2.addComponent(openShoppingCartLink);

		return verticalLayout_2;
	}

	@AutoGenerated
	private VerticalLayout buildSystemIdPanel() {
		// common part: create layout
		systemIdPanel = new VerticalLayout();
		systemIdPanel.setCaption("System Id Settings");
		systemIdPanel.setImmediate(true);
		systemIdPanel.setDescription("System Id Settings");
		systemIdPanel.setWidth("-1px");
		systemIdPanel.setHeight("-1px");
		systemIdPanel.setMargin(true);

		// systemIdTxtField
		systemIdTxtField = new TextField();
		systemIdTxtField.setCaption("System Id");
		systemIdTxtField.setImmediate(true);
		systemIdTxtField.setWidth("-1px");
		systemIdTxtField.setHeight("-1px");
		systemIdPanel.addComponent(systemIdTxtField);

		// verticalLayout_3
		verticalLayout_3 = buildVerticalLayout_3();
		systemIdPanel.addComponent(verticalLayout_3);

		return systemIdPanel;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_3() {
		// common part: create layout
		verticalLayout_3 = new VerticalLayout();
		verticalLayout_3.setImmediate(true);
		verticalLayout_3.setWidth("-1px");
		verticalLayout_3.setHeight("-1px");
		verticalLayout_3.setMargin(true);

		// updateSystemIdButton
		updateSystemIdButton = new Button();
		updateSystemIdButton.setCaption("Update System Id");
		updateSystemIdButton.setImmediate(true);
		updateSystemIdButton.setWidth("-1px");
		updateSystemIdButton.setHeight("-1px");
		verticalLayout_3.addComponent(updateSystemIdButton);

		// generateRandomSystemIdButton
		generateRandomSystemIdButton = new Button();
		generateRandomSystemIdButton.setCaption("Generate Random System Id");
		generateRandomSystemIdButton.setImmediate(true);
		generateRandomSystemIdButton.setWidth("-1px");
		generateRandomSystemIdButton.setHeight("-1px");
		verticalLayout_3.addComponent(generateRandomSystemIdButton);

		return verticalLayout_3;
	}

	@AutoGenerated
	private VerticalLayout buildFeatureSettingsPanel() {
		// common part: create layout
		featureSettingsPanel = new VerticalLayout();
		featureSettingsPanel.setCaption("Plugin Settings");
		featureSettingsPanel.setImmediate(true);
		featureSettingsPanel.setWidth("-1px");
		featureSettingsPanel.setHeight("-1px");
		featureSettingsPanel.setMargin(true);

		// featuresTabSheet_1
		featuresTabSheet_1 = buildFeaturesTabSheet_1();
		featureSettingsPanel.addComponent(featuresTabSheet_1);

		// systemMessages
		systemMessages = new TextArea();
		systemMessages.setCaption("System Messages");
		systemMessages.setImmediate(true);
		systemMessages.setWidth("100.0%");
		systemMessages.setHeight("100.0%");
		featureSettingsPanel.addComponent(systemMessages);

		return featureSettingsPanel;
	}

	@AutoGenerated
	private TabSheet buildFeaturesTabSheet_1() {
		// common part: create layout
		featuresTabSheet_1 = new TabSheet();
		featuresTabSheet_1.setCaption("Plugin Settings");
		featuresTabSheet_1.setImmediate(true);
		featuresTabSheet_1.setWidth("-1px");
		featuresTabSheet_1.setHeight("-1px");

		// installedPluginsTab
		installedPluginsTab = buildInstalledPluginsTab();
		featuresTabSheet_1.addTab(installedPluginsTab, "Installed Plugins",
				null);

		// availablePluginsTab
		availablePluginsTab = buildAvailablePluginsTab();
		featuresTabSheet_1.addTab(availablePluginsTab, "Available Plugins",
				null);

		// installedLicencesTab
		installedLicencesTab = buildInstalledLicencesTab();
		featuresTabSheet_1.addTab(installedLicencesTab, "Installed Licences",
				null);

		// addLicenceTab
		addLicenceTab = buildAddLicenceTab();
		featuresTabSheet_1.addTab(addLicenceTab, "Add Licence", null);

		return featuresTabSheet_1;
	}

	@AutoGenerated
	private VerticalLayout buildInstalledPluginsTab() {
		// common part: create layout
		installedPluginsTab = new VerticalLayout();
		installedPluginsTab.setImmediate(true);
		installedPluginsTab.setWidth("-1px");
		installedPluginsTab.setHeight("-1px");
		installedPluginsTab.setMargin(true);

		// installedPluginsPanel
		installedPluginsPanel = new ProductDescriptorTablePanel();
		installedPluginsPanel.setImmediate(true);
		installedPluginsPanel.setWidth("100.0%");
		installedPluginsPanel.setHeight("100.0%");
		installedPluginsTab.addComponent(installedPluginsPanel);

		return installedPluginsTab;
	}

	@AutoGenerated
	private VerticalLayout buildAvailablePluginsTab() {
		// common part: create layout
		availablePluginsTab = new VerticalLayout();
		availablePluginsTab.setImmediate(true);
		availablePluginsTab.setWidth("-1px");
		availablePluginsTab.setHeight("-1px");
		availablePluginsTab.setMargin(true);

		// availablePluginsPanel
		availablePluginsPanel = new ProductDescriptorTablePanel();
		availablePluginsPanel.setImmediate(true);
		availablePluginsPanel.setWidth("100.0%");
		availablePluginsPanel.setHeight("100.0%");
		availablePluginsTab.addComponent(availablePluginsPanel);

		return availablePluginsTab;
	}

	@AutoGenerated
	private VerticalLayout buildInstalledLicencesTab() {
		// common part: create layout
		installedLicencesTab = new VerticalLayout();
		installedLicencesTab.setCaption("Installed Licences");
		installedLicencesTab.setImmediate(true);
		installedLicencesTab.setWidth("-1px");
		installedLicencesTab.setHeight("-1px");
		installedLicencesTab.setMargin(true);

		// licenceDescriptorTablePanel
		licenceDescriptorTablePanel = new LicenceDescriptorTablePanel();
		licenceDescriptorTablePanel.setImmediate(true);
		licenceDescriptorTablePanel.setWidth("100.0%");
		licenceDescriptorTablePanel.setHeight("100.0%");
		installedLicencesTab.addComponent(licenceDescriptorTablePanel);

		return installedLicencesTab;
	}

	@AutoGenerated
	private VerticalLayout buildAddLicenceTab() {
		// common part: create layout
		addLicenceTab = new VerticalLayout();
		addLicenceTab.setCaption("Installed Licences");
		addLicenceTab.setImmediate(true);
		addLicenceTab.setWidth("-1px");
		addLicenceTab.setHeight("-1px");
		addLicenceTab.setMargin(true);

		// addLicencePanel
		addLicencePanel = new AddLicencePanel();
		addLicencePanel.setImmediate(true);
		addLicencePanel.setWidth("100.0%");
		addLicencePanel.setHeight("100.0%");
		addLicenceTab.addComponent(addLicencePanel);

		return addLicenceTab;
	}

}
