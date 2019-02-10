package tern.core.scope.instance;

import tern.core.platform.Bridge;
import tern.core.scope.Scope;

public interface Instance extends Scope {
   Instance getStack();
   Instance getScope();
   Instance getSuper();
   Bridge getBridge();
   Object getProxy();
}