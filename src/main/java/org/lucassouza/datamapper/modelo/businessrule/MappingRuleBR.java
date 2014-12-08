package org.lucassouza.datamapper.modelo.businessrule;

import org.lucassouza.datamapper.vo.Child;
import org.lucassouza.datamapper.vo.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.lucassouza.datamapper.utils.Configuration;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class MappingRuleBR {

  private static final HashMap<String, Resource> resourceList = new HashMap<>();
  private static String mappingFolder;

  public static void load() {
    mappingFolder = Configuration.getProperty("basico.mappingfolder");
    load(new File(mappingFolder));
  }

  public static void load(final File folder) {
    for (final File fileName : folder.listFiles()) {
      if (fileName.isDirectory()) {
        load(fileName);
      } else if (fileName.getName().contains(".xml")) {
        try {
          readResource(fileName.getName().replace(".xml", ""));
        } catch (FileNotFoundException | JAXBException ex) {
          Logger.getLogger(MappingRuleBR.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  public static Resource readResource(String resourceName) throws FileNotFoundException, JAXBException {
    Resource configuration = null;
    JAXBContext jaxbContext;
    Unmarshaller unmarshaller;
    File xml;

    if (resourceList.containsKey(resourceName)) {
      configuration = resourceList.get(resourceName);
    } else {
      jaxbContext = JAXBContext.newInstance(Resource.class);
      unmarshaller = jaxbContext.createUnmarshaller();
      xml = new File(mappingFolder + "/" + resourceName + ".xml");

      if (xml.exists()) {
        LinkedHashSet<Child> children;

        configuration = (Resource) unmarshaller.unmarshal(xml);
        children = configuration.getChildList();
        if (children != null) {
          for (Child childConfiguration : children) {
            String childResourceDescription = childConfiguration.getResource();
            Resource childResource = readResource(childResourceDescription);

            if (childResource != null) {
              childConfiguration.setConfiguration(childResource);
            }
          }
        }

        resourceList.put(resourceName, configuration);
      } else {
        throw new FileNotFoundException("Resource configuration \"" + resourceName + "\" not found!");
      }
    }

    return configuration;
  }
}
