package org.ternlang.tree;

import org.ternlang.core.Evaluation;
import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.DeclarationConstraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Local;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class DeclarationAllocator {

   private final DeclarationConstraint constraint;
   private final DeclarationConverter converter;
   private final Evaluation expression;
   
   public DeclarationAllocator(Constraint constraint, Evaluation expression) {      
      this.constraint = new DeclarationConstraint(constraint);
      this.converter = new DeclarationConverter();
      this.expression = expression;
   }   
   
   public <T extends Value> T compile(Scope scope, String name, int modifiers) throws Exception {
      Type type = constraint.getType(scope);
      Constraint declare = constraint.getConstraint(scope, modifiers);
      
      if(expression != null) {
         Constraint object = expression.compile(scope, null);
         Type real = object.getType(scope);
         
         if(real != null) {
            object = converter.compile(scope, real, constraint, name);        
         }
         return assign(scope, name, null, declare, modifiers);
      }
      return declare(scope, name, declare, modifiers); // nothing assigned yet
   }
   
   
   public <T extends Value> T allocate(Scope scope, String name, int modifiers) throws Exception {
      Type type = constraint.getType(scope);
      Constraint declare = constraint.getConstraint(scope, modifiers);
      Object object = null;
      
      if(expression != null) {
         Value value = expression.evaluate(scope, null);
         Object original = value.getValue();
         
         if(type != null) {
            object = converter.convert(scope, original, constraint, name);
         } else {
            object = original;
         }
         return assign(scope, name, object, declare, modifiers);
      }
      return declare(scope, name, declare, modifiers);
   }   
   
   protected <T extends Value> T declare(Scope scope, String name, Constraint type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return (T)Local.getConstant(null, name, type);
      }
      return (T)Local.getReference(null, name, type);
   }
   
   protected <T extends Value> T assign(Scope scope, String name, Object value, Constraint type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return (T)Local.getConstant(value, name, type);
      }
      return (T)Local.getReference(value, name, type);
   }
}