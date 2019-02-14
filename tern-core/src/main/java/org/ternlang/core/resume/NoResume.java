package org.ternlang.core.resume;

import static org.ternlang.core.result.Result.NORMAL;

import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

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