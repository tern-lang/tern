package org.ternlang.core.type.index;

import static org.ternlang.core.ModifierType.ABSTRACT;
import static org.ternlang.core.ModifierType.CONSTANT;
import static org.ternlang.core.ModifierType.PRIVATE;
import static org.ternlang.core.ModifierType.PROTECTED;
import static org.ternlang.core.ModifierType.PUBLIC;
import static org.ternlang.core.ModifierType.STATIC;
import static org.ternlang.core.ModifierType.VARARGS;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ModifierConverter {
   
   public int convert(Method method) {
      int result = PROTECTED.mask;
      
      if(method != null) {
         int modifiers = method.getModifiers();
   
         if(method.isVarArgs()) {
            result |= VARARGS.mask;
         }
         if(Modifier.isAbstract(modifiers)) {
            result |= ABSTRACT.mask;
         }
         if(Modifier.isFinal(modifiers)) {
            result |= CONSTANT.mask;
         }
         if(Modifier.isPrivate(modifiers)) {
            result |= PRIVATE.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isPublic(modifiers)) {
            result |= PUBLIC.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isStatic(modifiers)) {
            result |= STATIC.mask;
         }
      }
      return result;
   }
   
   public int convert(Constructor constructor) {
      int result = PROTECTED.mask;
      
      if(constructor != null) {
         int modifiers = constructor.getModifiers();
   
         if(constructor.isVarArgs()) {
            result |= VARARGS.mask;
         }
         if(Modifier.isAbstract(modifiers)) {
            result |= ABSTRACT.mask;
         }
         if(Modifier.isFinal(modifiers)) {
            result |= CONSTANT.mask;
         }
         if(Modifier.isPrivate(modifiers)) {
            result |= PRIVATE.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isPublic(modifiers)) {
            result |= PUBLIC.mask;
            result &= ~PROTECTED.mask;
         }
         result |= STATIC.mask;
      }
      return result;
   }
   
   public int convert(Field field) {
      int result = PROTECTED.mask; // default is protected
      
      if(field != null) {
         int modifiers = field.getModifiers();
         
         if(Modifier.isFinal(modifiers)) {
            result |= CONSTANT.mask;
         }
         if(Modifier.isPrivate(modifiers)) {
            result |= PRIVATE.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isPublic(modifiers)) {
            result |= PUBLIC.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isStatic(modifiers)) {
            result |= STATIC.mask;
         }
      }
      return result;
   }
   
   public int convert(Class type) {
      int result = PROTECTED.mask;
      
      if(type != null) {
         int modifiers = type.getModifiers();
   
         if(Modifier.isFinal(modifiers)) {
            result |= CONSTANT.mask;
         }
         if(Modifier.isPrivate(modifiers)) {
            result |= PRIVATE.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isPublic(modifiers)) {
            result |= PUBLIC.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isStatic(modifiers)) {
            result |= STATIC.mask;
         }
      }
      return result;
   }
}