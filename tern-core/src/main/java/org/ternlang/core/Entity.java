package org.ternlang.core;

import org.ternlang.common.Progress;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Phase;

public interface Entity extends Any{ // Artifact
   Progress<Phase> getProgress();
   Scope getScope();
   String getName();
   int getModifiers();
   int getOrder();
}
