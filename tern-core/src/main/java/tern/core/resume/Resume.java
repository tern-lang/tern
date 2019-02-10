package tern.core.resume;

import tern.core.result.Result;
import tern.core.scope.Scope;

public interface Resume<A, B> {
   Result resume(Scope scope, A value) throws Exception;
   Resume suspend(Result result, Resume resume, B value) throws Exception;
}