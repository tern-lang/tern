package org.ternlang.core.array;

import org.ternlang.core.error.InternalArgumentException;

public class BooleanList extends ArrayWrapper<Boolean> {

   private final Boolean[] array;
   private final int length;

   public BooleanList(Boolean[] array) {
      this.length = array.length;
      this.array = array;
   }

   @Override
   public int size() {
      return length;
   }
   
   @Override
   public Boolean get(int index) {
      return array[index];
   }

   @Override
   public Boolean set(int index, Boolean value) {
      Boolean previous = array[index];
      array[index] = value;
      return previous;
   }

   @Override
   public Object[] toArray() {
      Object[] copy = new Boolean[length];
      
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
         Boolean flag = array[i];
         Object value = flag;
         
         if(type == String[].class) {
            value = value.toString();
         } else if(type == Boolean[].class) {
            value = flag;
         } else if(type == Object[].class) {
            value = flag;
         } else {
            throw new InternalArgumentException("Incompatible array type");
         }
         copy[i] = (T)value;
      }
      return copy;
   }

   @Override
   public int indexOf(Object object) {
      Class type = object.getClass();
      
      for (int i = 0; i < length; i++) {
         Object value = array[i];

         if (object.equals(value)) {
            return i;
         }
      }
      return -1;
   }
}