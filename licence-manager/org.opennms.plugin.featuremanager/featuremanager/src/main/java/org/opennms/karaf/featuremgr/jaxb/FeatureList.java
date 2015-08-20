package org.opennms.karaf.featuremgr.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="features")
@XmlAccessorType(XmlAccessType.NONE)
public class FeatureList {


	@XmlElementWrapper(name="featureList")
	@XmlElement(name="feature")
	private List<FeatureWrapperJaxb> featureList = new ArrayList<FeatureWrapperJaxb>();

	public List<FeatureWrapperJaxb> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(List<FeatureWrapperJaxb> featureList) {
		this.featureList = featureList;
	}

}