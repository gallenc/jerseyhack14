package org.opennms.karaf.productpub.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencemgr.metadata.Licence;
import org.opennms.karaf.licencemgr.metadata.ProductMetadata;
import org.opennms.karaf.productpub.ProductPublisher;

@Command(scope = "product-mgr", name = "add", description="adds product spec for productId")
public class AddProductSpecCommand extends OsgiCommandSupport {

	private ProductPublisher productPublisher;

	/**
	 * @return the productPublisher
	 */
	public ProductPublisher getProductPublisher() {
		return productPublisher;
	}

	/**
	 * @param productPublisher the productPublisher to set
	 */
	public void setProductPublisher(ProductPublisher productPublisher) {
		this.productPublisher = productPublisher;
	}

	@Argument(index = 0, name = "productMetadata", description = "product metadata as xml (surround xml in 'quotes') ", required = true, multiValued = false)
	String productMetadataXml = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			ProductMetadata pmeta= new ProductMetadata();
			pmeta.fromXml(productMetadataXml);
			
			getProductPublisher().addProductDescription(pmeta);
			System.out.println("Added Product Metadata for productId='"+pmeta.getProductId()+ "' productMetadata='" + pmeta.toXml()+"'");
		} catch (Exception e) {
			System.out.println("Error adding product spec for productId. Exception="+e);
		}
		return null;
	}


}