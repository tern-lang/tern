package tern.compile;

import tern.common.store.ClassPathStore;
import tern.common.store.Store;
import tern.core.Context;

public class ClassPathCompilerBuilder {   
   
   public static Compiler createCompiler() {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      return new StringCompiler(context);
   }
}
