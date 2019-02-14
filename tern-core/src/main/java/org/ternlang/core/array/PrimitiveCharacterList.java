package org.ternlang.core.array;

import org.ternlang.core.error.InternalArgumentException;

public class PrimitiveCharacterList extends ArrayWrapper<Character> {

   private final char[] array;
   private final int length;

   public PrimitiveCharacterList(char[] array) {
      this.length = array.length;
      this.array = array;
   }

   @Override
   public int size() {
      return length;
   }
   
   @Override
   public Character get(int index) {
      return array[index];
   }
   
   @Override
   public Character set(int index, Character value) {
      Character previous = array[index];
      array[index] = value;
      return previous;
   }

   @Override
   public Object[] toArray() {
      Object[] copy = new Character[length];
      
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
         Character character = array[i];
         Object value = character;
         
         if(type == String[].class) {
            value = value.toString();
         } else if(type == Character[].class) {
            value = character;
         } else if(type == Object[].class) {
            value = character;
         } else {
            throw new InternalArgumentException("Incompatible array type");
         }
         copy[i] = (T)value;
      }
      return copy;
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