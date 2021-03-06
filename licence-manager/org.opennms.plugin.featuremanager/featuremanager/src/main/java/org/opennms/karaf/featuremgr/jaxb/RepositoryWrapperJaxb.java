package org.opennms.karaf.featuremgr.jaxb;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.karaf.features.Feature;
import org.apache.karaf.features.Repository;

/**
 * Wrapper for a Karaf repository including JAXB annotations.
 */
@XmlRootElement(name = "Repository")
public class RepositoryWrapperJaxb {

	private String name=null;
	private URI uri=null;
	private FeatureList features=null;
	private List<URI> repositories=null;

	public RepositoryWrapperJaxb() { }

	public RepositoryWrapperJaxb(String name,  URI uri) {
		this.name = name;
		this.uri = uri;
	}

	public RepositoryWrapperJaxb(String name, URI uri, FeatureList features, List<URI> repositories) {
		this.name = name;
		this.uri = uri;
		this.features=features;
		this.repositories=repositories;
	}
	

	public String getName() {
		return this.name;
	}

	@XmlElement(name="name")
	public void setName(String name) {
		this.name = name;
	}

	public URI getUri() {
		return uri;
	}

	@XmlElement(name="uri")
	public void setUri(URI uri) {
		this.uri = uri;
	}

	public FeatureList getFeatures() {
		return features;
	}

	@XmlElement(name="features")
	public void setFeatures(FeatureList features) {
		this.features = features;
	}

	public List<URI> getRepositories() {
		return repositories;
	}

	@XmlElementWrapper(name="repositories")
	@XmlElement(name="uri")
	public void setRepositories(List<URI> repositories) {
		this.repositories = repositories;
	}


}
