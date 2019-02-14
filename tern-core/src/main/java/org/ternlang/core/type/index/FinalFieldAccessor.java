package org.ternlang.core.type.index;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Accessor;

public class FinalFieldAccessor implements Accessor<Object>{

   private final AtomicReference<Object> reference;
   private final Field field;
   
   public FinalFieldAccessor(Field field){
      this.reference = new AtomicReference<Object>();
      this.field = field;
   }
   
   @Override
   public Object getValue(Object source) {
      try {
         Object value = reference.get();
         
         if(value == null) {
            value = field.get(source);
            reference.set(value);
         }
         return value;
      } catch(Exception e) {
         throw new InternalStateException("Illegal access to " + field, e);
      }
   }

   @Override
   public void setValue(Object source, Object value) {
      throw new InternalStateException("Illegal modification of final " + field);
   }

}