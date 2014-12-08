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
public class ParametersMapAT extends XmlAdapter<ParametersMapAT.ParametroMapAdaptado, LinkedHashMap<String, Parameter>> {

  public static class ParametroMapAdaptado {

    public LinkedHashSet<ParametroLido> parametro = new LinkedHashSet<>();
  }

  public static class ParametroLido {

    @XmlAttribute
    public String nome;
    @XmlAttribute
    public String tipo;
    @XmlValue
    public String padrao;
  }

  @Override
  public LinkedHashMap<String, Parameter> unmarshal(ParametroMapAdaptado parametroMapAdaptado)
          throws Exception {
    LinkedHashMap<String, Parameter> map = new LinkedHashMap<>();

    for (ParametroLido parametroLido : parametroMapAdaptado.parametro) {
      Parameter configuracaoParametro = new Parameter();

      configuracaoParametro.setType(parametroLido.tipo);
      configuracaoParametro.setDefaultValue(parametroLido.padrao);

      map.put(parametroLido.nome, configuracaoParametro);
    }

    return map;
  }

  @Override
  public ParametroMapAdaptado marshal(LinkedHashMap<String, Parameter> map) throws Exception {
    ParametroMapAdaptado parametroMapAdaptado = new ParametroMapAdaptado();

    for (Map.Entry<String, Parameter> mapEntry : map.entrySet()) {
      ParametroLido parametroLido = new ParametroLido();

      parametroLido.nome = mapEntry.getKey();
      parametroLido.tipo = map.get(parametroLido.nome).getType();
      parametroLido.padrao = map.get(parametroLido.nome).getDefaultValue();

      parametroMapAdaptado.parametro.add(parametroLido);
    }

    return parametroMapAdaptado;
  }
}
