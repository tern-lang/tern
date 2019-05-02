package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public interface TypeHierarchy {
   void create(Scope scope, Type type) throws Exception;
   void define(Scope scope, Type type) throws Exception;
   void compile(Scope scope, Type type) throws Exception;
}