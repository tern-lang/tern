package tern.core.constraint;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import tern.core.Context;
import tern.core.ModifierType;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeLoader;

public class ClassParameterConstraint extends Constraint {
   
   private final List<Constraint> generics;
   private final Class require;
   private final String name;
   private final int modifiers;
   
   public ClassParameterConstraint(Class require, String name) {
      this(require, EMPTY_LIST, name);
   }
   
   public ClassParameterConstraint(Class require, String name, int modifiers) {
      this(require, EMPTY_LIST, name, modifiers);
   }
   
   public ClassParameterConstraint(Class require, List<Constraint> generics, String name) {
      this(require, generics, name, 0);
   }
   
   public ClassParameterConstraint(Class require, List<Constraint> generics, String name, int modifiers) {
      this.modifiers = modifiers;
      this.generics = generics;
      this.require = require;
      this.name = name;
   }

   @Override
   public Type getType(Scope scope) {
      if(require != null) {
         Module module = scope.getModule();
         Context context = module.getContext();               
         TypeLoader loader = context.getLoader();
         
         return loader.loadType(require);
      }
      return null;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      return generics;
   }
   
   @Override
   public String getName(Scope scope) {
      return name;
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
