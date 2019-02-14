package org.ternlang.core.link;

import org.ternlang.core.Statement;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;

public interface PackageDefinition {
   Statement define(Scope scope, Path from) throws Exception;
}