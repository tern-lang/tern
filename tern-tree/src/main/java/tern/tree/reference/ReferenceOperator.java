package tern.tree.reference;

import tern.core.Evaluation;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.parse.StringToken;

public enum ReferenceOperator {
   NORMAL("."){
      @Override
      public Value operate(Scope scope, Evaluation next, Value value) throws Exception {
         Object object = value.getValue();
         
         if(object != null) {
            return next.evaluate(scope, value);
         }
         throw new NullPointerException("Reference to a null object");
      }      
   }, 
   FORCE("!."){
      @Override
      public Value operate(Scope scope, Evaluation next, Value value) throws Exception {
         Object object = value.getValue();
         
         if(object != null) {
            return next.evaluate(scope, value);
         }
         throw new NullPointerException("Reference to a null object");
      } 
   },
   EXISTENTIAL("?."){
      @Override
      public Value operate(Scope scope, Evaluation next, Value value) throws Exception {
         Object object = value.getValue();
         
         if(object != null) {
            return next.evaluate(scope, value);
         }
         return Value.getTransient(object);
      }
   };   
   
   private final String symbol;
   
   private ReferenceOperator(String symbol) {
      this.symbol = symbol;
   }
   
   public abstract Value operate(Scope scope, Evaluation next, Value value) throws Exception;
   
   public static ReferenceOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         ReferenceOperator[] operators = ReferenceOperator.values();
         
         for(ReferenceOperator operator : operators) {
            if(operator.symbol.equals(value)) {
               return operator;
            }
         }
      }
      return null;
   }
}