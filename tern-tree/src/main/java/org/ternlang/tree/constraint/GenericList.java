package org.ternlang.tree.constraint;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

public interface GenericList {
   List<Constraint> getGenerics(Scope scope) throws Exception;
}
