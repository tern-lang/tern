package tern.tree.define;

import static tern.core.type.Category.OTHER;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.type.Category;
import tern.core.type.Type;
import tern.core.type.TypeState;
import tern.core.variable.Value;
import tern.tree.ArgumentList;

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