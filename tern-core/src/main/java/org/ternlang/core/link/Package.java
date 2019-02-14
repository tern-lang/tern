package org.ternlang.core.link;

import org.ternlang.core.scope.Scope;

public interface Package {
   PackageDefinition create(Scope scope) throws Exception;
}