package org.ternlang.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ternlang.core.error.InternalArgumentException;

public class AnnotationConverter {
   
   public AnnotationConverter() {
      super();
   }
   
   public Object convert(Object object) throws Exception {
      if(object != null) {
         Class type = extractType(object);
         
         if(type.isArray()) {
            return convertArray(object);
         }
         if(type.isAnnotation()) {
            return convertValue(object);
         }
         return object;
      }
      return null;
   }
   
   private Object convertArray(Object object) throws Exception {
      int length = Array.getLength(object);
      
      if(length < 0) {
         throw new InternalArgumentException("Invalid array length " + length);
      }
      List<Object> list = new ArrayList<Object>();
      
      for(int i = 0; i < length; i++) {
         Object value = Array.get(object, i);
         
         if(value != null) {
            value = convert(value);
         }
         list.add(value);
      }
      return list;
   }
   
   private Object convertValue(Object object) throws Exception {
      Class type = extractType(object);
      String name = type.getSimpleName();
      Method[] methods = type.getDeclaredMethods();

      if(!type.isAnnotation()) {
         throw new InternalArgumentException("Invalid annotation " + type);
      }
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      MapAnnotation annotation = new MapAnnotation(name, map);
      
      for(Method method : methods) {
         String key = method.getName();
         Object value = method.invoke(object);
         Object conversion = convert(value);
         
         map.put(key, conversion);
      }
      return annotation;
   }
   
   private Class extractType(Object value) throws Exception {
      Class type = value.getClass();
      
      if(Annotation.class.isInstance(value)) {
         Annotation annotation = (Annotation)value;
         return annotation.annotationType();
      }
      return type;
   }
}