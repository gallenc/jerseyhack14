package org.opennms.plugins.graphml.asset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="nodeParameter")
@XmlAccessorType(XmlAccessType.NONE)
public class NodeParameterXML {

	@XmlElement(name="paramKey")
	private String paramKey=null;
	
	@XmlElement(name="paramValue")
	private String paramValue=null;

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	


}
