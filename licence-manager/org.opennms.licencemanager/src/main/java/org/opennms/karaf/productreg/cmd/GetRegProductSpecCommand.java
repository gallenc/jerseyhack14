package org.opennms.karaf.productreg.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.productpub.ProductRegister;

@Command(scope = "product-reg", name = "getproductspec", description="gets product spec from  product registry for productId")
public class GetRegProductSpecCommand extends OsgiCommandSupport {

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
	
	@Argument(index = 0, name = "productId", description = "Product Id for which to get product spec", required = true, multiValued = false)
	String productId = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			if (productRegister==null) throw new RuntimeException("productPublisher cannot be null");
			ProductMetadata pmeta = productRegister.getProductDescription(productId);
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