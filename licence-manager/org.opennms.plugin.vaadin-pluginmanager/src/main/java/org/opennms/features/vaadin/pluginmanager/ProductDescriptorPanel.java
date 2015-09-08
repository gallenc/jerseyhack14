package org.opennms.features.vaadin.pluginmanager;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;

public class ProductDescriptorPanel extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,5","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	
	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private TextField organizationTextField;
	@AutoGenerated
	private TextField licenceTypeTextField;
	@AutoGenerated
	private TextField licenceRequiredTextField;
	@AutoGenerated
	private TextField featureRepositoryTextField;
	@AutoGenerated
	private TextField productUrlTextField;
	@AutoGenerated
	private TextArea productDescriptionTextArea;
	@AutoGenerated
	private TextField productVersionTextField;
	@AutoGenerated
	private TextField productIdTextField;
	@AutoGenerated
	private TextField productNameTextField;

	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */

	public ProductDescriptorPanel() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here

	}
	
	public synchronized void setProductMetadata(ProductMetadata productMetadata){
		productIdTextField.setValue( (productMetadata.getProductId()==null )?  "":productMetadata.getProductId());
		productNameTextField.setValue( (productMetadata.getProductName()==null )?  "":productMetadata.getProductName());
		
		//TODO productVersionTextField .setValue( (productMetadata.getProductUrl()==null ) ? "":productMetadata.get)
		
		featureRepositoryTextField.setValue(( productMetadata.getFeatureRepository()== null ) ? "": productMetadata.getFeatureRepository());
	
		licenceRequiredTextField.setValue( (productMetadata.getLicenceKeyRequired()==null ) ? "":productMetadata.getLicenceKeyRequired().toString());
		licenceTypeTextField.setValue( (productMetadata.getLicenceType()==null )?  "":productMetadata.getLicenceType());
		organizationTextField.setValue( (productMetadata.getOrganization()==null ) ? "":productMetadata.getOrganization());
		productDescriptionTextArea.setValue( (productMetadata.getProductDescription()==null ) ? "":productMetadata.getProductDescription());
		productUrlTextField.setValue( (productMetadata.getProductUrl()==null ) ? "":productMetadata.getProductUrl());
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(true);
		mainLayout.setWidth("-1px");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(true);
		
		// top-level component properties
		setWidth("-1px");
		setHeight("-1px");
		
		// productNameTextField
		productNameTextField = new TextField();
		productNameTextField.setCaption("Product Name");
		productNameTextField.setImmediate(true);
		productNameTextField.setWidth("-1px");
		productNameTextField.setHeight("-1px");
		mainLayout.addComponent(productNameTextField);
		
		// productIdTextField
		productIdTextField = new TextField();
		productIdTextField.setCaption("Product Id");
		productIdTextField.setImmediate(true);
		productIdTextField.setWidth("-1px");
		productIdTextField.setHeight("-1px");
		mainLayout.addComponent(productIdTextField);
		
		// productVersionTextField
		productVersionTextField = new TextField();
		productVersionTextField.setCaption("Product Version");
		productVersionTextField.setImmediate(true);
		productVersionTextField.setWidth("-1px");
		productVersionTextField.setHeight("-1px");
		mainLayout.addComponent(productVersionTextField);
		
		// productDescriptionTextArea
		productDescriptionTextArea = new TextArea();
		productDescriptionTextArea.setCaption("Product Description");
		productDescriptionTextArea.setImmediate(true);
		productDescriptionTextArea.setWidth("-1px");
		productDescriptionTextArea.setHeight("-1px");
		mainLayout.addComponent(productDescriptionTextArea);
		
		// productUrlTextField
		productUrlTextField = new TextField();
		productUrlTextField.setCaption("Product URL");
		productUrlTextField.setImmediate(true);
		productUrlTextField.setWidth("-1px");
		productUrlTextField.setHeight("-1px");
		mainLayout.addComponent(productUrlTextField);
		
		// featureRepositoryTextField
		featureRepositoryTextField = new TextField();
		featureRepositoryTextField.setCaption("Feature Repository URL");
		featureRepositoryTextField.setImmediate(true);
		featureRepositoryTextField.setWidth("-1px");
		featureRepositoryTextField.setHeight("-1px");
		mainLayout.addComponent(featureRepositoryTextField);
		
		// licenceRequiredTextField
		licenceRequiredTextField = new TextField();
		licenceRequiredTextField.setCaption("Licence Required");
		licenceRequiredTextField.setImmediate(true);
		licenceRequiredTextField.setWidth("-1px");
		licenceRequiredTextField.setHeight("-1px");
		mainLayout.addComponent(licenceRequiredTextField);
		
		// licenceTypeTextField
		licenceTypeTextField = new TextField();
		licenceTypeTextField.setCaption("Licence Type");
		licenceTypeTextField.setImmediate(true);
		licenceTypeTextField.setWidth("-1px");
		licenceTypeTextField.setHeight("-1px");
		licenceTypeTextField.setNullSettingAllowed(true);
		mainLayout.addComponent(licenceTypeTextField);
		
		// organizationTextField
		organizationTextField = new TextField();
		organizationTextField.setCaption("Organization");
		organizationTextField.setImmediate(true);
		organizationTextField.setWidth("-1px");
		organizationTextField.setHeight("-1px");
		organizationTextField.setNullSettingAllowed(true);
		mainLayout.addComponent(organizationTextField);
		
		return mainLayout;
	}


}
