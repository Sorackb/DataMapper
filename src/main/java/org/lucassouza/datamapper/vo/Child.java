package org.lucassouza.datamapper.vo;

import java.util.LinkedHashSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
@XmlRootElement(name = "child")
@XmlAccessorType(XmlAccessType.FIELD)
public class Child {

  @XmlAttribute
  private String resource;
  @XmlElementWrapper(name = "childParameters")
  @XmlElement(name = "childParameter")
  private LinkedHashSet<ChildParameter> childParameters;
  @XmlTransient
  private Resource configuration;

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public LinkedHashSet<ChildParameter> getParameters() {
    return childParameters;
  }

  public void setParameters(LinkedHashSet<ChildParameter> childParameterList) {
    this.childParameters = childParameterList;
  }

  public Resource getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Resource configuration) {
    this.configuration = configuration;
  }
}
