package org.ternlang.core.scope.instance;

import org.ternlang.core.platform.Bridge;
import org.ternlang.core.scope.Scope;

public interface Instance extends Scope {
   Instance getChild();
   Instance getParent();
   Instance getSuper();
   Bridge getBridge();
   Object getProxy();
}