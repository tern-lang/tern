package tern.tree.define;

import static tern.core.ModifierType.ENUM;
import static tern.core.ModifierType.MODULE;
import static tern.core.ModifierType.TRAIT;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Execution;
import tern.core.constraint.Constraint;
import tern.core.constraint.StaticConstraint;
import tern.core.error.ErrorHandler;
import tern.core.function.resolve.FunctionResolver;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.type.TypePart;
import tern.core.type.TypeState;
import tern.parse.StringToken;
import tern.tree.ArgumentList;
import tern.tree.construct.CreateObject;

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