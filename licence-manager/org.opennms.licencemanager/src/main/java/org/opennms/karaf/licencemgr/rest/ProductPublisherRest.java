package org.opennms.karaf.licencemgr.rest;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.opennms.karaf.licencemgr.metadata.jaxb.ErrorMessage;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecMap;
import org.opennms.karaf.licencemgr.metadata.jaxb.ReplyMessage;
import org.opennms.karaf.productpub.ProductPublisher;

@Path("/product-pub")
public class ProductPublisherRest {

	@POST
	@Path("/addproductspec")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response addProductDescription(ProductMetadata productMetadata){

		ProductPublisher productPublisher= ServiceLoader.getProductPublisher();
		if (productPublisher == null) throw new RuntimeException("ServiceLoader.getProductPublisher cannot be null.");

		try{
			if (productMetadata == null) throw new RuntimeException("productMetadata cannot be null.");
			productPublisher.addProductDescription(productMetadata);
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to add product description", null, e.getMessage())).build();
		}

		ReplyMessage reply= new ReplyMessage();
        reply.setReplyComment("Product Description added for productId="+productMetadata.getProductId());
        reply.setProductId(productMetadata.getProductId());
		
		return Response
				.status(200).entity(reply).build();

	}



	@GET
	@Path("/removeproductspec")
	@Produces(MediaType.APPLICATION_XML)
	public Response removeProductDescription(@QueryParam("productId") String productId){

		ProductPublisher productPublisher= ServiceLoader.getProductPublisher();
		if (productPublisher == null) throw new RuntimeException("ServiceLoader.getProductPublisher cannot be null.");

		Boolean removed=null;
		try{
			if (productId == null) throw new RuntimeException("productId cannot be null.");
			removed = productPublisher.removeProductDescription(productId);
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to remove product description", null, e.getMessage())).build();
		}

		ReplyMessage reply= new ReplyMessage();
		if (removed) {
			reply.setReplyComment("Product Description removed for productId="+productId);
		} else {
			reply.setReplyComment("Product Description not found to remove for productId="+productId);
		}
		
		return Response
				.status(200).entity(reply).build();

	}


	@GET
	@Path("/getproductspec")
	@Produces(MediaType.APPLICATION_XML)
	public Response getProductDescription(@QueryParam("productId") String productId){

		ProductPublisher productPublisher= ServiceLoader.getProductPublisher();
		if (productPublisher == null) throw new RuntimeException("ServiceLoader.getProductPublisher cannot be null.");

		ProductMetadata productDescription=null;
		try{
			if (productId == null) throw new RuntimeException("productId cannot be null.");
			productDescription = productPublisher.getProductDescription(productId);
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get product description", null, e.getMessage())).build();
		}

		ReplyMessage reply= new ReplyMessage();
		if (productDescription==null) {
			reply.setReplyComment("Product Description not found for productId for productId="+productId);
			reply.setProductMetadata(null);
		} else {
			reply.setReplyComment("Product Description found for productId="+productId);
			reply.setProductMetadata(productDescription);
		}
		
		return Response
				.status(200).entity(reply).build();

	}


	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_XML)
	public Response getProductDescriptionMap(){

		ProductPublisher productPublisher= ServiceLoader.getProductPublisher();
		if (productPublisher == null) throw new RuntimeException("ServiceLoader.getProductPublisher cannot be null.");

		Map<String, ProductMetadata> productDescrMap=null;
		try{
			productDescrMap = productPublisher.getProductDescriptionMap();
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get product description map", null, e.getMessage())).build();
		}

		ProductSpecMap productDescriptionMap = new ProductSpecMap();
		productDescriptionMap.getProductSpecMap().putAll(productDescrMap);

		return Response
				.status(200).entity(productDescriptionMap).build();

	}

	@GET
	@Path("/clearproductspecs")
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteProductDescriptions(){

		ProductPublisher productPublisher= ServiceLoader.getProductPublisher();
		if (productPublisher == null) throw new RuntimeException("ServiceLoader.getProductPublisher cannot be null.");

		try{
			productPublisher.deleteProductDescriptions();
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to delete product descriptions", null, e.getMessage())).build();
		}

		ReplyMessage reply= new ReplyMessage();
        reply.setReplyComment("All Product Specifications removed");
		
		return Response
				.status(200).entity(reply).build();

	}

}