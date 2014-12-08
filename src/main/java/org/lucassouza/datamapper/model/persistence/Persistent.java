package org.lucassouza.datamapper.model.persistence;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public interface Persistent {

  LinkedHashSet<LinkedHashMap<String, Object>> performSQL(String sql,
          LinkedHashMap<String, Object> parametros) throws SQLException;
}
