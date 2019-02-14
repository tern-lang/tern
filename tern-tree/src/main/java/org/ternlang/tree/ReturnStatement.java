package org.ternlang.tree;

import static org.ternlang.core.result.Result.RETURN;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.AliasResolver;
import org.ternlang.core.convert.ConstraintConverter;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.Score;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceStatement;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

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