package tern.core.function;

import tern.core.scope.Scope;

public interface InvocationBuilder {
   void define(Scope scope) throws Exception;
   void compile(Scope scope) throws Exception;
   Invocation create(Scope scope) throws Exception;
}