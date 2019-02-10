package tern.tree.define;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.constraint.DeclarationConstraint;
import tern.core.error.InternalStateException;
import tern.core.function.Accessor;
import tern.core.function.AccessorProperty;
import tern.core.function.ModuleAccessor;
import tern.core.module.Module;
import tern.core.property.Property;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.variable.Value;
import tern.tree.DeclarationAllocator;
import tern.tree.NameReference;
import tern.tree.literal.TextLiteral;

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