package org.ternlang.core.type;

import static org.ternlang.core.result.Result.NORMAL;
import static org.ternlang.core.type.Category.OTHER;

import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public abstract class TypeState {

   public Category define(Scope scope, Type type) throws Exception {
      return OTHER;
   } 
   
   public void compile(Scope scope, Type type) throws Exception {}
   public void allocate(Scope scope, Type type) throws Exception {} // static stuff
   
   public Result execute(Scope scope, Type type) throws Exception { // instance stuff
      return NORMAL;
   }
}