package org.ternlang.tree.define;

import static org.ternlang.core.type.Category.OTHER;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Category;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.ArgumentList;

public class SuperState extends TypeState {
   
   private final SuperInstanceBuilder builder;
   private final Evaluation expression;
   
   public SuperState(ArgumentList arguments, Type type) {
      this.expression = new SuperInvocation(arguments, type);
      this.builder = new SuperInstanceBuilder(type);
   }
   
   @Override
   public Category define(Scope instance, Type real) throws Exception {
      expression.define(instance);
      return OTHER;
   }
   
   @Override
   public void compile(Scope instance, Type real) throws Exception {
      Constraint constraint = Constraint.getConstraint(real);
      expression.compile(instance, constraint);
   }

   @Override
   public Result execute(Scope instance, Type real) throws Exception {
      Value value = Value.getTransient(real);
      Value reference = expression.evaluate(instance, value); 
      Scope scope = reference.getValue();
      Scope base = builder.create(scope, value);
      
      return Result.getNormal(base);
   }
}