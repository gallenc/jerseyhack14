package org.opennms.karaf.productreg.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.productpub.ProductRegister;

@Command(scope = "product-reg", name = "addproductspec", description="adds product spec to product registry for productId")
public class AddRegProductSpecCommand extends OsgiCommandSupport {

	private ProductRegister productRegister;

	/**
	 * @return the productRegister
	 */
	public ProductRegister getProductRegister() {
		return productRegister;
	}

	/**
	 * @param productRegister the productRegister to set
	 */
	public void setProductRegister(ProductRegister productRegister) {
		this.productRegister = productRegister;
	}

	@Argument(index = 0, name = "productMetadata", description = "product metadata as xml (surround xml in 'quotes') ", required = true, multiValued = false)
	String productMetadataXml = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			ProductMetadata pmeta= new ProductMetadata();
			pmeta.fromXml(productMetadataXml);
			
			getProductRegister().addProductDescription(pmeta);
			System.out.println("Added Product Metadata for productId='"+pmeta.getProductId()+ "' productMetadata='" + pmeta.toXml()+"'");
		} catch (Exception e) {
			System.out.println("Error adding product spec for productId. Exception="+e);
		}
		return null;
	}


}