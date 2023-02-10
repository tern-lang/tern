package org.ternlang.tree;

import org.ternlang.core.Evaluation;
import org.ternlang.core.scope.Scope;

public interface Expansion {
   <T extends Evaluation> T expand(Scope scope, Evaluation value) throws Exception;
}
