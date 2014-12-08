package org.lucassouza.datamapper.model.persistence;

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
    String instance = null;
    Connection connection;  //atributo do tipo Connection
    String instanceServer[];
    String sqlServer;

    try {
      instanceServer = Configuration.getProperty("connection.host", "127.0.0.1").split("\\\\");
      sqlServer = instanceServer[0];

      if (instanceServer.length > 1) {
        instance = instanceServer[1];
      }

      ds.setServerName(sqlServer);
      ds.setInstanceName(instance);
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
