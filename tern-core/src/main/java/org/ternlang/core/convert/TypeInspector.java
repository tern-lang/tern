package org.ternlang.core.convert;

import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import java.lang.reflect.Proxy;

import org.ternlang.core.Context;
import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.AnyConstraint;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class TypeInspector {
   
   private final Constraint any;

   public TypeInspector() {
      this.any = new AnyConstraint();
   }

   public boolean isType(Type type) {
      return type != null;
   }

   public boolean isAny(Type type) {
      if(type != null) {
         Scope scope = type.getScope();
         Type base = any.getType(scope);

         return type == base;
      }
      return false;
   }

   public boolean isClass(Type type) {
      if(type != null) {
         Class real = type.getType();

         if (real != null) {
            return true;
         }
      }
      return false; // null is valid
   }

   public boolean isProxy(Type type) {
      if(type != null) {
         Class real = type.getType();

         if (real != null) {
            return Proxy.class.isAssignableFrom(real);
         }
      }
      return false; // null is valid
   }

   public boolean isArray(Type type) throws Exception {
      if(type != null) {
         int modifiers = type.getModifiers();

         if (ModifierType.isArray(modifiers)) {
            return true;
         }
      }
      return false;
   }
   
   public boolean isFunction(Type type) throws Exception {
      if(type != null) {
         int modifiers = type.getModifiers();

         if (ModifierType.isFunction(modifiers)) {
            return true;
         }
      }
      return false;
   }   

   public boolean isSame(Type type, Class require) throws Exception {
      if(type != null) {
         Class actual = type.getType();

         if (actual == require) {
            return true;
         }
      }
      return false;
   }
   
   public boolean isConstructor(Type type, Function function) {
      if(function != null) {
         Type source = function.getSource();
         String name = function.getName();

         if (name.equals(TYPE_CONSTRUCTOR)) {
            return source == type;
         }
      }
      return false;
   }
   
   public boolean isSuperConstructor(Type type, Function function) {
      if(function != null) {
         Type source = function.getSource();
         String name = function.getName();

         if (name.equals(TYPE_CONSTRUCTOR)) {
            return source != type;
         }
      }
      return false;
   }
   
   public boolean isCompatible(Type type, Object value) throws Exception {
      if(type != null) {
         Module module = type.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(value);
         
         return score.isValid();
      }
      return true;
   }
}