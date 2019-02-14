package org.ternlang.core;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public class IdentityEvaluation extends Evaluation {
   
   private final Constraint type;
   private final Object value;
   
   public IdentityEvaluation(Object value) {
      this(value, NONE);      
   }
   
   public IdentityEvaluation(Object value, Constraint type) {
      this.value = value;
      this.type = type;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return type;
   }

   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      return Value.getTransient(value, type);
   }
}