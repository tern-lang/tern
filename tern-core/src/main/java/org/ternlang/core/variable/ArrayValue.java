package org.ternlang.core.variable;

import java.lang.reflect.Array;

public class ArrayValue extends Value {
   
   private final Object array;
   private final Integer index;
   private final Class type;
   
   public ArrayValue(Object array, Integer index) {
      this.type = array.getClass();
      this.array = array;
      this.index = index;
   }
   
   @Override
   public Class getType() {
      return type.getComponentType();
   }
   
   @Override
   public Object getValue(){
      return Array.get(array, index);
   }
   
   @Override
   public void setValue(Object value){
      Array.set(array, index, value);
   }       
   
   @Override
   public String toString() {
      return String.valueOf(array);
   }
}