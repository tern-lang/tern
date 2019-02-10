package tern.core.array;

import tern.core.error.InternalArgumentException;

public class PrimitiveFloatList extends ArrayWrapper<Object> {

   private final float[] array;
   private final int length;
   
   public PrimitiveFloatList(float[] array) {
      this.length = array.length;
      this.array = array;
   }

   @Override
   public int size() {
      return length;
   }

   @Override
   public Object get(int index) {
      return array[index];
   }

   @Override
   public Object set(int index, Object value) {
      Float previous = array[index];
      Class type = value.getClass();
      
      if(type == String.class) {
         String text = (String)value;
         array[index] = Float.parseFloat(text);
      } else {
         Number number = (Number)value;
         array[index] = number.floatValue();
      }
      return previous;
   }
   
   @Override
   public Object[] toArray() {
      Object[] copy = new Float[length];
      
      for(int i = 0; i < length; i++) {
         copy[i] = array[i];
      }
      return copy;
   }

   @Override
   public <T> T[] toArray(T[] copy) {
      Class type = copy.getClass();
      int require = copy.length;
     
      for(int i = 0; i < length && i < require; i++) {
         Float number = array[i];
         Object value = number;
         
         if(type != Float[].class) {
            if(type == Byte[].class) {
               value = number.byteValue();
            } else if(type == Double[].class) {
               value = number.doubleValue();
            } else if(type == Integer[].class) {
               value = number.intValue();
            } else if(type == Long[].class) {
               value = number.longValue();
            } else if(type == Short[].class) {
               value = number.shortValue();
            } else if(type == String[].class) {
               value = number.toString();
            } else if(type == Object[].class) {
               value = number;
            } else {
               throw new InternalArgumentException("Incompatible array type");
            }
         }
         copy[i] = (T)value;
      }
      return copy;
   }

   @Override
   public int indexOf(Object object) {
      Class type = object.getClass();
      
      for (int i = 0; i < length; i++) {
         Float number = array[i];
         Object value = number;
         
         if(type != Float.class) {
            if(type == Integer.class) {
               value = number.intValue();
            } else if(type == Byte.class) {
               value = number.byteValue();
            } else if(type == Double.class) {
               value = number.doubleValue();
            } else if(type == Long.class) {
               value = number.longValue();
            } else if(type == Short.class) {
               value = number.shortValue();
            } else if(type == String.class) {
               value = number.toString();
            } else {
               throw new InternalArgumentException("Incompatible value type");
            }
         }
         if (object.equals(value)) {
            return i;
         }
      }
      return -1;
   }
}