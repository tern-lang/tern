package tern.core.scope;

import tern.core.Handle;
import tern.core.module.Module;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.type.Type;
import tern.core.variable.Value;

public interface Scope extends Handle {
   Type getType();
   Scope getStack(); // extend on current scope
   Scope getScope(); // get callers scope  
   ScopeIndex getIndex();
   ScopeTable getTable(); 
   ScopeState getState();   
   Module getModule();
   Value getThis();
}