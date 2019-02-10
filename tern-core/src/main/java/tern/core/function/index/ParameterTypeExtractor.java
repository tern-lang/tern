package tern.core.function.index;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.function.Parameter;
import tern.core.function.Signature;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class ParameterTypeExtractor {

   private final Type[] empty;

   public ParameterTypeExtractor() {
      this.empty = new Type[]{};
   }

   public Type[] extract(FunctionPointer pointer) {
      Function function = pointer.getFunction();
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      Type source = function.getSource();
      int size = parameters.size();

      if(source != null) {
         Type[] list = new Type[size];

         for (int i = 0; i < size; i++) {
            Scope scope = source.getScope();
            Parameter parameter = parameters.get(i);
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);

            if (type != null) {
               list[i] = type;
            }
         }
         return list;
      }
      return empty;
   }
}
