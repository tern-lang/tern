package org.ternlang.core.attribute;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.ArgumentListCompiler;
import org.ternlang.core.function.ArgumentNameValidator;
import org.ternlang.core.function.Function;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class StaticAttributeResult implements AttributeResult {
   
   private final ArgumentNameValidator validator;
   private final ArgumentListCompiler compiler;
   private final Attribute attribute;

   public StaticAttributeResult(Attribute attribute) {
      this.validator = new ArgumentNameValidator();
      this.compiler = new ArgumentListCompiler();
      this.attribute = attribute;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left, Constraint... arguments) throws Exception {
      if(arguments.length > 0) {
         if(Function.class.isInstance(attribute)) {
            Function function = (Function)attribute;
            
            if(!validator.isValid(scope, function, arguments)) {
               Type[] types = compiler.compile(scope, arguments);
               Module module = scope.getModule();
               Context context = module.getContext();  
               ErrorHandler handler = context.getHandler();
               String name = function.getName();
               
               handler.failCompileArguments(scope, name, types);
            }
         }
      }
      return attribute.getConstraint();
   }
}