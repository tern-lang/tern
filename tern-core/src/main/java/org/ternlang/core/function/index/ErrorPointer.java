package org.ternlang.core.function.index;

import static org.ternlang.core.function.index.Retention.NEVER;

import org.ternlang.core.function.EmptyFunction;
import org.ternlang.core.function.ErrorSignature;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;

public class ErrorPointer implements FunctionPointer {

   private final Signature signature;
   private final Function function;
   
   public ErrorPointer() {
      this.signature = new ErrorSignature();
      this.function = new EmptyFunction(signature);
   }

   @Override
   public ReturnType getType(Scope scope) {
      return new AttributeType(null, scope);
   }

   @Override
   public Function getFunction() {
      return function;
   }

   @Override
   public Invocation getInvocation() {
      return null;
   }

   @Override
   public Retention getRetention() {
      return NEVER;
   }
   
   @Override
   public String toString() {
      return String.valueOf(function);
   }
}
