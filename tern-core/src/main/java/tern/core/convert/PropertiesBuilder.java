package tern.core.convert;

import java.util.Map;
import java.util.Properties;

public class PropertiesBuilder {

   public static Properties create(Map map) {
      Properties properties = new Properties();
      
      if(!map.isEmpty()) {
         properties.putAll(map);
      }
      return properties;
   }
}
