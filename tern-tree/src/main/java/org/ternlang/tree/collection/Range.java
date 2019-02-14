package org.ternlang.tree.collection;

import static org.ternlang.core.constraint.Constraint.ITERABLE;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

public class Range extends Evaluation {

   private final RangeOperator operator;
   private final StringToken token;
   private final Evaluation start;
   private final Evaluation finish;
   
   public Range(Evaluation start, StringToken token, Evaluation finish) {
      this.operator = RangeOperator.resolveOperator(token);
      this.token = token;
      this.start = start;
      this.finish = finish;
   }

   @Override
   public void define(Scope scope) throws Exception {
      start.define(scope); // compile for stack reference
      finish.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      if(operator == null) {
         throw new InternalStateException("Illegal " + token + " operator for range");
      }
      return ITERABLE;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Iterable<Number> range = create(scope, left);
      return Value.getTransient(range);
   }
   
   private Sequence create(Scope scope, Value left) throws Exception {
      Value first = start.evaluate(scope, left);
      Value last = finish.evaluate(scope, left);
      long start = first.getLong();
      long finish = last.getLong();

      return new Sequence(start, finish, operator.forward);
   }
}