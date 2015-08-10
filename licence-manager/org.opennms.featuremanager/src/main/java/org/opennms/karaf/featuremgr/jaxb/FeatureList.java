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
	private List<FeatureWrapper> featureList = new ArrayList<FeatureWrapper>();

	public List<FeatureWrapper> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(List<FeatureWrapper> featureList) {
		this.featureList = featureList;
	}

}