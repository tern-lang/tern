package tern.tree.condition;

import static tern.core.result.Result.NORMAL;
import static tern.tree.condition.RelationalOperator.EQUALS;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.Execution;
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
import tern.core.variable.Value;
import tern.core.resume.Resume;
import tern.core.resume.Yield;
import tern.tree.SuspendStatement;

public class SwitchStatement implements Compilation {
   
   private final Statement statement;
   
   public SwitchStatement(Evaluation evaluation, Case... cases) {
      this.statement = new CompileResult(evaluation, cases);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, statement, trace);
   }
   
   private static class CompileCase {
      
      private final Evaluation expression;
      private final Execution execution;
      
      public CompileCase(Evaluation expression, Execution execution) {
         this.expression = expression;
         this.execution = execution;
      }
      
      public Evaluation getEvaluation() {
         return expression;
      }
      
      public Execution getExecution(){
         return execution;
      }
   }
   
   private static class CompileResult extends Statement {

      private final Evaluation condition;
      private final Case[] cases;
      
      public CompileResult(Evaluation condition, Case... cases) {
         this.condition = condition;
         this.cases = cases;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         for(int i = 0; i < cases.length; i++){
            Statement statement = cases[i].getStatement();
            statement.define(scope);
         }
         condition.define(scope);
         return true;
      }

      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         CompileCase[] list = new CompileCase[cases.length];
         
         for(int i = 0; i < cases.length; i++){
            Evaluation evaluation = cases[i].getEvaluation();
            Statement statement = cases[i].getStatement();
            
            if(evaluation != null) {
               evaluation.compile(scope, null);
            }
            Execution execution = statement.compile(scope, returns);
            
            list[i] = new CompileCase(evaluation, execution);            
         }
         condition.compile(scope, null);
         return new CompileExecution(condition, list);
      }
   }
   
   
   private static class CompileExecution extends SuspendStatement<Integer> {

      private final Evaluation condition;
      private final CompileCase[] cases;
      
      public CompileExecution(Evaluation condition, CompileCase... cases) {
         this.condition = condition;
         this.cases = cases;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value left = condition.evaluate(scope, null);
         
         for(int i = 0; i < cases.length; i++){
            Evaluation evaluation = cases[i].getEvaluation();
            
            if(evaluation == null) {
               Execution statement = cases[i].getExecution();
               Result result = statement.execute(scope);
               
               if(result.isBreak()) {
                  return NORMAL;
               }
               if(!result.isNormal()) {
                  return result;      
               }
               return NORMAL;
            }
            Value right = evaluation.evaluate(scope, null);
            Value value = EQUALS.operate(scope, left, right);
            Object result = value.getValue();
            
            if(BooleanChecker.isTrue(result)) {
               return resume(scope, i);
            }  
         }
         return NORMAL;
      }
      
      @Override
      public Result resume(Scope scope, Integer index) throws Exception {
         for(int j = index; j < cases.length; j++) {
            Execution statement = cases[j].getExecution();
            Result result = statement.execute(scope);

            if(result.isYield()) {
               return suspend(scope, result, this, j);
            }
            if(result.isBreak()) {
               return NORMAL;
            }
            if(!result.isNormal()) {
               return result;      
            }
         }   
         return NORMAL;
      }

      @Override
      public Resume suspend(Result result, Resume resume, Integer value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new SwitchResume(child, resume, value);
      }
   }
}