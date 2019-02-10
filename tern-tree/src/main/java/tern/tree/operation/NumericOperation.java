package tern.tree.operation;

import tern.core.Evaluation;
import tern.core.convert.AliasResolver;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.parse.Token;
import tern.tree.math.NumericChecker;

public abstract class NumericOperation extends Evaluation {

   protected final AliasResolver resolver;
   protected final Evaluation evaluation;
   protected final Token operator;
   
   protected NumericOperation(Evaluation evaluation, Token operator) {
      this.resolver = new AliasResolver();
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

         if(!NumericChecker.isNumeric(real)) {
            throw new InternalStateException("Illegal " + operator +" of type '" + type + "'");
         }
      }
      return constraint;      
   }
}