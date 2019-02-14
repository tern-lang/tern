package org.ternlang.core.function.index;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FunctionPointerCollector {

   private final List<FunctionPointer> pointers;
   private final Set<Object> overrides;

   public FunctionPointerCollector(List<FunctionPointer> pointers) {
      this.overrides = new HashSet<Object>();
      this.pointers = pointers;
   }

   public void collect(Object key, FunctionPointer pointer) {
      FunctionPointer overload = new CachePointer(pointer, overrides);

      pointers.add(overload);
      overrides.add(key);
   }
}
