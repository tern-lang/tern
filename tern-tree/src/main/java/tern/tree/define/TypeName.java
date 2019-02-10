package tern.tree.define;

import tern.core.scope.Scope;
import tern.tree.constraint.GenericName;

public interface TypeName extends GenericName {
   int getModifiers(Scope scope) throws Exception;
}