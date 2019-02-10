package tern.core.link;

import tern.core.scope.Scope;

public interface Package {
   PackageDefinition create(Scope scope) throws Exception;
}