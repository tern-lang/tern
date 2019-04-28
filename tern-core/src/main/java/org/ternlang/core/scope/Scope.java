package org.ternlang.core.scope;

import org.ternlang.core.Handle;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public interface Scope extends Handle {
   Type getType();
   Value getThis();
   Scope getChild(); // extend on current scope
   Scope getParent(); // get callers scope  
   ScopeIndex getIndex();
   ScopeTable getTable(); 
   ScopeState getState();  
   ScopeStack getStack();
   Module getModule();
}