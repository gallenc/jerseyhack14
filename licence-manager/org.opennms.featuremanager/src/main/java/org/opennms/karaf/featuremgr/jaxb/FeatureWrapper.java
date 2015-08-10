package org.opennms.karaf.featuremgr.jaxb;

import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

/**
* Wrapper to a Karaf feature including JAXB and JAX-RS annotations.
*/
@XmlRootElement(name = "Feature")
public class FeatureWrapper {

  private String name;
  private String version;

  public FeatureWrapper() { }

  public FeatureWrapper(String name, String version) {
    this.name = name;
    this.version = version;
  }

  @Path("name")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Path("version")
  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

}
