package org.opennms.karaf.productreg.cmd;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.productpub.ProductRegister;

@Command(scope = "product-reg", name = "list", description="lists product specifications installed in product registry")
public class ListRegProductSpecsCommand extends OsgiCommandSupport {

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
	
	@Override
	protected Object doExecute() throws Exception {
		try {
			System.out.println("list of product specifications");

			Map<String, ProductMetadata> productSpecMap = getProductRegister().getProductDescriptionMap();
			for (Entry<String, ProductMetadata> entry : productSpecMap.entrySet()){
				
				ProductMetadata productSpecification = entry.getValue();
				
				System.out.println("  productId='"+entry.getKey()+"'\n"
						+ "      productMetadata='"+productSpecification.toXml()+"'\n");
				}
		} catch (Exception e) {
			System.out.println("Error getting list of installed licence specifications. Exception="+e);
		}
		return null;
	}


}