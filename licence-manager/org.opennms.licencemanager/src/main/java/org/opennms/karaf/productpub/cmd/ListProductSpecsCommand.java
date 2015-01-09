package org.opennms.karaf.productpub.cmd;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.metadata.ProductMetadata;
import org.opennms.karaf.productpub.ProductPublisher;

@Command(scope = "product-mgr", name = "list", description="lists product specifications installed in product publisher")
public class ListProductSpecsCommand extends OsgiCommandSupport {

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

	@Override
	protected Object doExecute() throws Exception {
		try {
			System.out.println("list of product specifications");

			Map<String, ProductMetadata> productSpecMap = getProductPublisher().getProductDescriptionMap();
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