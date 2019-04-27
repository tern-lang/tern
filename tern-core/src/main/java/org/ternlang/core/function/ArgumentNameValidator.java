package org.ternlang.core.function;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

public class ArgumentNameValidator {
   
   public ArgumentNameValidator() {
      super();
   }
   
   public boolean isValid(Scope scope, Function function, Constraint... arguments) throws Exception {
      if(arguments.length > 0) {
         Signature signature = function.getSignature();
         Origin origin = signature.getOrigin();
         
         if(origin.isDefault()) {
            List<Parameter> parameters = signature.getParameters();
            int length = parameters.size();
            
            for(int i = 0; i < length; i++) {
               Constraint type = arguments[i];
               Parameter parameter = parameters.get(i);
               String name = type.getName(scope);
               
               if(name != null) {
                  String actual = parameter.getName();
                  
                  if(!name.equals(actual)) {
                     return false;
                  }
               }
            }
         }
      }
      return true;
   }
}
