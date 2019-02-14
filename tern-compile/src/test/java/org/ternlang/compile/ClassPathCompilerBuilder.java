package org.ternlang.compile;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.core.Context;

public class ClassPathCompilerBuilder {   
   
   public static Compiler createCompiler() {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      return new StringCompiler(context);
   }
}
