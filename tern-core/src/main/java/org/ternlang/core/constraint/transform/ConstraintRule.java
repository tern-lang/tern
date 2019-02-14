package org.ternlang.core.constraint.transform;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.ParameterBuilder;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;

public abstract class ConstraintRule {

   private final ParameterBuilder builder;

   protected ConstraintRule() {
      this.builder = new ParameterBuilder();
   }

   public List<Parameter> getParameters(Scope scope, Function function) {
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      int count = parameters.size();

      if(count > 0) {
         List<Parameter> changed = new ArrayList<Parameter>(count);

         for(int i = 0; i < count; i++) {
            Parameter parameter = parameters.get(i);
            String name = parameter.getName();
            Constraint constraint = parameter.getConstraint();
            Constraint change = getResult(scope, constraint);
            boolean variable = constraint.isVariable();

            if(change != constraint) {
               parameter = builder.create(change, name, i, variable);
            }
            changed.add(parameter);
         }
         return changed;
      }
      return parameters;
   }

   public abstract Constraint getResult(Scope scope, Constraint returns);
   public abstract Constraint getSource();
}
