package org.opennms.karaf.productpub.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencemgr.metadata.Licence;
import org.opennms.karaf.licencemgr.metadata.ProductMetadata;
import org.opennms.karaf.productpub.ProductPublisher;

@Command(scope = "product-mgr", name = "getProductInfo", description="gets product info from product publisher for productId")
public class GetProductSpecCommand extends OsgiCommandSupport {

	private ProductPublisher productPublisher=null;

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

	@Argument(index = 0, name = "productId", description = "Product Id for which to get product spec", required = true, multiValued = false)
	String productId = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			if (productPublisher==null) throw new RuntimeException("productPublisher cannot be null");
			ProductMetadata pmeta = productPublisher.getProductDescription(productId);
			if (pmeta==null) {
				System.out.println("No product metadata installed for productId='" + productId+"'");
			} else {
				System.out.println("productId='" + productId+"' Product metadata XML='"+pmeta.toXml()+"'");
			}
		} catch (Exception e) {
			System.out.println("Error getting product info. Exception="+e);
		}
		return null;
	}
}