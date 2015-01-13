package org.opennms.karaf.productreg.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.productpub.ProductRegister;

@Command(scope = "product-reg", name = "removeproductspec", description="removes product specification from product register")
public class RemoveRegProductSpecCommand extends OsgiCommandSupport {

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
	
	@Argument(index = 0, name = "productId", description = "Product Id to remove product information", required = true, multiValued = false)
	String productId = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			if (getProductRegister().removeProductDescription(productId)){
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