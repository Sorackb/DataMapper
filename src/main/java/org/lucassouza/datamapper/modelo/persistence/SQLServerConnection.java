package org.lucassouza.datamapper.modelo.persistence;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import org.lucassouza.datamapper.utils.Configuration;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class SQLServerConnection {

  public static Connection getSQLServerConnection() {
    SQLServerDataSource ds = new SQLServerDataSource();
    String instancia = null;
    Connection connection;  //atributo do tipo Connection
    String servidorInstancia[];
    String servidorSQL;

    try {
      servidorInstancia = Configuration.getProperty("connection.host", "127.0.0.1").split("\\\\");
      servidorSQL = servidorInstancia[0];

      if (servidorInstancia.length > 1) {
        instancia = servidorInstancia[1];
      }

      ds.setServerName(servidorSQL);
      ds.setInstanceName(instancia);
      ds.setDatabaseName(Configuration.getProperty("connection.database"));
      ds.setUser(Configuration.getProperty("connection.user"));
      ds.setPassword(Configuration.getProperty("connection.password"));
      connection = ds.getConnection();

      return connection;

    } catch (SQLServerException e) {
      //Não conseguindo se conectar ao banco
      System.out.println(e.getMessage());
      return null;
    }
  }
}
