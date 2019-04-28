package org.ternlang.core.scope;

import java.util.Iterator;

import org.ternlang.core.function.Function;
import org.ternlang.core.trace.Trace;

public interface ScopeStack extends Iterable {
   Function current();
   Iterator iterator();
   void before(Trace trace);
   void before(Function function);
   void after(Trace trace);
   void after(Function function);
   void clear();
}
