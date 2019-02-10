package tern.tree.define;

import tern.core.scope.Scope;
import tern.core.type.Type;

public interface TypeHierarchy {
   void define(Scope scope, Type type) throws Exception;
   void compile(Scope scope, Type type) throws Exception;
}