package org.ternlang.core.resume;

import org.ternlang.core.scope.Scope;

public class AwaitResult extends YieldResult {

   public AwaitResult() {
      this(null);
   }

   public AwaitResult(Object value) {
      this(value, null, null);
   }

   public AwaitResult(Object value, Scope scope, Resume next) {
      super(value, scope, next);
   }

   @Override
   public boolean isAwait() {
      return true;
   }
}
