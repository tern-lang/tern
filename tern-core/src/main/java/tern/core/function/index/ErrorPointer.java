package tern.core.function.index;

import static tern.core.function.index.Retention.NEVER;

import tern.core.function.EmptyFunction;
import tern.core.function.ErrorSignature;
import tern.core.function.Function;
import tern.core.function.Invocation;
import tern.core.function.Signature;
import tern.core.scope.Scope;

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
