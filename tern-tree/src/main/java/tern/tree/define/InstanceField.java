package tern.tree.define;

import static tern.core.result.Result.NORMAL;
import static tern.core.type.Category.INSTANCE;

import tern.core.Evaluation;
import tern.core.scope.Scope;
import tern.core.type.TypeState;
import tern.core.type.Category;
import tern.core.type.Type;
import tern.core.result.Result;

public class InstanceField extends TypeState {
   
   private final Evaluation evaluation;
   
   public InstanceField(Evaluation evaluation){
      this.evaluation = evaluation;
   }
   
   @Override
   public Category define(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.define(scope); 
      }
      return INSTANCE;
   }

   @Override
   public void compile(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.compile(scope, null); 
      }
   }
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.evaluate(scope, null); 
      }
      return NORMAL;
   }
}