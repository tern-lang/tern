package org.ternlang.core.resume;

import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public interface Resume<A, B> {
   Result resume(Scope scope, A value) throws Exception;
   Resume suspend(Result result, Resume resume, B value) throws Exception;
}