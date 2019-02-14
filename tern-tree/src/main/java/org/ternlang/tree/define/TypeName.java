package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.tree.constraint.GenericName;

public interface TypeName extends GenericName {
   int getModifiers(Scope scope) throws Exception;
}