package org.ternlang.core.function;

import java.util.List;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.ConstraintConverter;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.FixedArgumentConverter;
import org.ternlang.core.convert.IdentityArgumentConverter;
import org.ternlang.core.convert.NullConverter;
import org.ternlang.core.convert.VariableArgumentConverter;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class ArgumentConverterBuilder {
   
   private final ConstraintConverter converter;
   private final ArgumentConverter none;

   public ArgumentConverterBuilder() {
      this.none = new IdentityArgumentConverter();
      this.converter = new NullConverter();
   }

   public ArgumentConverter create(Scope scope, List<Parameter> parameters) throws Exception {
      int size = parameters.size();

      if(size > 0) {
         Parameter last = parameters.get(size - 1);
         boolean variable = last.isVariable();
         
         return create(scope, parameters, variable);
      }
      return none;
   }
   
   public ArgumentConverter create(Scope scope, List<Parameter> parameters, boolean variable) throws Exception {
      int size = parameters.size();

      if(size > 0) {
         for(int i = 0; i < size; i++) {
            Parameter parameter = parameters.get(i);
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);
         
            if(type != null) {
               return create(scope, parameters, variable, size);
            }
         }
         if(variable) {
            return create(scope, parameters, variable, size);
         }
      }
      return new IdentityArgumentConverter(size);
   }
   
   private ArgumentConverter create(Scope scope, List<Parameter> parameters, boolean variable, int size) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();
      
      if(size > 0) {
         ConstraintConverter[] converters = new ConstraintConverter[size];
   
         for(int i = 0; i < size - 1; i++) {
            Parameter parameter = parameters.get(i);
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);
   
            if(type != null) {
               converters[i] = matcher.match(type);
            } else {
               converters[i] = converter;
            }
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
            converters[size - 1] = converter;
         }
         if(variable) {
            return new VariableArgumentConverter(converters);
         }
         return new FixedArgumentConverter(converters);
      }
      return none;
   }
}

