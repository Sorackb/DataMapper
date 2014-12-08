package org.lucassouza.datamapper.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 *
 * Esta classe é separada da Parametro porque tem uma estrutura diferente
 */
@XmlRootElement(name = "childParameter")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChildParameter {

  @XmlAttribute
  private String name;
  @XmlAttribute
  private String type;
  @XmlValue
  private String field;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }
}
