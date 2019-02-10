package tern.compile;

import java.util.Comparator;

import junit.framework.TestCase;

import tern.common.store.ClassPathStore;
import tern.common.store.Store;
import tern.core.Context;
import tern.core.scope.EmptyModel;
import tern.core.type.Type;
import tern.core.type.TypeLoader;

public class TypeDescriptionTest extends TestCase {
   
   private static final String SOURCE =
   "class Outer {\n"+
   "   class Inner{\n"+
   "      class InnerInner{}\n"+
   "   }\n"+ 
   "}\n"+
   "var t = new Outer.Inner.InnerInner().class;\n"+
   "println(t);\n";

   
   public void testScriptTypeDescription() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }

   public void testTypeDescription() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      TypeLoader loader = context.getLoader();
      Class real1 = new Comparator() {

         @Override
         public int compare(Object o1, Object o2) {
            return 0;
         }
      }.getClass();
      Type type1 = loader.loadType(real1);
      
      System.err.println();
      System.err.println(type1.getName());
      System.err.println(type1.toString());
      
      Class real2 = MockComparator.class;
      Type type2 = loader.loadType(real2);
     
      System.err.println();
      System.err.println(type2.getName());
      System.err.println(type2.toString());
      
      Class real3 = MockComparator.INNER;
      Type type3 = loader.loadType(real3);
      
      System.err.println();
      System.err.println(type3.getName());
      System.err.println(type3.toString());
      
      assertEquals("compile.TypeDescriptionTest$1", type1.toString());
      assertEquals("compile.TypeDescriptionTest$MockComparator", type2.toString());
      assertEquals("compile.TypeDescriptionTest$MockComparator$1", type3.toString());
   }
   private static class MockComparator implements Comparator{
      public static Class INNER = new Comparator() {

         @Override
         public int compare(Object o1, Object o2) {
            return 0;
         }
      }.getClass();

      @Override
      public int compare(Object o1, Object o2) {
         return 0;
      }
      
   }
}
