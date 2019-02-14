package org.ternlang.tree.constraint;

import org.ternlang.core.scope.Scope;

public interface GenericName extends GenericList {
   String getName(Scope scope) throws Exception;
}
