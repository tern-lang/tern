package org.ternlang.tree;

import org.ternlang.core.Execution;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.resume.Resume;
import org.ternlang.core.resume.Yield;

public abstract class SuspendStatement<T> extends Execution implements Resume<T, T> {

   protected Result suspend(Scope scope, Result result, Resume child, T value) throws Exception {
      Resume parent = suspend(result, child, value);
      Yield yield = result.getValue();
      Object object = yield.getValue();

      if(result.isAwait()) {
         return Result.getAwait(object, scope, parent);
      }
      return Result.getYield(object, scope, parent);
   }
}
