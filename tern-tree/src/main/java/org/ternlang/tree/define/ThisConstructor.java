package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.ENUM;
import static org.ternlang.core.ModifierType.MODULE;
import static org.ternlang.core.ModifierType.TRAIT;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Execution;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.StaticConstraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.ArgumentList;
import org.ternlang.tree.construct.CreateObject;

public class ThisConstructor implements Compilation {

   private final ArgumentList arguments;
   
   public ThisConstructor(StringToken token) {
      this(token, null);
   }
   
   public ThisConstructor(ArgumentList arguments) {
      this(null, arguments);
   }
   
   public ThisConstructor(StringToken token, ArgumentList arguments) {
      this.arguments = arguments;
   }
   
   @Override
   public TypePart compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      FunctionResolver resolver = context.getResolver();
      
      return new CompileResult(resolver, handler, arguments);
   }
   
   private static class CompileResult extends TypePart {
   
      private final FunctionResolver resolver;
      private final ArgumentList arguments;
      private final ErrorHandler handler;
      
      public CompileResult(FunctionResolver resolver, ErrorHandler handler, ArgumentList arguments) {
         this.arguments = arguments;
         this.resolver = resolver;
         this.handler = handler;
      }
      
      @Override
      public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {  
         Execution execution = new StaticBody(body, type);
         Constraint constraint = new StaticConstraint(type);
         CreateObject evaluation = new CreateObject(resolver, handler, constraint, arguments, TRAIT.mask | ENUM.mask | MODULE.mask);
         
         return new ThisState(execution, evaluation);
      }   
   }
}