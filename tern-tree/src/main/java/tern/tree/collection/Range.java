package tern.tree.collection;

import static tern.core.constraint.Constraint.ITERABLE;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.parse.StringToken;

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