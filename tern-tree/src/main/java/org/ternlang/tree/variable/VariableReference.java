package org.ternlang.tree.variable;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public class VariableReference implements Compilation {
   
   private final Evaluation[] evaluations;
   private final Evaluation variable;
   
   public VariableReference(Evaluation variable, Evaluation... evaluations) {
      this.evaluations = evaluations;
      this.variable = variable;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      if(evaluations.length > 0) {
         return new CompileResult(variable, evaluations);
      }
      return variable;
   }
   
   private static class CompileResult extends Evaluation {
   
      private final Evaluation[] evaluations;
      private final Evaluation variable;
      
      public CompileResult(Evaluation variable, Evaluation... evaluations) {
         this.evaluations = evaluations;
         this.variable = variable;
      }
      
      @Override
      public void define(Scope scope) throws Exception{
         variable.define(scope);
         
         for(Evaluation evaluation : evaluations) {
            evaluation.define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception{
         Constraint result = variable.compile(scope, left);
         
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of evaluation is null"); 
            }
            result = evaluation.compile(scope, result);
         }
         return result;
      } 
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception{
         Value value = variable.evaluate(scope, left);
         
         for(Evaluation evaluation : evaluations) {
            Object result = value.getValue();
            
            if(result == null) {
               throw new InternalStateException("Result of evaluation is null"); 
            }
            value = evaluation.evaluate(scope, value);
         }
         return value; 
      } 
   }
}