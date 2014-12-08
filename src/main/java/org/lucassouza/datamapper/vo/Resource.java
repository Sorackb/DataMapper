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
  private TreeMap<String, String> actionList;
  private LinkedHashMap<String, Parameter> parameterList;
  private LinkedHashSet<Child> childList;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LinkedHashMap<String, Parameter> getParameterList() {
    return parameterList;
  }

  @XmlJavaTypeAdapter(ParametersMapAT.class)
  @XmlElement(name = "parameterList")
  public void setParameterList(LinkedHashMap<String, Parameter> parameterList) {
    this.parameterList = parameterList;
  }

  @XmlElementWrapper(name = "childList")
  @XmlElement(name = "child")
  public LinkedHashSet<Child> getChildList() {
    return childList;
  }

  public void setChildList(LinkedHashSet<Child> filhos) {
    this.childList = filhos;
  }

  public TreeMap<String, String> getActionList() {
    return actionList;
  }

  @XmlJavaTypeAdapter(ActionMapAT.class)
  @XmlElement(name = "actionList")
  public void setActionList(TreeMap<String, String> actionList) {
    this.actionList = actionList;
  }
}
