package tern.core;

import tern.common.Progress;
import tern.core.scope.Scope;
import tern.core.type.Phase;

public interface Entity extends Any{ // Artifact
   Progress<Phase> getProgress();
   Scope getScope();
   String getName();
   int getModifiers();
   int getOrder();
}
