package org.lucassouza.datamapper.vo;

import org.lucassouza.datamapper.vo.adapter.ActionMapAT;
import org.lucassouza.datamapper.vo.adapter.ParametersMapAT;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
@XmlRootElement(name = "resourceConfiguration")
public class Resource {

  private String name;
  private TreeMap<String, String> actions;
  private LinkedHashMap<String, Parameter> parameters;
  private LinkedHashSet<Child> children;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LinkedHashMap<String, Parameter> getParameters() {
    return parameters;
  }

  @XmlJavaTypeAdapter(ParametersMapAT.class)
  @XmlElement(name = "parameters")
  public void setParameters(LinkedHashMap<String, Parameter> parameters) {
    this.parameters = parameters;
  }

  @XmlElementWrapper(name = "children")
  @XmlElement(name = "child")
  public LinkedHashSet<Child> getChildren() {
    return children;
  }

  public void setChildren(LinkedHashSet<Child> children) {
    this.children = children;
  }

  public TreeMap<String, String> getActions() {
    return actions;
  }

  @XmlJavaTypeAdapter(ActionMapAT.class)
  @XmlElement(name = "actions")
  public void setActions(TreeMap<String, String> actions) {
    this.actions = actions;
  }
}
