package org.ternlang.core.attribute;

import java.util.List;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.transform.ConstraintRule;
import org.ternlang.core.constraint.transform.ConstraintTransform;
import org.ternlang.core.constraint.transform.ConstraintTransformer;
import org.ternlang.core.convert.Score;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.ArgumentConverter;
import org.ternlang.core.function.ArgumentConverterBuilder;
import org.ternlang.core.function.ArgumentListCompiler;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class GenericAttributeResult implements AttributeResult {

   private final ArgumentConverterBuilder builder;
   private final ArgumentListCompiler compiler;
   private final Attribute attribute;

   public GenericAttributeResult(Attribute attribute) {
      this.builder = new ArgumentConverterBuilder();
      this.compiler = new ArgumentListCompiler();
      this.attribute = attribute;      
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left, Constraint... arguments) throws Exception {
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
         Type[] types = compiler.compile(scope, arguments);
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