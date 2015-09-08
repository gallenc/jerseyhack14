package org.opennms.features.vaadin.pluginmanager;

import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TabSheet;
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
	private VerticalLayout installedLicencesTab;

	@AutoGenerated
	private HorizontalLayout horizontalLayout_7;

	@AutoGenerated
	private VerticalLayout verticalLayout_4;

	@AutoGenerated
	private VerticalLayout verticalLayout_13;

	@AutoGenerated
	private HorizontalLayout horizontalLayout_10;

	@AutoGenerated
	private Button installedLicencesTabInstallLicenceButton;

	@AutoGenerated
	private Button installedLicencesTabUninstallLicenceButton;

	@AutoGenerated
	private VerticalLayout installedLicencesTabLicenceDetailPanel;

	@AutoGenerated
	private TextArea textArea_6;

	@AutoGenerated
	private VerticalLayout verticalLayout_3;

	@AutoGenerated
	private ListSelect installedLicencesListSelect_1;

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
	private VerticalLayout rawPluginImportPanel;

	@AutoGenerated
	private HorizontalLayout horizontalLayout_6;

	@AutoGenerated
	private Button unInstallRawPluginButton;

	@AutoGenerated
	private Button installRawPluginButton;

	@AutoGenerated
	private TextField rawMavenPluginIdTxtField;

	@AutoGenerated
	private VerticalLayout featureServerPanel;

	@AutoGenerated
	private HorizontalLayout horizontalLayout_2;

	@AutoGenerated
	private Button updatePluginServerButton;

	@AutoGenerated
	private TextField featureServerPasswordTxtField;

	@AutoGenerated
	private TextField featureServerUsernameTxtField;

	@AutoGenerated
	private TextField featureServerUrlTxtField;

	@AutoGenerated
	private VerticalLayout licenceServerConfigPanel;

	@AutoGenerated
	private Button updateLicenceServerButton;

	@AutoGenerated
	private TextField licenceServerPasswordTxtField;

	@AutoGenerated
	private TextField licenceServerUsernameTxtField;

	@AutoGenerated
	private TextField licenceServerUrlTxtField;

	@AutoGenerated
	private VerticalLayout systemIdPanel;

	@AutoGenerated
	private HorizontalLayout horizontalLayout_3;

	@AutoGenerated
	private Button generateRandomSystemIdButton;

	@AutoGenerated
	private Button updateSystemIdButton;

	@AutoGenerated
	private TextField systemIdTxtField;

	private static final long serialVersionUID = 1L;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	@SuppressWarnings("deprecation")
	public PluginManagerUIMainPanelImpl2() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here

		// added simple no effect methods to show which buttons / fields are available
		
		// General Values Panel
		
		// SystemID Panel
		generateRandomSystemIdButton.getClass();
		updateSystemIdButton.getClass();
		systemIdTxtField.setValue("System id text field");
		
		//feature server panel
		featureServerPasswordTxtField.setValue("Plugin server password");
		featureServerUsernameTxtField.setValue("Plugin server username");
		featureServerUrlTxtField.setValue("Server URL text field");
		updatePluginServerButton.getClass();
		
		// licence Server Panel
		licenceServerPasswordTxtField.setValue("Licence server password");
		licenceServerUsernameTxtField.setValue("Licence server username");
		licenceServerUrlTxtField.setValue("Licence server URL");
		updateLicenceServerButton.getClass();

		// Raw Plugin Panel
		unInstallRawPluginButton.getClass();
		installRawPluginButton.getClass();
		rawMavenPluginIdTxtField.setValue("Raw Maven command text field");
		
		// test code generating a spec list
		ProductSpecList productSpecList = new ProductSpecList();
		
		String[] productIds = {"Mercury","Venus","Earth","Mars","Jupiter","Saturn","Neptune","Uranus"};
		
		for (String pid: productIds){
			ProductMetadata pmeta= new ProductMetadata();
			pmeta.setProductId(pid);
			pmeta.setOrganization("OpenNMS Project");
			pmeta.setProductDescription("Test product description");
			pmeta.setFeatureRepository("mvn:org.opennms.licencemgr/licence.manager.example/2.10.0/xml/features");
			pmeta.setProductName("test Bundle");
			pmeta.setProductUrl("http:\\\\opennms.co.uk");
			pmeta.setLicenceKeyRequired(true);
			pmeta.setLicenceType("OpenNMS EULA See http:\\\\opennms.co.uk\\EULA");
			productSpecList.getProductSpecList().add(pmeta);
		}
		
		// Plugins Detail tabs panel

		// Available Plugins Tab
		availablePluginsPanel.addProductList(productSpecList);

		// Installed Plugins Tab
		installedPluginsPanel.addProductList(productSpecList);

		// Installed Licences Tab
		installedLicencesTabLicenceDetailPanel.getClass();
		installedLicencesTabInstallLicenceButton.getClass();
		installedLicencesTabUninstallLicenceButton.getClass();

	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
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
		pluginMgrHorSplitPanel_1.setImmediate(false);
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
		generalSettingsPanel.setImmediate(false);
		generalSettingsPanel.setWidth("-1px");
		generalSettingsPanel.setHeight("-1px");
		generalSettingsPanel.setMargin(true);
		
		// systemIdPanel
		systemIdPanel = buildSystemIdPanel();
		generalSettingsPanel.addComponent(systemIdPanel);
		
		// licenceServerConfigPanel
		licenceServerConfigPanel = buildLicenceServerConfigPanel();
		generalSettingsPanel.addComponent(licenceServerConfigPanel);
		
		// featureServerPanel
		featureServerPanel = buildFeatureServerPanel();
		generalSettingsPanel.addComponent(featureServerPanel);
		generalSettingsPanel.setExpandRatio(featureServerPanel, 1.0f);
		
		// rawPluginImportPanel
		rawPluginImportPanel = buildRawPluginImportPanel();
		generalSettingsPanel.addComponent(rawPluginImportPanel);
		
		return generalSettingsPanel;
	}

	@AutoGenerated
	private VerticalLayout buildSystemIdPanel() {
		// common part: create layout
		systemIdPanel = new VerticalLayout();
		systemIdPanel.setCaption("System Id Settings");
		systemIdPanel.setImmediate(false);
		systemIdPanel.setDescription("System Id Settings");
		systemIdPanel.setWidth("-1px");
		systemIdPanel.setHeight("-1px");
		systemIdPanel.setMargin(true);
		
		// systemIdTxtField
		systemIdTxtField = new TextField();
		systemIdTxtField.setCaption("System Id");
		systemIdTxtField.setImmediate(false);
		systemIdTxtField.setWidth("-1px");
		systemIdTxtField.setHeight("-1px");
		systemIdPanel.addComponent(systemIdTxtField);
		
		// horizontalLayout_3
		horizontalLayout_3 = buildHorizontalLayout_3();
		systemIdPanel.addComponent(horizontalLayout_3);
		
		return systemIdPanel;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_3() {
		// common part: create layout
		horizontalLayout_3 = new HorizontalLayout();
		horizontalLayout_3.setImmediate(false);
		horizontalLayout_3.setWidth("-1px");
		horizontalLayout_3.setHeight("-1px");
		horizontalLayout_3.setMargin(false);
		
		// updateSystemIdButton
		updateSystemIdButton = new Button();
		updateSystemIdButton.setCaption("Update System Id");
		updateSystemIdButton.setImmediate(true);
		updateSystemIdButton.setWidth("-1px");
		updateSystemIdButton.setHeight("-1px");
		horizontalLayout_3.addComponent(updateSystemIdButton);
		
		// generateRandomSystemIdButton
		generateRandomSystemIdButton = new Button();
		generateRandomSystemIdButton.setCaption("Generate Random System Id");
		generateRandomSystemIdButton.setImmediate(true);
		generateRandomSystemIdButton.setWidth("-1px");
		generateRandomSystemIdButton.setHeight("-1px");
		horizontalLayout_3.addComponent(generateRandomSystemIdButton);
		
		return horizontalLayout_3;
	}

	@AutoGenerated
	private VerticalLayout buildLicenceServerConfigPanel() {
		// common part: create layout
		licenceServerConfigPanel = new VerticalLayout();
		licenceServerConfigPanel.setCaption("Licence Server");
		licenceServerConfigPanel.setImmediate(false);
		licenceServerConfigPanel.setWidth("-1px");
		licenceServerConfigPanel.setHeight("-1px");
		licenceServerConfigPanel.setMargin(true);
		
		// licenceServerUrlTxtField
		licenceServerUrlTxtField = new TextField();
		licenceServerUrlTxtField.setCaption("Licence Server URL");
		licenceServerUrlTxtField.setImmediate(false);
		licenceServerUrlTxtField.setWidth("-1px");
		licenceServerUrlTxtField.setHeight("-1px");
		licenceServerConfigPanel.addComponent(licenceServerUrlTxtField);
		
		// licenceServerUsernameTxtField
		licenceServerUsernameTxtField = new TextField();
		licenceServerUsernameTxtField.setCaption("Licence Server Username");
		licenceServerUsernameTxtField.setImmediate(false);
		licenceServerUsernameTxtField.setWidth("-1px");
		licenceServerUsernameTxtField.setHeight("-1px");
		licenceServerConfigPanel.addComponent(licenceServerUsernameTxtField);
		
		// licenceServerPasswordTxtField
		licenceServerPasswordTxtField = new TextField();
		licenceServerPasswordTxtField.setCaption("Licence Server Password");
		licenceServerPasswordTxtField.setImmediate(false);
		licenceServerPasswordTxtField.setWidth("-1px");
		licenceServerPasswordTxtField.setHeight("-1px");
		licenceServerConfigPanel.addComponent(licenceServerPasswordTxtField);
		
		// updateLicenceServerButton
		updateLicenceServerButton = new Button();
		updateLicenceServerButton.setCaption("Update Licence Server");
		updateLicenceServerButton.setImmediate(true);
		updateLicenceServerButton.setWidth("-1px");
		updateLicenceServerButton.setHeight("-1px");
		licenceServerConfigPanel.addComponent(updateLicenceServerButton);
		
		return licenceServerConfigPanel;
	}

	@AutoGenerated
	private VerticalLayout buildFeatureServerPanel() {
		// common part: create layout
		featureServerPanel = new VerticalLayout();
		featureServerPanel.setCaption("Maven Plugin Server");
		featureServerPanel.setImmediate(false);
		featureServerPanel.setWidth("-1px");
		featureServerPanel.setHeight("-1px");
		featureServerPanel.setMargin(true);
		
		// featureServerUrlTxtField
		featureServerUrlTxtField = new TextField();
		featureServerUrlTxtField.setCaption("Plugin Server URL");
		featureServerUrlTxtField.setImmediate(false);
		featureServerUrlTxtField.setWidth("-1px");
		featureServerUrlTxtField.setHeight("-1px");
		featureServerPanel.addComponent(featureServerUrlTxtField);
		
		// featureServerUsernameTxtField
		featureServerUsernameTxtField = new TextField();
		featureServerUsernameTxtField.setCaption("Plugin Server Username");
		featureServerUsernameTxtField.setImmediate(false);
		featureServerUsernameTxtField.setWidth("-1px");
		featureServerUsernameTxtField.setHeight("-1px");
		featureServerPanel.addComponent(featureServerUsernameTxtField);
		featureServerPanel.setExpandRatio(featureServerUsernameTxtField, 1.0f);
		
		// featureServerPasswordTxtField
		featureServerPasswordTxtField = new TextField();
		featureServerPasswordTxtField.setCaption("Plugin Server Password");
		featureServerPasswordTxtField.setImmediate(false);
		featureServerPasswordTxtField.setWidth("-1px");
		featureServerPasswordTxtField.setHeight("-1px");
		featureServerPanel.addComponent(featureServerPasswordTxtField);
		
		// horizontalLayout_2
		horizontalLayout_2 = buildHorizontalLayout_2();
		featureServerPanel.addComponent(horizontalLayout_2);
		
		return featureServerPanel;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_2() {
		// common part: create layout
		horizontalLayout_2 = new HorizontalLayout();
		horizontalLayout_2.setImmediate(false);
		horizontalLayout_2.setWidth("-1px");
		horizontalLayout_2.setHeight("-1px");
		horizontalLayout_2.setMargin(false);
		
		// updatePluginServerButton
		updatePluginServerButton = new Button();
		updatePluginServerButton.setCaption("Update Plugin Server");
		updatePluginServerButton.setImmediate(true);
		updatePluginServerButton.setWidth("-1px");
		updatePluginServerButton.setHeight("-1px");
		horizontalLayout_2.addComponent(updatePluginServerButton);
		
		return horizontalLayout_2;
	}

	@AutoGenerated
	private VerticalLayout buildRawPluginImportPanel() {
		// common part: create layout
		rawPluginImportPanel = new VerticalLayout();
		rawPluginImportPanel.setCaption("Raw Plugin Import");
		rawPluginImportPanel.setImmediate(false);
		rawPluginImportPanel.setWidth("-1px");
		rawPluginImportPanel.setHeight("-1px");
		rawPluginImportPanel.setMargin(true);
		
		// rawMavenPluginIdTxtField
		rawMavenPluginIdTxtField = new TextField();
		rawMavenPluginIdTxtField.setCaption("Plugin Maven Id");
		rawMavenPluginIdTxtField.setImmediate(false);
		rawMavenPluginIdTxtField.setWidth("-1px");
		rawMavenPluginIdTxtField.setHeight("-1px");
		rawPluginImportPanel.addComponent(rawMavenPluginIdTxtField);
		
		// horizontalLayout_6
		horizontalLayout_6 = buildHorizontalLayout_6();
		rawPluginImportPanel.addComponent(horizontalLayout_6);
		
		return rawPluginImportPanel;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_6() {
		// common part: create layout
		horizontalLayout_6 = new HorizontalLayout();
		horizontalLayout_6.setImmediate(false);
		horizontalLayout_6.setWidth("-1px");
		horizontalLayout_6.setHeight("-1px");
		horizontalLayout_6.setMargin(false);
		
		// installRawPluginButton
		installRawPluginButton = new Button();
		installRawPluginButton.setCaption("Install Raw Plugin");
		installRawPluginButton.setImmediate(true);
		installRawPluginButton.setWidth("-1px");
		installRawPluginButton.setHeight("-1px");
		horizontalLayout_6.addComponent(installRawPluginButton);
		
		// unInstallRawPluginButton
		unInstallRawPluginButton = new Button();
		unInstallRawPluginButton.setCaption("Uninstall Raw Plugin");
		unInstallRawPluginButton.setImmediate(true);
		unInstallRawPluginButton.setWidth("-1px");
		unInstallRawPluginButton.setHeight("-1px");
		horizontalLayout_6.addComponent(unInstallRawPluginButton);
		
		return horizontalLayout_6;
	}

	@AutoGenerated
	private VerticalLayout buildFeatureSettingsPanel() {
		// common part: create layout
		featureSettingsPanel = new VerticalLayout();
		featureSettingsPanel.setCaption("Plugin Settings");
		featureSettingsPanel.setImmediate(false);
		featureSettingsPanel.setWidth("-1px");
		featureSettingsPanel.setHeight("-1px");
		featureSettingsPanel.setMargin(true);
		
		// featuresTabSheet_1
		featuresTabSheet_1 = buildFeaturesTabSheet_1();
		featureSettingsPanel.addComponent(featuresTabSheet_1);
		
		// systemMessages
		systemMessages = new TextArea();
		systemMessages.setCaption("System Messages");
		systemMessages.setImmediate(false);
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
		
		return featuresTabSheet_1;
	}

	@AutoGenerated
	private VerticalLayout buildInstalledPluginsTab() {
		// common part: create layout
		installedPluginsTab = new VerticalLayout();
		installedPluginsTab.setImmediate(false);
		installedPluginsTab.setWidth("-1px");
		installedPluginsTab.setHeight("-1px");
		installedPluginsTab.setMargin(true);
		
		// installedPluginsPanel
		installedPluginsPanel = new ProductDescriptorTablePanel();
		installedPluginsPanel.setImmediate(false);
		installedPluginsPanel.setWidth("100.0%");
		installedPluginsPanel.setHeight("100.0%");
		installedPluginsTab.addComponent(installedPluginsPanel);
		
		return installedPluginsTab;
	}

	@AutoGenerated
	private VerticalLayout buildAvailablePluginsTab() {
		// common part: create layout
		availablePluginsTab = new VerticalLayout();
		availablePluginsTab.setImmediate(false);
		availablePluginsTab.setWidth("-1px");
		availablePluginsTab.setHeight("-1px");
		availablePluginsTab.setMargin(true);
		
		// availablePluginsPanel
		availablePluginsPanel = new ProductDescriptorTablePanel();
		availablePluginsPanel.setImmediate(false);
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
		installedLicencesTab.setImmediate(false);
		installedLicencesTab.setWidth("-1px");
		installedLicencesTab.setHeight("-1px");
		installedLicencesTab.setMargin(true);
		
		// horizontalLayout_7
		horizontalLayout_7 = buildHorizontalLayout_7();
		installedLicencesTab.addComponent(horizontalLayout_7);
		
		return installedLicencesTab;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_7() {
		// common part: create layout
		horizontalLayout_7 = new HorizontalLayout();
		horizontalLayout_7.setImmediate(false);
		horizontalLayout_7.setWidth("-1px");
		horizontalLayout_7.setHeight("-1px");
		horizontalLayout_7.setMargin(false);
		
		// verticalLayout_3
		verticalLayout_3 = buildVerticalLayout_3();
		horizontalLayout_7.addComponent(verticalLayout_3);
		
		// verticalLayout_4
		verticalLayout_4 = buildVerticalLayout_4();
		horizontalLayout_7.addComponent(verticalLayout_4);
		
		return horizontalLayout_7;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_3() {
		// common part: create layout
		verticalLayout_3 = new VerticalLayout();
		verticalLayout_3.setImmediate(false);
		verticalLayout_3.setWidth("-1px");
		verticalLayout_3.setHeight("-1px");
		verticalLayout_3.setMargin(false);
		
		// installedLicencesListSelect_1
		installedLicencesListSelect_1 = new ListSelect();
		installedLicencesListSelect_1.setCaption("Installed Licences");
		installedLicencesListSelect_1.setImmediate(false);
		installedLicencesListSelect_1.setWidth("-1px");
		installedLicencesListSelect_1.setHeight("-1px");
		verticalLayout_3.addComponent(installedLicencesListSelect_1);
		
		return verticalLayout_3;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_4() {
		// common part: create layout
		verticalLayout_4 = new VerticalLayout();
		verticalLayout_4.setImmediate(false);
		verticalLayout_4.setWidth("-1px");
		verticalLayout_4.setHeight("-1px");
		verticalLayout_4.setMargin(true);
		
		// verticalLayout_13
		verticalLayout_13 = buildVerticalLayout_13();
		verticalLayout_4.addComponent(verticalLayout_13);
		
		return verticalLayout_4;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_13() {
		// common part: create layout
		verticalLayout_13 = new VerticalLayout();
		verticalLayout_13.setImmediate(false);
		verticalLayout_13.setWidth("-1px");
		verticalLayout_13.setHeight("-1px");
		verticalLayout_13.setMargin(false);
		
		// installedLicencesTabLicenceDetailPanel
		installedLicencesTabLicenceDetailPanel = buildInstalledLicencesTabLicenceDetailPanel();
		verticalLayout_13.addComponent(installedLicencesTabLicenceDetailPanel);
		
		// horizontalLayout_10
		horizontalLayout_10 = buildHorizontalLayout_10();
		verticalLayout_13.addComponent(horizontalLayout_10);
		
		return verticalLayout_13;
	}

	@AutoGenerated
	private VerticalLayout buildInstalledLicencesTabLicenceDetailPanel() {
		// common part: create layout
		installedLicencesTabLicenceDetailPanel = new VerticalLayout();
		installedLicencesTabLicenceDetailPanel.setCaption("Licence Detail");
		installedLicencesTabLicenceDetailPanel.setImmediate(false);
		installedLicencesTabLicenceDetailPanel.setWidth("-1px");
		installedLicencesTabLicenceDetailPanel.setHeight("-1px");
		installedLicencesTabLicenceDetailPanel.setMargin(true);
		
		// textArea_6
		textArea_6 = new TextArea();
		textArea_6.setCaption("Licence");
		textArea_6.setImmediate(false);
		textArea_6.setWidth("-1px");
		textArea_6.setHeight("-1px");
		installedLicencesTabLicenceDetailPanel.addComponent(textArea_6);
		
		return installedLicencesTabLicenceDetailPanel;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_10() {
		// common part: create layout
		horizontalLayout_10 = new HorizontalLayout();
		horizontalLayout_10.setCaption("Controls");
		horizontalLayout_10.setImmediate(false);
		horizontalLayout_10.setWidth("-1px");
		horizontalLayout_10.setHeight("-1px");
		horizontalLayout_10.setMargin(true);
		
		// installedLicencesTabUninstallLicenceButton
		installedLicencesTabUninstallLicenceButton = new Button();
		installedLicencesTabUninstallLicenceButton
				.setCaption("Uninstall Licence");
		installedLicencesTabUninstallLicenceButton.setImmediate(true);
		installedLicencesTabUninstallLicenceButton
				.setDescription("Uninstalls the selected feature");
		installedLicencesTabUninstallLicenceButton.setWidth("-1px");
		installedLicencesTabUninstallLicenceButton.setHeight("-1px");
		horizontalLayout_10
				.addComponent(installedLicencesTabUninstallLicenceButton);
		
		// installedLicencesTabInstallLicenceButton
		installedLicencesTabInstallLicenceButton = new Button();
		installedLicencesTabInstallLicenceButton.setCaption("Install Licence");
		installedLicencesTabInstallLicenceButton.setImmediate(true);
		installedLicencesTabInstallLicenceButton
				.setDescription("Uninstalls the selected feature");
		installedLicencesTabInstallLicenceButton.setWidth("-1px");
		installedLicencesTabInstallLicenceButton.setHeight("-1px");
		horizontalLayout_10
				.addComponent(installedLicencesTabInstallLicenceButton);
		
		return horizontalLayout_10;
	}

}
