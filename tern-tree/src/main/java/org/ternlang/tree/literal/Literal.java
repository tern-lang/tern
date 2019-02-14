package org.ternlang.tree.literal;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public abstract class Literal extends Evaluation {   
   
   private volatile LiteralValue value;
   
   protected Literal() {
      super();
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return value.constraint;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return value;
   }
   
   protected abstract LiteralValue create(Scope scope) throws Exception; 

   protected static class LiteralValue extends Value {
      
      private final Constraint constraint;
      private final Object value;
      
      public LiteralValue(Object value, Constraint constraint) {
         this.constraint = constraint;
         this.value = value;       
      }
      
      @Override
      public boolean isConstant() {
         return true;
      }   
      
      @Override
      public Constraint getConstraint() {
         return constraint;
      }     
      
      @Override
      public <T> T getValue() {
         return (T)value;
      }
      
      @Override
      public void setValue(Object value){
         throw new InternalStateException("Illegal modification of literal '" + value + "'");
      } 
      
      @Override
      public String toString() {
         return String.valueOf(value);
      }      
   }
}