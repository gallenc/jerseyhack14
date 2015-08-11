package org.opennms.karaf.featuremgr.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper to a Karaf feature including JAXB and JAX-RS annotations.
 */
@XmlRootElement(name = "Feature")
public class FeatureWrapper {

	private String name;
	private String version;
	private String description;
	private String details;

	public FeatureWrapper() { }

	public FeatureWrapper(String name, String version, String description, String details) {
		this.name = name;
		this.version = version;
		this.description=description;
		this.details=details;
	}

	public String getName() {
		return this.name;
	}

	@XmlElement(name="name")
	public void setName(String name) {
		this.name = name;
	}


	public String getVersion() {
		return this.version;
	}

	@XmlElement(name="version")
	public void setVersion(String version) {
		this.version = version;
	}


	public String getDescription() {
		return description;
	}

	@XmlElement(name="description")
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	@XmlElement(name="details")
	public void setDetails(String details) {
		this.details = details;
	}

}
