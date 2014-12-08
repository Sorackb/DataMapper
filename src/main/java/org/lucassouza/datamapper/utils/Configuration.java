package org.lucassouza.datamapper.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lucassouza.tools.PropertyTool;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class Configuration {

  private static PropertyTool iniFile;

  public static void readFile() {
    String systemPath;
    File system;

    if (iniFile == null) {
      iniFile = new PropertyTool();
      systemPath = Configuration.class.getProtectionDomain().getCodeSource()
              .getLocation().getPath();
      system = new File(systemPath);

      try {
        if (system.getParent().contains("target")) {
          iniFile.readPropertyFile("C:/DataMapper/config.ini");
        } else {
          iniFile.readPropertyFile(system.getParent() + "/config.ini");
        }
      } catch (IOException ex) {
        Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public static String getProperty(String key) {
    readFile();

    return iniFile.getProperty(key);
  }

  public static String getProperty(String key, String defaultValue) {
    readFile();

    return iniFile.getProperty(key, defaultValue);
  }
}
