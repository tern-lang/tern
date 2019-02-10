package tern.tree.constraint;

import tern.core.scope.Scope;

public interface GenericName extends GenericList {
   String getName(Scope scope) throws Exception;
}
