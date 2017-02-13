package org.opennms.plugins.graphml.asset.test.manual;

import static org.junit.Assert.*;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.graphdrawing.graphml.xmlns.GraphmlType;
import org.graphdrawing.graphml.xmlns.ObjectFactory;
import org.junit.Test;
import org.opennms.netmgt.model.OnmsAssetRecord;
import org.opennms.netmgt.model.OnmsCategory;
import org.opennms.netmgt.model.OnmsGeolocation;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.model.monitoringLocations.OnmsMonitoringLocation;
import org.opennms.plugins.graphml.asset.AssetTopologyMapper;
import org.opennms.plugins.graphml.asset.AssetTopologyMapperImpl;
import org.opennms.plugins.graphml.asset.NodeInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetTopologyMapperImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(AssetTopologyMapperImplTest.class);

	@Test
	public void test() {
		
		AssetTopologyMapper assetTopologyMapper=new AssetTopologyMapperImpl();
		NodeInfoRepository nodeInfoRepository= NodeInfoRepositoryTest.getMockNodeInfoRepository();
	
		GraphmlType graphml = assetTopologyMapper.nodeInfoToTopology(nodeInfoRepository);
		LOG.debug("graphml:\n"+graphmlTypeToString(graphml));
		
	}
	
	
	public String graphmlTypeToString(GraphmlType graphmlType){
		try {
			
		      // Marshal graphmlType
	        ObjectFactory objectFactory = new ObjectFactory();
	        JAXBElement<GraphmlType> je =  objectFactory.createGraphml(graphmlType);
	        
			StringWriter sw = new StringWriter();
			JAXBContext ctx = JAXBContext.newInstance("org.graphdrawing.graphml.xmlns");

			Marshaller marshaller = ctx.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(je, sw);

			return sw.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
