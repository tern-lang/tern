package tern.core.array;

import java.lang.reflect.Array;

public class ObjectList extends ArrayWrapper<Object> {

   private final Object[] array;
   private final Class type;
   private final int length;

   public ObjectList(Object[] array, Class type) {
      this.length = array.length;
      this.array = array;
      this.type = type;
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
      Object previous = array[index];
      array[index] = value;
      return previous;
   }

   @Override
   public Object[] toArray() {
      Object instance = Array.newInstance(type, length);
      Object[] copy = (Object[])instance;
      
      for(int i = 0; i < length; i++) {
         copy[i] = array[i];
      }
      return copy;
   }

   @Override
   public <T> T[] toArray(T[] copy) {
      Class type = copy.getClass();
      int require = copy.length;
     
      if(require >= length) {
         for(int i = 0; i < length; i++) {
            copy[i] = (T)array[i];
         }
      }
      return (T[])toArray();
   }

   @Override
   public int indexOf(Object object) {
      for (int i = 0; i < length; i++) {
         Object value = array[i];

         if (object.equals(value)) {
            return i;
         }
      }
      return -1;
   }
}