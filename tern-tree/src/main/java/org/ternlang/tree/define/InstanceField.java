package org.ternlang.tree.define;

import static org.ternlang.core.result.Result.NORMAL;
import static org.ternlang.core.type.Category.INSTANCE;

import org.ternlang.core.Evaluation;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.type.Category;
import org.ternlang.core.type.Type;
import org.ternlang.core.result.Result;

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