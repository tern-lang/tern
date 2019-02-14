package org.ternlang.compile;

import java.util.function.BiFunction;

import junit.framework.TestCase;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.core.Context;
import org.ternlang.core.type.Type;

public class FunctionReferenceInSuperInterfaceTest extends TestCase{

   public static class Foo implements Runnable{

      @Override
      public void run() {}
   }
   
   private static final String SOURCE =
   "var list = [1,2,3,4];\n"+
   "list.stream().reduce((a,b) -> a+b);";      
   
   public void testHierarchyOfInterfaces() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Type fooType = context.getLoader().loadType(Foo.class);
      Type biFunctionType = context.getLoader().loadType(BiFunction.class);
      Type testType = context.getLoader().loadType(FunctionReferenceInSuperInterfaceTest.class);
      
      System.err.println(fooType.getTypes());
      System.err.println(biFunctionType.getTypes());
      System.err.println(testType.getTypes());
      
   }
   
   
   public void testFunctionReference()throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
