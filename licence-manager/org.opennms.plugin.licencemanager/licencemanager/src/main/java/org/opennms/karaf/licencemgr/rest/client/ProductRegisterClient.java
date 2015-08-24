package org.opennms.karaf.licencemgr.rest.client;

import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;

public interface ProductRegisterClient {

	/**
	 * /addproductspec (POST productMetadata)
	 * 
	 * Post command to add a new product specification. 
	 * Adds a new product specification to the product publisher. 
	 * Looks for the productId in the product specification and adds 
	 * an entry in the licence table under that productId. 
	 * Replaces any previous licence entry

	 * @param productMetadata
	 * @throws Exception
	 */
	public void addProductSpec(ProductMetadata productMetadata) throws Exception;


	/**
	 * /removeproductspec (GET productId)
	 * 
	 * Checks the product publisher and removes any entry for productId
	 * e.g. http://localhost:8181/licencemgr/rest/product-pub/removeproductspec?productId=
	 * 
	 * @param productId
	 * @throws Exception
	 */
	public void removeProductSpec(String productId ) throws Exception;


	/**
	 * /getproductspec (GET productId)
	 * returns product description metadata for productId if found
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/product-pub/getproductspec?productId=
	 * 
	 * @param productId
	 * @return productMetadata contains description of product
	 * @throws Exception
	 */
	public ProductMetadata getProductSpec(String productId ) throws Exception;


	/**
	 * /list (GET )
	 * returns a map of product description entries with key=productId, value= ProductMetadata
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/product-pub/list
	 * 
	 * @return productSpecList 
	 * @throws Exception
	 */
	public ProductSpecList getList() throws Exception;


	/**
	 * /clearproductspecs (GET )
	 * 
	 * Deletes all product descriptions. 
	 * Will only delete descriptions if parameter confirm=true
	 * 
	 * e.g. http://localhost:8181/licencemgr/rest/product-pub/clearproductspecs?confirm=false
	 * 
	 * @param confirm
	 * @throws Exception
	 */
	public void clearProductSpecs(Boolean confirm ) throws Exception;

	
}
