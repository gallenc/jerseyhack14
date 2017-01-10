package org.opennms.plugin.graphml.client.test.manual;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.graphdrawing.graphml.GraphmlType;
import org.junit.Test;
import org.opennms.karaf.licencemgr.metadata.jaxb.ErrorMessage;
import org.opennms.plugin.graphml.client.GraphMLRestJerseyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphMLRestJerseyClientTest {
	private static final Logger LOG = LoggerFactory.getLogger(GraphMLRestJerseyClientTest.class);


	private String baseUrl = "http://localhost:8980";
	private String basePath = "/opennms/rest";
	private String userName = "admin"; // If userName is null no basic authentication is generated
	private String password = "admin";

	private String graphname="testgraph1";

	GraphMLRestJerseyClient client = null;

	private GraphMLRestJerseyClient getClient(){
		GraphMLRestJerseyClient client = new GraphMLRestJerseyClient();
		client.setBasePath(basePath);
		client.setBaseUrl(baseUrl);
		client.setPassword(password);
		client.setUserName(userName);
		return client;
	}

	private GraphmlType readTestGraph(){
		File graphmlfile = new File("./src/test/resources/test-graph.xml");
		LOG.debug("reading graph file from"+graphmlfile.getAbsolutePath());

		GraphmlType graph = JAXB.unmarshal(graphmlfile, GraphmlType.class);
		return graph;
	}
	
	@Test
	public void testsInOrder(){
		LOG.debug("testsInOrder() START");
		testCreateGraph();
		testGetGraph(); 
		testDeleteGraph();
		LOG.debug("testsInOrder() FINISH");
	}

	//@Test
	public void testCreateGraph() {
		LOG.debug("testCreateGraph() START");
		GraphmlType graphmlType = readTestGraph();

		try {
			GraphMLRestJerseyClient client = getClient();
			String response = client.createGraph(graphname, graphmlType);

			LOG.debug("response "+ response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("testCreateGraph() FINISH");

	}

	//@Test 
	public void testDeleteGraph() {
		LOG.debug("testdDeleteGraph() START");
		try {
			GraphMLRestJerseyClient client = getClient();
			String response = client.deleteGraph(graphname);

			LOG.debug("response "+ response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("testdDeleteGraph() FINISH");
	}

	//@Test
	public void testGetGraph() {
		LOG.debug("testGetGraph() START");
		try {
			GraphMLRestJerseyClient client = getClient();
			GraphmlType graphmlType = client.getGraph(graphname);

			String gmlstr=null;
			if (graphmlType !=null){
				gmlstr=graphmlTypeToString(graphmlType);
			}

			LOG.debug("response "+ gmlstr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("testGetGraph() FINISH");
	}

	public String graphmlTypeToString(GraphmlType graphmlType){
		try {
			StringWriter sw = new StringWriter();
			JAXBContext ctx = JAXBContext
					.newInstance("org.graphdrawing.graphml");
			Marshaller marshaller = ctx.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(graphmlType, sw);

			System.out.println(sw.toString());
			return sw.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
