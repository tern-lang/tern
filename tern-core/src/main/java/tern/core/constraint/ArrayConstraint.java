package tern.core.constraint;

import tern.core.Context;
import tern.core.ModifierType;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeLoader;

public class ArrayConstraint extends Constraint {
   
   private final Constraint entry;
   private final String name;
   private final int modifiers;
   private final int bounds;
   
   public ArrayConstraint(Constraint entry, String name, int bounds) {
      this(entry, name, bounds, 0);
   }
   
   public ArrayConstraint(Constraint entry, String name, int bounds, int modifiers) {
      this.modifiers = modifiers;
      this.bounds = bounds;
      this.entry = entry;
      this.name = name;
   }

   @Override
   public Type getType(Scope scope) {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeLoader loader = context.getLoader();
      Type type = entry.getType(scope);
      
      if(type != null) {
         Module parent = type.getModule();
         String prefix = parent.getName();
         String name = type.getName();
         
         return loader.loadArrayType(prefix, name, bounds);
      }
      return loader.loadType(Object[].class);
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
   public String toString() {
      return entry + "[]";
   }
}
