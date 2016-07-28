package org.opennms.plugins.dbnotifier.test.manual;

import javax.ws.rs.client.Client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
import org.junit.Test;
 
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
 
public class JaxrsTest {
 
	@Test
    public void test1() {
                // 1. Setup the JAX-RS Client and register the JacksonJsonProvider so that we can
                // marshall/unmarshall POJO <-> JSON
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
 
                // 2. Create reference to an endpoint (we can't do all the calls using the fluent API too)
        WebTarget rootTarget = client.target("http://localhost:9200");
 
        // 3. Build and call default endpoint
        Response response = rootTarget
                                    .request() // Build request
                                    .get();    // Call get method
 
        System.out.println("Default endpoint");
        System.out.println("Response code: " + response.getStatus());
        System.out.println("Response: \n" + response.readEntity(String.class));

    	Article newArticle = new Article("New Article", "Lorem Ipsum");
    	 newArticle.addTag("some-tag");
    	 
    	 // let's create an article
    	 Response createArticleResponse = rootTarget.path("articles/article")
    	             .request().post(Entity.json(newArticle));
    	 
    	System.out.println("Creating new article");
    	System.out.println("Response code: "+ createArticleResponse.getStatus());
    	System.out.println("Response :"+createArticleResponse.readEntity(String.class));

    	ESDocumentResponse createArticleResponse2 = rootTarget
    	           .path("articles/article").request()
    	           .post(Entity.json(newArticle), ESDocumentResponse.class);
    	 
    	System.out.println("Creating new article");
    	System.out.println("Created : " + createArticleResponse2.isCreated());
    	System.out.println("Article ID: " + createArticleResponse2.getId());
    	
    	// let's get the article we just created and marshall to
    	// ESDocumentResponse
    	ESDocumentResponse getArticleResponse = rootTarget
    	           .path("articles/article").path(createArticleResponse2.getId())
    	           .request().get(ESDocumentResponse.class);
    	 
    	System.out.println("Getting the article we just created");
    	System.out.println("ID: " + getArticleResponse.getId());
    	System.out.println("SOURCE TITLE: " + getArticleResponse.getSource().get("title"));
    }
}