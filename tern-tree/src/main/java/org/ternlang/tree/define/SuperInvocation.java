package org.ternlang.tree.define;

import static org.ternlang.core.scope.extract.ScopePolicy.EXECUTE_SUPER;
import static org.ternlang.core.variable.Value.NULL;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.StaticConstraint;
import org.ternlang.core.function.Connection;
import org.ternlang.core.function.dispatch.FunctionDispatcher;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.extract.ScopePolicyExtractor;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.ArgumentList;
import org.ternlang.tree.construct.ConstructArgumentList;

public class SuperInvocation extends Evaluation {

   private final ConstructArgumentList arguments;
   private final SuperInstanceBuilder builder;
   private final ScopePolicyExtractor extractor;
   private final SuperFunctionMatcher matcher;
   private final Constraint constraint;
   private final Type type;
   
   public SuperInvocation(ArgumentList arguments, Type type) {
      this.arguments = new ConstructArgumentList(arguments);
      this.extractor = new ScopePolicyExtractor(EXECUTE_SUPER);
      this.matcher = new SuperFunctionMatcher(type);
      this.constraint = new StaticConstraint(type);
      this.builder = new SuperInstanceBuilder(type);
      this.type = type;
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Scope outer = scope.getScope();
      FunctionDispatcher dispatcher = matcher.match(scope, constraint);  
      Scope compound = extractor.extract(scope, outer);
      Type[] list = arguments.compile(compound, type); // arguments have no left hand side

      return dispatcher.compile(scope, constraint, list);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Type real = scope.getType();  
      Scope outer = real.getScope();
      Scope instance = builder.create(scope, left);
      Scope compound = extractor.extract(scope, outer);
      Value value = Value.getTransient(instance);
      Object[] list = arguments.create(compound, real); // arguments have no left hand side
      FunctionDispatcher dispatcher = matcher.match(instance, NULL);  
      Connection connection = dispatcher.connect(instance, value, list);
      Object result = connection.invoke(instance, value, list);
      
      return Value.getTransient(result);
   }
}