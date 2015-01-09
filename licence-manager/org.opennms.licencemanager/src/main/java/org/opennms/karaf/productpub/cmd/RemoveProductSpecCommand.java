package org.opennms.karaf.productpub.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.productpub.ProductPublisher;

@Command(scope = "product-mgr", name = "removeProduct", description="removes product description from product manager")
public class RemoveProductSpecCommand extends OsgiCommandSupport {

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

	@Argument(index = 0, name = "productId", description = "Product Id to remove product information", required = true, multiValued = false)
	String productId = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			if (getProductPublisher().removeProductDescription(productId)){
				System.out.println("Removed description for productId=" + productId);
			}else {
				System.out.println("No product description installed for productId='" + productId+"'");
			}
		} catch (Exception e) {
			System.out.println("error removing product description for productId. Exception="+e);
		}

		return null;
	}
}