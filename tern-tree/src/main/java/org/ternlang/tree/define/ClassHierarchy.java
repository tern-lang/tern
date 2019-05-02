package org.ternlang.tree.define;

import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.AnyConstraint;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.ConstraintVerifier;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.constraint.ClassConstraint;
import org.ternlang.tree.constraint.TraitConstraint;

public class ClassHierarchy implements TypeHierarchy {
      
   private final TypeHierarchyCompiler collector;
   private final ConstraintVerifier verifier;
   private final TraitConstraint[] traits; 
   private final ClassConstraint base;
   private final Constraint any;
   
   public ClassHierarchy(TraitConstraint... traits) {
      this(null, traits);     
   }
   
   public ClassHierarchy(ClassConstraint base, TraitConstraint... traits) {
      this.collector = new TypeHierarchyCompiler();
      this.verifier = new ConstraintVerifier();
      this.any = new AnyConstraint();
      this.traits = traits;
      this.base = base;
   }
   
   @Override
   public void create(Scope scope, Type type) throws Exception {
      List<Constraint> types = type.getTypes();
      int modifiers = type.getModifiers();
      
      if(base == null) {
         if(!ModifierType.isAlias(modifiers)) {
            types.add(any);
         }
      } else {
         if(ModifierType.isEnum(modifiers)) {
            throw new InternalStateException("Invalid super class for enum '" + type + "'");
         }
      }
   }

   @Override
   public void define(Scope scope, Type type) throws Exception {
      List<Constraint> types = type.getTypes();
      
      if(base != null) {
         Type match = base.getType(scope);
         
         if(match == null) {
            throw new InternalStateException("Invalid super class for type '" + type + "'");
         }
         int modifiers = match.getModifiers();
         
         if(!ModifierType.isClass(modifiers)) {
            throw new InternalStateException("Invalid super class '" + match + "' for type '" + type + "'");
         }
         types.add(base);  
      }
      for(int i = 0; i < traits.length; i++) {
         Constraint trait = traits[i];
         Type match = trait.getType(scope);
         
         if(match == null) {
            throw new InternalStateException("Invalid trait for type '" + type + "'");
         }
         int modifiers = match.getModifiers();
         
         if(!ModifierType.isTrait(modifiers)) {
            throw new InternalStateException("Invalid trait '" + match + "' for type '" + type + "'");
         }
         types.add(trait);
      }
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      List<Constraint> types = type.getTypes();
      
      if(base != null) {
         Type match = base.getType(scope);
         
         if(match == null) {
            throw new InternalStateException("Invalid super class for type '" + type + "'");
         }
         collector.compile(type, match);
      }
      for(Constraint base : types) {
         try {
            verifier.verify(scope, base);
         } catch(Exception e) {
            throw new InternalStateException("Invalid super class for type '" + type + "'", e); 
         }
      }
   }
}