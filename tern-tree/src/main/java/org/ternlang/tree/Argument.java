package org.ternlang.tree;

import org.ternlang.core.Evaluation;
import org.ternlang.core.Expansion;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.TypeParameterConstraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.literal.TextLiteral;

public class Argument extends Evaluation{
   
   private final TextLiteral identifier;
   private final Evaluation evaluation;
   
   public Argument(Evaluation evaluation){
      this(null, evaluation);
   }

   public Argument(TextLiteral identifier, Evaluation evaluation){
      this.identifier = identifier;
      this.evaluation = evaluation;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }

   @Override
   public Expansion expansion(Scope scope) throws Exception {
      return evaluation.expansion(scope);
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Constraint constraint = evaluation.compile(scope, left);
      Type type = constraint.getType(scope);
      
      if(identifier != null) {
         Value value = identifier.evaluate(scope, null);
         String name = value.getValue();
         
         return new TypeParameterConstraint(type, name);
      }
      return new TypeParameterConstraint(type, null);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      return evaluation.evaluate(scope, left);
   }
}