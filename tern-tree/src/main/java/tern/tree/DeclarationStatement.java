package tern.tree;

import static tern.core.result.Result.NORMAL;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Execution;
import tern.core.ModifierType;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceStatement;

public class DeclarationStatement implements Compilation {
   
   private final Statement declaration;   
   
   public DeclarationStatement(Modifier modifier, Declaration... declarations) {
      this.declaration = new CompileResult(modifier, declarations);     
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, declaration, trace);
   }
   
   private static class CompileResult extends Statement {

      private final Declaration[] declarations;
      private final Modifier modifier;
      
      public CompileResult(Modifier modifier, Declaration... declarations) {
         this.declarations = declarations;
         this.modifier = modifier;
      }  
      
      @Override
      public boolean define(Scope scope) throws Exception {
         ModifierType type = modifier.getType();
         
         for(Declaration declaration : declarations) {
            declaration.define(scope, type.mask); 
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         ModifierType type = modifier.getType();
         
         for(Declaration declaration : declarations) {
            declaration.compile(scope, type.mask); 
         }
         return new CompileExecution(type, declarations);
      }
   }
   
   private static class CompileExecution extends Execution {

      private final Declaration[] declarations;
      private final ModifierType modifier;
      
      public CompileExecution(ModifierType modifier, Declaration... declarations) {
         this.declarations = declarations;
         this.modifier = modifier;
      }  
      
      @Override
      public Result execute(Scope scope) throws Exception {
         for(Declaration declaration : declarations) {
            declaration.declare(scope, modifier.mask); 
         }
         return NORMAL;
      }
   }
}