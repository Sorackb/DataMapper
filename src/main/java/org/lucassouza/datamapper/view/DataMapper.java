package org.lucassouza.datamapper.view;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.lucassouza.datamapper.model.businessrule.DataMapperBR;
import org.lucassouza.datamapper.type.RequestType;

/**
 *
* @author Lucas Souza [sorackb@gmail.com]
 */
public class DataMapper {
  public static void main(String[] args) {
    DataMapperBR dataMapperBR = new DataMapperBR();
    LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
    String result;
    
    try {
      parameters.put("ID", 1);
      result = dataMapperBR.performAllActions("brand", RequestType.GET, parameters);
      System.out.println(result);
    } catch (JAXBException ex) {
      Logger.getLogger(DataMapper.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(DataMapper.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(DataMapper.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
