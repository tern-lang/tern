package tern.tree;

import tern.core.Execution;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.resume.Resume;
import tern.core.resume.Yield;

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
