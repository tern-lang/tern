package tern.core.type;

import static tern.core.result.Result.NORMAL;
import static tern.core.type.Category.OTHER;

import tern.core.result.Result;
import tern.core.scope.Scope;

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