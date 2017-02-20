package org.opennms.plugins.graphml.asset;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="nodeInfoRepository")
@XmlAccessorType(XmlAccessType.NONE)
public class NodeInfoRepositoryXML {


	@XmlElementWrapper(name="nodeInfoList")
	@XmlElement(name="nodeInfo")
	private List<NodeInfoXML> nodeInfoList =  new ArrayList<NodeInfoXML>();

	public List<NodeInfoXML> getNodeInfoList() {
		return nodeInfoList;
	}

	public void setNodeInfoList(List<NodeInfoXML> nodeInfo) {
		this.nodeInfoList = nodeInfo;
	}

	//nodeid, paramkey, paramvalue
	//Map<String, Map<String, String>>
	public static String nodeInfoToXML(Map<String, Map<String, String>> nodeInfo){
		try {
			
			NodeInfoRepositoryXML nodeInfoRepositoryxml= new NodeInfoRepositoryXML();

			List<NodeInfoXML> nodeinfoListxml = nodeInfoRepositoryxml.getNodeInfoList();

			for (String nodeId : nodeInfo.keySet()){
				NodeInfoXML nodeInfoXML= new NodeInfoXML();
				nodeInfoXML.setNodeId(nodeId);
				nodeinfoListxml.add(nodeInfoXML);

				List<NodeParameterXML> nodeParamListxml = nodeInfoXML.getNodeParamList();

				for( String paramkey : nodeInfo.get(nodeId).keySet()){
					NodeParameterXML nodeParamaterxml=new NodeParameterXML();
					nodeParamaterxml.setParamKey(paramkey);
					String paramValue = nodeInfo.get(nodeId).get(paramkey);
					nodeParamaterxml.setParamValue(paramValue );
					nodeParamListxml.add(nodeParamaterxml);
				}
			}

			// marshal to string and return
			StringWriter sw = new StringWriter();
			JAXBContext jaxbContext = JAXBContext.newInstance(NodeInfoRepositoryXML.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(nodeInfoRepositoryxml, sw);
			return sw.toString();
		} catch (JAXBException e) {
			throw new RuntimeException("cannot marshal NodeInfoRepository. ",e);
		}

	}

	//nodeid, paramkey, paramvalue
	//Map<String, Map<String, String>>
	public static void XMLtoNodeInfo(Map<String, Map<String, String>> nodeInfo, String xmlStr){
		try{

			nodeInfo.clear();

			JAXBContext jaxbContext = JAXBContext.newInstance(NodeInfoRepositoryXML.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			Reader reader = new StringReader(xmlStr);
			NodeInfoRepositoryXML nodeInfoRepositoryXML = (NodeInfoRepositoryXML) jaxbUnmarshaller.unmarshal(reader);

			List<NodeInfoXML> nodeInfoListxml = nodeInfoRepositoryXML.getNodeInfoList();
			if (nodeInfoListxml!=null){
				for(NodeInfoXML nodeInfoXML:nodeInfoListxml){
					String nodeid = nodeInfoXML.getNodeId();
					if(nodeid!=null){
						Map<String, String> paramvalues = new LinkedHashMap<String, String>();
						for (NodeParameterXML nodeParameterxml:nodeInfoXML.getNodeParamList()){
							String key=nodeParameterxml.getParamKey();
							if(key!=null){
								String value=nodeParameterxml.getParamValue();
								paramvalues.put(key, value);
							}
						}
						nodeInfo.put(nodeid, paramvalues);
					}
				}
			}

		} catch (JAXBException e) {
			throw new RuntimeException("cannot unmarshal NodeInfoRepository. ",e);
		}
	}



}
