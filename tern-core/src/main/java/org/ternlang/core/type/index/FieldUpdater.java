package org.ternlang.core.type.index;

import java.lang.reflect.Field;

public class FieldUpdater {
   
   private Field field;
   private String name;
   private Class type;
   
   public FieldUpdater(Class type, String name) {
      this.name = name;
      this.type = type;
   }
   
   public boolean update(Object object, Object value) {
      try {
         if(field == null) {
            field = type.getDeclaredField(name);
         }
         field.setAccessible(true);
         field.set(object, value);
         return true;
      } catch(Exception e) {
         return false;
      }
   }
}
