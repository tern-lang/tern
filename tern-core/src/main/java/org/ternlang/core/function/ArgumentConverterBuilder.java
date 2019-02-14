package org.ternlang.core.function;

import java.util.List;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.ConstraintConverter;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.FixedArgumentConverter;
import org.ternlang.core.convert.NoArgumentConverter;
import org.ternlang.core.convert.VariableArgumentConverter;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class ArgumentConverterBuilder {

   public ArgumentConverterBuilder() {
      super();
   }

   public ArgumentConverter create(Scope scope, List<Parameter> parameters) throws Exception {
      int size = parameters.size();

      if(size > 0) {
         Parameter parameter = parameters.get(size - 1);
         boolean variable = parameter.isVariable();

         return create(scope, parameters, variable);
      }
      return create(scope, parameters, false);
   }

   public ArgumentConverter create(Scope scope, List<Parameter> parameters, boolean variable) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();
      int size = parameters.size();

      if(size > 0) {
         ConstraintConverter[] converters = new ConstraintConverter[size];

         for(int i = 0; i < size - 1; i++) {
            Parameter parameter = parameters.get(i);
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);

            converters[i] = matcher.match(type);
         }
         Parameter parameter = parameters.get(size - 1);
         Constraint constraint = parameter.getConstraint();
         Type type = constraint.getType(scope);

         if(type != null) {
            Type entry = type.getEntry();

            if(variable) {
               converters[size - 1] = matcher.match(entry);
            } else {
               converters[size - 1] = matcher.match(type);
            }
         } else {
            converters[size - 1] = matcher.match(type);
         }
         if(variable) {
            return new VariableArgumentConverter(converters);
         }
         return new FixedArgumentConverter(converters);
      }
      return new NoArgumentConverter();
   }
}
