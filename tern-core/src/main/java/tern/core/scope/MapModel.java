package tern.core.scope;

import java.util.Map;

public class MapModel implements Model {
   
   private final Map<String, Object> model;
   
   public MapModel(Map<String, Object> model) {
      this.model = model;
   }

   @Override
   public Object getAttribute(String name) {
      return model.get(name);
   }

}