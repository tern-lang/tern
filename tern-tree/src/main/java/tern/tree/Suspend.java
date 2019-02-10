package tern.tree;

import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.resume.Resume;
import tern.core.resume.Yield;

public abstract class Suspend<A, B> implements Resume<A, B> {

   protected Result suspend(Scope scope, Result result, Resume child, B value) throws Exception {
      Resume parent = suspend(result, child, value);
      Yield yield = result.getValue();
      Object object = yield.getValue();

      if(result.isAwait()) {
         return Result.getAwait(object, scope, parent);
      }
      return Result.getYield(object, scope, parent);
   }
}
