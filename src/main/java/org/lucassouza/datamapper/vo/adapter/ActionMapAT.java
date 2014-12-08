/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package org.lucassouza.datamapper.vo.adapter;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ActionMapAT extends XmlAdapter<ActionMapAT.AcaoMapAdaptado, TreeMap<String, String>> {

  public static class AcaoMapAdaptado {

    public LinkedHashSet<ActionMapAT.AcaoLida> acao = new LinkedHashSet<>();
  }

  public static class AcaoLida {

    @XmlAttribute
    public String tipo;
    @XmlValue
    public String conteudo;
  }

  @Override
  public TreeMap<String, String> unmarshal(ActionMapAT.AcaoMapAdaptado acaoMapAdaptado) throws Exception {
    TreeMap<String, String> map = new TreeMap<>();

    for (ActionMapAT.AcaoLida acaoLida : acaoMapAdaptado.acao) {
      map.put(acaoLida.tipo, acaoLida.conteudo);
    }

    return map;
  }

  @Override
  public ActionMapAT.AcaoMapAdaptado marshal(TreeMap<String, String> map) throws Exception {
    ActionMapAT.AcaoMapAdaptado acaoMapAdaptado = new ActionMapAT.AcaoMapAdaptado();

    for (Map.Entry<String, String> mapEntry : map.entrySet()) {
      ActionMapAT.AcaoLida acaoLida = new ActionMapAT.AcaoLida();

      acaoLida.tipo = mapEntry.getKey();
      acaoLida.conteudo = map.get(acaoLida.tipo);

      acaoMapAdaptado.acao.add(acaoLida);
    }

    return acaoMapAdaptado;
  }
}
