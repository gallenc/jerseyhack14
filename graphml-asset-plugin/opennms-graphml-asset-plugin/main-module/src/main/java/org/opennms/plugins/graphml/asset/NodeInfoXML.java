package org.opennms.plugins.graphml.asset;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="nodeInfo")
@XmlAccessorType(XmlAccessType.NONE)
public class NodeInfoXML {

	@XmlElement(name="nodeId")
	private String nodeId=null;
	
	@XmlElement(name="nodeParamList")
	private List<NodeParameterXML> nodeParamList=new ArrayList<NodeParameterXML>();

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public List<NodeParameterXML> getNodeParamList() {
		return nodeParamList;
	}

	public void setNodeParamList(List<NodeParameterXML> nodeParamList) {
		this.nodeParamList = nodeParamList;
	}
}
