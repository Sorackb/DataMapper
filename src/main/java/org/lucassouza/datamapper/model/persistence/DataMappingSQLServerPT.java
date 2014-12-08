package org.lucassouza.datamapper.model.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class DataMappingSQLServerPT implements Persistent {

  @Override
  public LinkedHashSet<LinkedHashMap<String, Object>> performSQL(String sql,
          LinkedHashMap<String, Object> parameters) throws SQLException {
    LinkedHashSet<LinkedHashMap<String, Object>> result = new LinkedHashSet<>();
    Set<String> parameterNameList = parameters.keySet();

    Connection connection = SQLServerConnection.getSQLServerConnection();
    CallableStatement statement;
    ResultSet resultSet;
    ResultSetMetaData resultSetMetaData;

    for (String parameterName : parameterNameList) {
      Object valor = parameters.get(parameterName);

      if (valor instanceof String) {
        valor = "'" + valor + "'";
      }

      sql = sql.replace("[" + parameterName + "]", String.valueOf(valor));
    }

    statement = connection.prepareCall(sql);

    resultSet = statement.executeQuery();
    resultSetMetaData = resultSet.getMetaData();

    while (resultSet.next()) {
      LinkedHashMap<String, Object> record = new LinkedHashMap<>();

      for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
        record.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
      }

      result.add(record);
    }

    return result;
  }
}
