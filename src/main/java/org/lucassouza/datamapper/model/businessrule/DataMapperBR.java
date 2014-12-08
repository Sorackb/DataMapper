package org.lucassouza.datamapper.model.businessrule;

import org.lucassouza.datamapper.model.persistence.DataMappingSQLServerPT;
import org.lucassouza.datamapper.model.persistence.Persistent;
import org.lucassouza.datamapper.type.RequestType;
import org.lucassouza.datamapper.vo.Child;
import org.lucassouza.datamapper.vo.Parameter;
import org.lucassouza.datamapper.vo.ChildParameter;
import org.lucassouza.datamapper.vo.Resource;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class DataMapperBR {

  Persistent dataMappingPT;

  public DataMapperBR() {
    this.dataMappingPT = new DataMappingSQLServerPT();
  }

  public String performAllActions(String requestJson,
          RequestType requestType) throws FileNotFoundException,
          JAXBException,
          SQLException,
          Exception {
    Gson converter = new Gson();
    LinkedHashMap<String, Object> json;
    LinkedHashMap<String, Object> parameters;
    String resource;

    json = converter.fromJson(requestJson, LinkedHashMap.class);
    resource = json.keySet().iterator().next();
    parameters = this.linkedTreeMapParaLinkedHashMap((LinkedTreeMap) ((ArrayList) json.get(resource)).get(0));

    return this.performAllActions(resource, requestType, parameters);
  }

  public String performAllActions(String resource, RequestType requestType,
          LinkedHashMap<String, Object> parameters) throws FileNotFoundException,
          JAXBException, SQLException, Exception {
    Resource configuration = MappingRuleBR.readResource(resource);
    Gson converter = new Gson();

    return converter.toJson(this.performAction(configuration, requestType, parameters));
  }

  private LinkedHashMap<String, Object> performAction(Resource configuration,
          RequestType requestType, LinkedHashMap<String, Object> parameterValueList)
          throws FileNotFoundException, JAXBException, SQLException, Exception {
    LinkedHashMap<String, Parameter> parameterConfigurationList = configuration.getParameters();
    LinkedHashMap<String, Object> fields = new LinkedHashMap<>();
    LinkedHashSet<LinkedHashMap<String, Object>> records;
    LinkedHashMap<String, Object> result = new LinkedHashMap<>();
    Set<String> parameterNameList;
    String stringParameterValue;

    if (parameterConfigurationList != null) {
      parameterNameList = parameterConfigurationList.keySet();

      for (String parameterName : parameterNameList) {
        Parameter parameter = parameterConfigurationList.get(parameterName);
        Object originalParameterValue = parameterValueList.get(parameterName);
        Object objectParameterValue = null;
        // Aqui vem basicamente os parâmetros que são provenientes da URL
        if (originalParameterValue == null || originalParameterValue instanceof String) {
          stringParameterValue = (String) originalParameterValue;

          if (stringParameterValue == null || stringParameterValue.trim().isEmpty()) {
            stringParameterValue = parameter.getDefaultValue();
          }

          if (stringParameterValue != null || (stringParameterValue != null
                  && !stringParameterValue.trim().isEmpty())) {
            if (!stringParameterValue.trim().isEmpty()
                    && parameter.getType().trim().toUpperCase().equals("INT")) {
              objectParameterValue = Long.parseLong(stringParameterValue);
            } else {
              objectParameterValue = stringParameterValue;
            }
          }
          // Aqui é todo parâmetro que já foi passado no formato que será utilizado
        } else {
          objectParameterValue = originalParameterValue;
        }

        fields.put(parameterName, objectParameterValue);
      }
    }

    if (configuration.getActions().containsKey(requestType.toString())) {
      records = this.dataMappingPT.performSQL(configuration.getActions().get(
              requestType.toString()), fields);
    } else {
      throw new Exception("\"" + requestType.toString() + "\" absent on \""
              + configuration.getName() + "\"");
    }

    // Configuração de filhos
    if (configuration.getChildren() != null) {
      for (LinkedHashMap<String, Object> record : records) {
        LinkedHashMap<String, Object> childResult;
        LinkedHashMap<String, Object> parameters;

        for (Child child : configuration.getChildren()) {
          parameters = new LinkedHashMap<>();

          // Para GET continua na loucura de parâmetros normalmente
          if (requestType.equals(RequestType.GET)) {
            for (ChildParameter childParameter : child.getParameters()) {
              // Relaciono o que tem no valor do parâmetro (indica o campo) com o registro em questão.
              parameters.put(childParameter.getName(), record.get(childParameter.getField()));
            }
            // SEARCH não possui filhos
          } else if (!requestType.equals(RequestType.SEARCH)) {
            if (parameterValueList.containsKey(child.getConfiguration().getName())) {
              parameters = (LinkedHashMap<String, Object>) parameterValueList.get(
                      child.getConfiguration().getName());
            }
          }

          childResult = this.performAction(child.getConfiguration(), requestType, parameters);
          record.put(child.getConfiguration().getName(), childResult);
        }
      }
    }

    result.put(configuration.getName().trim(), records);

    return result;
  }

  private LinkedHashMap<String, Object> linkedTreeMapParaLinkedHashMap(LinkedTreeMap linkedTreeMap) {
    LinkedHashMap<String, Object> result = new LinkedHashMap<>();

    Set<String> keys = linkedTreeMap.keySet();

    for (String key : keys) {
      Object value = linkedTreeMap.get(key);

      if (value instanceof LinkedTreeMap) {
        result.put(key, this.linkedTreeMapParaLinkedHashMap((LinkedTreeMap) value));
      } else {
        result.put(key, value);
      }
    }

    return result;
  }
}
