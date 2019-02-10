package tern.tree.reference;

import static tern.core.constraint.Constraint.NONE;
import static tern.tree.reference.ReferenceOperator.FORCE;

import tern.core.Compilation;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class ReferenceNavigation implements Compilation {
   
   private final StringToken operator;
   private final Evaluation part;
   private final Evaluation next;

   public ReferenceNavigation(Evaluation part) {
      this(part, null, null);
   }
   
   public ReferenceNavigation(Evaluation part, StringToken operator, Evaluation next) {
      this.operator = operator;
      this.part = part;
      this.next = next;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      if(next != null) {
         return new CompileResult(part, operator, next);
      }
      return part;
   }
   
   private static class CompileResult extends Evaluation {
   
      private final ReferenceOperator operator;
      private final Evaluation part;
      private final Evaluation next;
      
      public CompileResult(Evaluation part, StringToken operator, Evaluation next) {
         this.operator = ReferenceOperator.resolveOperator(operator);
         this.part = part;
         this.next = next;
      }
      
      @Override
      public void define(Scope scope) throws Exception {
         next.define(scope);
         part.define(scope);
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         Constraint value = part.compile(scope, left);         
         
         if(operator == FORCE) {
            return next.compile(scope, NONE);
         }
         if(value != null) {
            return next.compile(scope, value);
         }
         return value;
      } 
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         Value value = part.evaluate(scope, left);         
         
         if(operator != null) {
            return operator.operate(scope, next, value);
         }
         return value;
      }   
   }
}