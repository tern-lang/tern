package org.ternlang.core.constraint;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.ternlang.core.Context;
import org.ternlang.core.ModifierType;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeLoader;

public class ClassConstraint extends Constraint {

   private final List<Constraint> generics;
   private final Class require;
   private final int modifiers;
   
   public ClassConstraint(Class require) {
      this(require, EMPTY_LIST);
   }
   
   public ClassConstraint(Class require, int modifiers) {
      this(require, EMPTY_LIST, modifiers);
   }
   
   public ClassConstraint(Class require, List<Constraint> generics) {
      this(require, generics, 0);
   }
   
   public ClassConstraint(Class require, List<Constraint> generics, int modifiers) {
      this.modifiers = modifiers;
      this.generics = generics;
      this.require = require;

   }
   
   @Override
   public Type getType(Scope scope){
      if(require != null) {
         Module module = scope.getModule();
         Context context = module.getContext();               
         TypeLoader loader = context.getLoader();
         
         return loader.loadType(require);
      }
      return null;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope){
      return generics;
   }
   
   @Override
   public boolean isVariable(){
      return !ModifierType.isConstant(modifiers);
   }
   
   @Override
   public boolean isConstant(){
      return ModifierType.isConstant(modifiers);
   }
   
   @Override
   public boolean isClass(){
      return ModifierType.isClass(modifiers);
   }
   
   @Override
   public String toString(){
      return String.valueOf(require);
   }
}