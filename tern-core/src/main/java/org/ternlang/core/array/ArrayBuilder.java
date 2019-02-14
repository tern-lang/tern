package org.ternlang.core.array;

import java.lang.reflect.Array;
import java.util.List;

import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class ArrayBuilder {
   
   public ArrayBuilder() {
      super();
   }

   public Type convert(Type type) throws Exception {
      Scope scope = type.getScope();
      Module module = scope.getModule();
      
      return module.getType(List.class);
   }
   
   public List convert(Object array) throws Exception {
      Class type = array.getClass();
      
      if(type == byte[].class) {
         return new PrimitiveByteList((byte[])array);
      }
      if(type == int[].class) { 
         return new PrimitiveIntegerList((int[])array);
      }
      if(type == long[].class) {
         return new PrimitiveLongList((long[])array);
      }
      if(type == double[].class) {
         return new PrimitiveDoubleList((double[])array);
      }
      if(type == float[].class) {
         return new PrimitiveFloatList((float[])array);
      }
      if(type == short[].class) {
         return new PrimitiveShortList((short[])array);
      }
      if(type == char[].class) {
         return new PrimitiveCharacterList((char[])array);
      }
      if(type == boolean[].class) {
         return new PrimitiveBooleanList((boolean[])array);
      }
      if(type == Byte[].class) {
         return new ByteList((Byte[])array);
      }
      if(type == Integer[].class) {
         return new IntegerList((Integer[])array);
      }
      if(type == Long[].class) {
         return new LongList((Long[])array);
      }
      if(type == Double[].class) {
         return new DoubleList((Double[])array);
      }
      if(type == Float[].class) {
         return new FloatList((Float[])array);
      }
      if(type == Short[].class) {
         return new ShortList((Short[])array);
      }
      if(type == Character[].class) {
         return new CharacterList((Character[])array);
      }
      if(type == Boolean[].class) {
         return new BooleanList((Boolean[])array);
      }
      return new ObjectList((Object[])array, type);
   }
   
   public Object create(Class type, int size) throws Exception {
      if(type == byte.class || type == Byte.class) {
         return new byte[size];
      }
      if(type == int.class || type == Integer.class) {
         return new int[size];
      }
      if(type == long.class || type == Long.class) {
         return new long[size];
      }
      if(type == double.class || type == Double.class) {
         return new double[size];
      }
      if(type == float.class || type == Float.class) {
         return new float[size];
      }
      if(type == short.class || type == Short.class) {
         return new short[size];
      }
      if(type == char.class || type == Character.class) {
         return new char[size];
      }
      if(type == boolean.class || type == Boolean.class) {
         return new boolean[size];
      }
      if(type == null) {
         return new Object[size];
      }
      return Array.newInstance(type, size);
   }
   
   public Object create(Class type, int first, int second) throws Exception {
      if(type == byte.class || type == Byte.class) {
         return new byte[first][second];
      }
      if(type == int.class || type == Integer.class) {
         return new int[first][second];
      }
      if(type == long.class || type == Long.class) {
         return new long[first][second];
      }
      if(type == double.class || type == Double.class) {
         return new double[first][second];
      }
      if(type == float.class || type == Float.class) {
         return new float[first][second];
      }
      if(type == short.class || type == Short.class) {
         return new short[first][second];
      }
      if(type == char.class || type == Character.class) {
         return new char[first][second];
      }
      if(type == boolean.class || type == Boolean.class) {
         return new boolean[first][second];
      }
      if(type == null) {
         return new Object[first][second];
      }
      return Array.newInstance(type, first, second);
   }
   
   public Object create(Class type, int first, int second, int third) throws Exception {
      if(type == byte.class || type == Byte.class) {
         return new byte[first][second][third];
      }
      if(type == int.class || type == Integer.class) {
         return new int[first][second][third];
      }
      if(type == long.class || type == Long.class) {
         return new long[first][second][third];
      }
      if(type == double.class || type == Double.class) {
         return new double[first][second][third];
      }
      if(type == float.class || type == Float.class) {
         return new float[first][second][third];
      }
      if(type == short.class || type == Short.class) {
         return new short[first][second][third];
      }
      if(type == char.class || type == Character.class) {
         return new char[first][second][third];
      }
      if(type == boolean.class || type == Boolean.class) {
         return new boolean[first][second][third];
      }
      if(type == null) {
         return new Object[first][second][third];
      }
      return Array.newInstance(type, first, second, third);
   }
}