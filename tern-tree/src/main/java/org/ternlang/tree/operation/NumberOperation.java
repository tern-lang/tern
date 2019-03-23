package org.ternlang.tree.operation;

import org.ternlang.core.Evaluation;
import org.ternlang.core.convert.AliasResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.parse.Token;
import org.ternlang.tree.math.NumberChecker;

public abstract class NumberOperation extends Evaluation {

   protected final NumberChecker checker;
   protected final AliasResolver resolver;
   protected final Evaluation evaluation;
   protected final Token operator;
   
   protected NumberOperation(Evaluation evaluation, Token operator) {
      this.resolver = new AliasResolver();
      this.checker = new NumberChecker();
      this.evaluation = evaluation;
      this.operator = operator;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Constraint constraint = evaluation.compile(scope, left);
      Type type = constraint.getType(scope);
      
      if(constraint.isConstant()) {
         throw new InternalStateException("Illegal " + operator+ " of constant");
      }
      if(type != null) {
         Type real = resolver.resolve(type);

         if(!checker.isNumeric(real)) {
            throw new InternalStateException("Illegal " + operator +" of type '" + type + "'");
         }
      }
      return constraint;      
   }
}