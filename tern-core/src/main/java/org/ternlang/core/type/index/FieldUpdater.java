package org.ternlang.core.type.index;

import java.lang.reflect.Field;

public class FieldUpdater {
   
   private volatile Field field;
   private volatile String name;
   
   public FieldUpdater(String name) {
      this.name = name;
   }
   
   public boolean update(Object object, Object value) {
      try {
         if(field == null) {
            field = Throwable.class.getDeclaredField(name);
         }
         field.setAccessible(true);
         field.set(object, value);
         return true;
      } catch(Exception e) {
         return false;
      }
   }
}
