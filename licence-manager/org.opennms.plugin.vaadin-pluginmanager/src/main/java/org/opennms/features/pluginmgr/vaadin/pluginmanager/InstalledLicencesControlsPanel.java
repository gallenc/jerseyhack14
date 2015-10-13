package org.opennms.features.pluginmgr.vaadin.pluginmanager;

import org.opennms.features.pluginmgr.SessionPluginManager;
import org.opennms.features.pluginmgr.vaadin.config.SimpleStackTrace;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class InstalledLicencesControlsPanel extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;

	@AutoGenerated
	private Button uninstallLicenceButton;

	private static final long serialVersionUID = 1L;

	private SessionPluginManager sessionPluginManager=null;

	private LicenceDescriptorTablePanel licenceDescriptorTablePanel=null;

	private TextArea systemMessages;

	private boolean remoteUpdateControlsEnabled=true;


	public void setRemoteUpdateControlsEnabled(boolean remoteUpdateControlsEnabled) {
		this.remoteUpdateControlsEnabled = remoteUpdateControlsEnabled;
		// set state of update control buttons to remoteUpdateControlsEnabled 
		uninstallLicenceButton.setEnabled(remoteUpdateControlsEnabled);
	}


	public void setSessionPluginManager(SessionPluginManager sessionPluginManager) {
		this.sessionPluginManager = sessionPluginManager;
	}


	public void setLicenceDescriptorTablePanel(	LicenceDescriptorTablePanel licenceDescriptorTablePanel) {
		this.licenceDescriptorTablePanel = licenceDescriptorTablePanel;
	}

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public InstalledLicencesControlsPanel() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		// Handle the installPluginButton events with an anonymous class
		uninstallLicenceButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				systemMessages.setValue("");
				try{
					String selectedLicenceId = licenceDescriptorTablePanel.getSelectedLicenceId();
					sessionPluginManager.removeLicence(selectedLicenceId);
					systemMessages.setValue("uninstalled product Id "+selectedLicenceId);
					LicenceList licencelist = sessionPluginManager.getInstalledLicenceList();
					if (licencelist!=null) licenceDescriptorTablePanel.addLicenceList(licencelist);
				} catch (Exception e){
					systemMessages.setValue(SimpleStackTrace.errorToString(e));
				}
				licenceDescriptorTablePanel.markAsDirty();
			}
		});
	}

	public void setSystemMessages(TextArea systemMessages) {
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
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// uninstallLicenceButton
		uninstallLicenceButton = new Button();
		uninstallLicenceButton.setCaption("Uninstall Selected Licence");
		uninstallLicenceButton.setImmediate(true);
		uninstallLicenceButton.setWidth("-1px");
		uninstallLicenceButton.setHeight("-1px");
		mainLayout.addComponent(uninstallLicenceButton);
		
		return mainLayout;
	}
}
