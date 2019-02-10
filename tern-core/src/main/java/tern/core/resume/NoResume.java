package tern.core.resume;

import static tern.core.result.Result.NORMAL;

import tern.core.result.Result;
import tern.core.scope.Scope;

public class NoResume implements Resume {
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return NORMAL;
   }

   @Override
   public Resume suspend(Result result, Resume resume, Object o) throws Exception {
      return null;
   }
}