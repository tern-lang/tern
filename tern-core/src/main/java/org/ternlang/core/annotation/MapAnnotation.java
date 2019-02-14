package org.ternlang.core.annotation;

import java.util.Map;

public class MapAnnotation implements Annotation {
   
   private final Map<String, Object> attributes;
   private final String name;
   
   public MapAnnotation(String name, Map<String, Object> attributes){
      this.attributes = attributes;
      this.name = name;
   }

   @Override
   public Object getAttribute(String name) {
      return attributes.get(name);
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public String toString() {
      return String.format("@%s(%s)", name, attributes);
   }
}