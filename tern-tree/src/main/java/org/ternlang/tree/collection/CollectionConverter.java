package org.ternlang.tree.collection;

import java.util.List;
import java.util.Map;

import org.ternlang.core.array.ArrayBuilder;
import org.ternlang.core.error.InternalArgumentException;
import org.ternlang.core.type.Type;

public class CollectionConverter {

   private final ArrayBuilder builder;
   
   public CollectionConverter() {
      this.builder = new ArrayBuilder();
   }
   
   public boolean accept(Object value) throws Exception {
      if(value != null) {
         Class type = value.getClass();
         
         if(type.isArray()) {
            return true;
         }
         if(List.class.isAssignableFrom(type)) {
            return true;
         }
         if(Map.class.isAssignableFrom(type)) {
            return true;
         }
      }
      return false;
   }
   
   public Object convert(Object value) throws Exception {
      if(value != null) {
         Class type = value.getClass();
         
         if(type.isArray()) {
            return builder.convert(value);
         }
         if(List.class.isAssignableFrom(type)) {
            return value;
         }
         if(Map.class.isAssignableFrom(type)) {
            return value;
         }
         if(Type.class.isAssignableFrom(type)) {
            throw new InternalArgumentException("Illegal index of type " + value);            
         }
         throw new InternalArgumentException("Illegal index of " + type);
      }
      return null;
   }
}