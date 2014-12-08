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
public class ActionMapAT extends XmlAdapter<ActionMapAT.ActionMapAdapted, TreeMap<String, String>> {

  public static class ActionMapAdapted {

    public LinkedHashSet<ActionMapAT.ActionRead> action = new LinkedHashSet<>();
  }

  public static class ActionRead {

    @XmlAttribute
    public String type;
    @XmlValue
    public String content;
  }

  @Override
  public TreeMap<String, String> unmarshal(ActionMapAT.ActionMapAdapted actionMapAdapted) throws Exception {
    TreeMap<String, String> map = new TreeMap<>();

    for (ActionMapAT.ActionRead readedAction : actionMapAdapted.action) {
      map.put(readedAction.type, readedAction.content);
    }

    return map;
  }

  @Override
  public ActionMapAT.ActionMapAdapted marshal(TreeMap<String, String> map) throws Exception {
    ActionMapAT.ActionMapAdapted actionMapAdapted = new ActionMapAT.ActionMapAdapted();

    for (Map.Entry<String, String> mapEntry : map.entrySet()) {
      ActionMapAT.ActionRead actionRead = new ActionMapAT.ActionRead();

      actionRead.type = mapEntry.getKey();
      actionRead.content = map.get(actionRead.type);

      actionMapAdapted.action.add(actionRead);
    }

    return actionMapAdapted;
  }
}
