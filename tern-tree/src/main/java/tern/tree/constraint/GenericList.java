package tern.tree.constraint;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;

public interface GenericList {
   List<Constraint> getGenerics(Scope scope) throws Exception;
}
