package org.ternlang.core.function;

import org.ternlang.core.scope.Scope;

public class FunctionBody {
   
   protected final InvocationBuilder internal;
   protected final InvocationBuilder actual;
   protected final Function function;
   
   public FunctionBody(InvocationBuilder actual, InvocationBuilder internal, Function function) {
      this.function = function;
      this.internal = internal;
      this.actual = actual;
   }
   
   public Function create(Scope scope) throws Exception {
      return function;
   }
   
   public void define(Scope scope) throws Exception {
      actual.define(scope);
      
      if(internal != null) {
         internal.define(scope);
      }
   }   
   
   public void compile(Scope scope) throws Exception {
      actual.compile(scope);
      
      if(internal != null) {
         internal.compile(scope);
      }
   }
}