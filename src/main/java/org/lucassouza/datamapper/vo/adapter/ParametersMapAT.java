package org.lucassouza.datamapper.vo.adapter;

import org.lucassouza.datamapper.vo.Parameter;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ParametersMapAT extends XmlAdapter<ParametersMapAT.ParametersMapAdapted, LinkedHashMap<String, Parameter>> {

  public static class ParametersMapAdapted {

    public LinkedHashSet<ParameterRead> parameter = new LinkedHashSet<>();
  }

  public static class ParameterRead {

    @XmlAttribute
    public String name;
    @XmlAttribute
    public String type;
    @XmlValue
    public String defaultValue;
  }

  @Override
  public LinkedHashMap<String, Parameter> unmarshal(ParametersMapAdapted parametersMapAdapted)
          throws Exception {
    LinkedHashMap<String, Parameter> map = new LinkedHashMap<>();

    for (ParameterRead parameterRead : parametersMapAdapted.parameter) {
      Parameter parameterConfiguration = new Parameter();

      parameterConfiguration.setType(parameterRead.type);
      parameterConfiguration.setDefaultValue(parameterRead.defaultValue);

      map.put(parameterRead.name, parameterConfiguration);
    }

    return map;
  }

  @Override
  public ParametersMapAdapted marshal(LinkedHashMap<String, Parameter> map) throws Exception {
    ParametersMapAdapted parametersMapAdapted = new ParametersMapAdapted();

    for (Map.Entry<String, Parameter> mapEntry : map.entrySet()) {
      ParameterRead parameterRead = new ParameterRead();

      parameterRead.name = mapEntry.getKey();
      parameterRead.type = map.get(parameterRead.name).getType();
      parameterRead.defaultValue = map.get(parameterRead.name).getDefaultValue();

      parametersMapAdapted.parameter.add(parameterRead);
    }

    return parametersMapAdapted;
  }
}
