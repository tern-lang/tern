package org.ternlang.tree.define;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.DeclarationConstraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Accessor;
import org.ternlang.core.function.AccessorProperty;
import org.ternlang.core.function.ModuleAccessor;
import org.ternlang.core.module.Module;
import org.ternlang.core.property.Property;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.DeclarationAllocator;
import org.ternlang.tree.NameReference;
import org.ternlang.tree.literal.TextLiteral;

public class ModuleProperty {
   
   private final DeclarationAllocator allocator;
   private final DeclarationConstraint constraint;
   private final NameReference reference;
   private final Evaluation value;
   
   public ModuleProperty(TextLiteral identifier) {
      this(identifier, null, null);
   }
   
   public ModuleProperty(TextLiteral identifier, Constraint constraint) {      
      this(identifier, constraint, null);
   }
   
   public ModuleProperty(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }
   
   public ModuleProperty(TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.allocator = new ModulePropertyAllocator(constraint, value);
      this.constraint = new DeclarationConstraint(constraint);
      this.reference = new NameReference(identifier);
      this.value = value;
   }  
   
   public Property define(ModuleBody body, Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Accessor accessor = define(body, scope);
      Constraint require = constraint.getConstraint(scope, modifiers);
      
      return new AccessorProperty(name, name, null, require, accessor, modifiers);
   }

   public Value compile(ModuleBody body, Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Value value = allocator.compile(scope, name, modifiers);
      ScopeState state = scope.getState();
      
      try {
         state.addValue(name, value);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }
      return value;
   }
   
   public Value execute(ModuleBody body, Scope scope, int modifiers) throws Exception {      
      String name = reference.getName(scope);
      Value value = allocator.allocate(scope, name, modifiers);
      ScopeState state = scope.getState();
     
      try {
         state.addValue(name, value);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      } 
      return value;
   }
   
   private Accessor define(ModuleBody body, Scope scope) throws Exception {
      Module module = scope.getModule();
      String name = reference.getName(scope);

      if(value != null) {
         value.define(scope);
      }
      return new ModuleAccessor(module, body, scope, name);
   }
}