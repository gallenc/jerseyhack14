package org.opennms.karaf.featuremgr.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="repositories")
@XmlAccessorType(XmlAccessType.NONE)
public class RepositoryList {

	@XmlElementWrapper(name="repositoryList")
	@XmlElement(name="repository")
	private List<RepositoryWrapperJaxb> repositoryList = new ArrayList<RepositoryWrapperJaxb>();
	
	public List<RepositoryWrapperJaxb> getRepositoryList() {
		return repositoryList;
	}

	public void setRepositoryList(List<RepositoryWrapperJaxb> repositoryList) {
		this.repositoryList = repositoryList;
	}


}