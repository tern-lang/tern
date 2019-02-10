package tern.core.convert;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.constraint.transform.ConstraintRule;
import tern.core.constraint.transform.ConstraintTransform;
import tern.core.constraint.transform.ConstraintTransformer;
import tern.core.function.Function;
import tern.core.function.Parameter;
import tern.core.function.Signature;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class FunctionOverrideMatcher {

   private final ConstraintTransformer transformer;
   private final FunctionComparator comparator;
   private final Type[] empty;

   public FunctionOverrideMatcher(ConstraintMatcher matcher, ConstraintTransformer transformer) {
      this.comparator = new FunctionComparator(matcher);
      this.empty = new Type[] {};
      this.transformer = transformer;
   }

   public Type[] matchTypes(Scope scope, Function function, Type source) throws Exception {
      Constraint constraint = Constraint.getConstraint(source);
      ConstraintTransform transform = transformer.transform(source, function);
      ConstraintRule rule = transform.apply(constraint);
      List<Parameter> parameters = rule.getParameters(scope, function);
      int length = parameters.size();

      if(length > 0) {
         Type[] types = new Type[length];

         for(int i = 0; i < length; i++){
            Parameter parameter = parameters.get(i);
            Constraint actual = parameter.getConstraint();
            Type type = actual.getType(scope);

            types[i] = type;
         }
         return types;
      }
      return empty;
   }

   public Type[] matchTypes(Scope scope, Function override, Function function) throws Exception {
      Type source = override.getSource();
      Constraint constraint = Constraint.getConstraint(source);
      ConstraintTransform transform = transformer.transform(source, function);
      ConstraintRule rule = transform.apply(constraint);
      Signature signature = override.getSignature();
      List<Parameter> left = signature.getParameters();
      List<Parameter> right = rule.getParameters(scope, function);
      Score score = comparator.compare(scope, left, right);
      boolean exact = score.isExact();
      int length = left.size();

      if(exact) {
         Type[] types = new Type[length];

         for(int i = 0; i < length; i++){
            Parameter parameter = right.get(i);
            Constraint actual = parameter.getConstraint();
            Type type = actual.getType(scope);

            types[i] = type;
         }
         return types;
      }
      return null;
   }
}
