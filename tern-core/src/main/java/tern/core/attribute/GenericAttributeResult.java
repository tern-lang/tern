package tern.core.attribute;

import java.util.List;

import tern.core.Context;
import tern.core.constraint.Constraint;
import tern.core.constraint.transform.ConstraintRule;
import tern.core.constraint.transform.ConstraintTransform;
import tern.core.constraint.transform.ConstraintTransformer;
import tern.core.convert.Score;
import tern.core.error.ErrorHandler;
import tern.core.function.ArgumentConverter;
import tern.core.function.ArgumentConverterBuilder;
import tern.core.function.Function;
import tern.core.function.Parameter;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class GenericAttributeResult implements AttributeResult {

   private final ArgumentConverterBuilder builder;
   private final Attribute attribute;

   public GenericAttributeResult(Attribute attribute) {
      this.builder = new ArgumentConverterBuilder();
      this.attribute = attribute;      
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left, Type... types) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();  
      Type constraint = left.getType(scope);
      Constraint returns = attribute.getConstraint();       
      ConstraintTransformer transformer = context.getTransformer();
      ConstraintTransform transform = transformer.transform(constraint, attribute);
      ConstraintRule rule = transform.apply(left);

      if(Function.class.isInstance(attribute)) {
         Function function = (Function)attribute;
         List<Parameter> parameters = rule.getParameters(scope, function);
         ArgumentConverter converter = builder.create(scope, parameters);
         ErrorHandler handler = context.getHandler();
         Score score = converter.score(types);
         String name = function.getName();

         if(score.isInvalid()) {
            handler.failCompileGenerics(scope, name, types);
         }
      }
      return rule.getResult(scope, returns);
   }
}