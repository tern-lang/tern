package tern.tree;

import static tern.core.result.Result.RETURN;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.Execution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.convert.AliasResolver;
import tern.core.convert.ConstraintConverter;
import tern.core.convert.ConstraintMatcher;
import tern.core.convert.Score;
import tern.core.error.ErrorHandler;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceStatement;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class ReturnStatement implements Compilation {
   
   private final Evaluation evaluation;
   
   public ReturnStatement(StringToken token){
      this(null, token);
   }
   
   public ReturnStatement(Evaluation evaluation){
      this(evaluation, null);
   }
   
   public ReturnStatement(Evaluation evaluation, StringToken token){
      this.evaluation = evaluation;
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      Statement statement = create(module, path, line);
      
      return new TraceStatement(interceptor, handler, statement, trace);
   }   
   
   private Statement create(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      ConstraintMatcher matcher = context.getMatcher();
      
      return new CompileResult(matcher, handler, evaluation);
   }
   
   private static class CompileResult extends Statement {

      private final ConstraintMatcher matcher;
      private final AliasResolver resolver;
      private final ErrorHandler handler;
      private final Evaluation evaluation;

      public CompileResult(ConstraintMatcher matcher, ErrorHandler handler, Evaluation evaluation){
         this.resolver = new AliasResolver();
         this.evaluation = evaluation;
         this.matcher = matcher;
         this.handler = handler;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         if(evaluation != null) {
            evaluation.define(scope);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         if(evaluation != null) {
            Constraint constraint = evaluation.compile(scope, null);
            
            if(returns != null) {
               Type require = returns.getType(scope);
               Type actual = constraint.getType(scope);
               
               if(require != null) { // definite constraint
                  ConstraintConverter converter = matcher.match(require);
                  Score score = converter.score(actual);
                  
                  if(score.isInvalid()) {
                     handler.failCompileCast(scope, require, actual);
                  }                  
               }
            }                       
         }
         return new CompileExecution(evaluation);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final Evaluation evaluation;

      public CompileExecution(Evaluation evaluation){
         this.evaluation = evaluation;
      }

      @Override
      public Result execute(Scope scope) throws Exception {
         if(evaluation != null) {
            Value value = evaluation.evaluate(scope, null);
            Object object = value.getValue();
            
            return Result.getReturn(object);
         }
         return RETURN;
      }
   }
}