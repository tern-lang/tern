package tern.core.function;

import java.util.List;

import tern.core.Context;
import tern.core.constraint.Constraint;
import tern.core.convert.ConstraintConverter;
import tern.core.convert.ConstraintMatcher;
import tern.core.convert.FixedArgumentConverter;
import tern.core.convert.NoArgumentConverter;
import tern.core.convert.VariableArgumentConverter;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;

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
