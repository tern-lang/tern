package org.ternlang.compile;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import junit.framework.TestCase;


public class GenericReflectionTest extends TestCase {
   
   public void testGetGenericReturn() throws Exception {
      Type genericType1 = HasGenericReturnType.class.getDeclaredMethod("getGenericReturn1").getGenericReturnType();
      Type genericType2 = HasGenericReturnType.class.getDeclaredMethod("getGenericReturn2").getGenericReturnType();
      Type normalType = HasGenericReturnType.class.getDeclaredMethod("getNormalReturn").getGenericReturnType();
      
      System.err.println(genericType1);
      System.err.println(genericType2);
      System.err.println(normalType);
      
      System.err.println(getGenericDeclaration(genericType1));
      System.err.println(getGenericDeclaration(genericType2));
      System.err.println(getGenericDeclaration(normalType));
   }
   
   public static int getGenericDeclaration(Type type) throws Exception{
      if(type instanceof TypeVariable) {
         GenericDeclaration declaration = ((TypeVariable) type).getGenericDeclaration();
         TypeVariable[] parameters = declaration.getTypeParameters();
         
         for(int i = 0; i < parameters.length; i++) {
            if(type == parameters[i]){
               return i;
            }
         }
      }
      return -1;
   }
   
   public static class HasGenericReturnType<A, B> {
      public A getGenericReturn1(){
         return (A)null;
      }
      public B getGenericReturn2(){
         return (B)null;
      }
      public String getNormalReturn(){
         return null;
      }
   }
}
