package tern.core.link;

import tern.core.Statement;
import tern.core.module.Path;
import tern.core.scope.Scope;

public interface PackageDefinition {
   Statement define(Scope scope, Path from) throws Exception;
}