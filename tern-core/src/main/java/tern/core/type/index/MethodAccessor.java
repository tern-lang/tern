package tern.core.type.index;

import java.lang.reflect.Method;

import tern.core.type.Type;
import tern.core.error.InternalStateException;
import tern.core.function.Accessor;

public class MethodAccessor implements Accessor<Object> {

   private final ParameterConverter converter;
   private final Method write;
   private final Method read;

   public MethodAccessor(Type type, Method read){
      this(type, read, null);
   }
   
   public MethodAccessor(Type type, Method read, Method write){
      this.converter = new ParameterConverter(type);
      this.write = write;
      this.read = read;
   }
   
   @Override
   public Object getValue(Object source) {
      try {
         return read.invoke(source);
      } catch(Exception e) {
         throw new InternalStateException("Error occurred invoking " + read, e);
      }
   }

   @Override
   public void setValue(Object source, Object value) {
      try {
         if(write == null) {
            throw new InternalStateException("Illegal write for method " + read);
         }
         if(value != null){
            value = converter.convert(value);
         }
         write.invoke(source, value);
      } catch(Exception e) {
         throw new InternalStateException("Error occurred invoking " + write, e);
      }
   }
   
   private static class ParameterConverter {
      
      private final Type type;
      
      public ParameterConverter(Type type) {
         this.type = type;
      }
      
      public Object convert(Object value) {
         Class actual = value.getClass();
         Class parent = actual.getSuperclass();
         Class require = type.getType();
         
         if(require == String.class) {
            return String.valueOf(value);
         }
         if(parent == Number.class) {
            Number number = (Number)value;
            
            if(require == Double.class) {
               return number.doubleValue();
            }
            if(require == Integer.class) {
               return number.intValue();
            }
            if(require == Float.class) {
               return number.floatValue();
            }
            if(require == Long.class) {
               return number.longValue();
            }
            if(require == Short.class) {
               return number.shortValue();
            }
            if(require == Byte.class) {
               return number.byteValue();
            }
         }
         return value;
      }
   }
}