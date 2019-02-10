package tern.compile;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class PrimitiveArrayCoerctionTest extends TestCase {

   private static final String SOURCE = 
   "var arr1: %{1}[] = new %{1}[10];\n"+
   "var arr2 = new %{1}[10];\n"+
   "\n"+
   "fun(arr1);\n"+
   "fun(arr2);\n"+
   "\n"+
   "for(var i = 0; i < 10; i++){\n"+
   "   assert arr1[i] == i;\n"+
   "   assert arr2[i] == i;\n"+
   "}\n"+
   "\n"+
   "function fun(arr: %{1}[]){\n"+
   "   for(var i = 0; i < arr.length; i++){\n"+
   "      arr[i] = i;\n"+
   "   }\n"+
   "}\n";

   public void testArrayCoercion() throws Exception {
      Set<Class> types = new HashSet<Class>();
      types.add(Integer.class);
      types.add(Long.class);
      types.add(Float.class);
      types.add(Short.class);
      types.add(Byte.class);
      types.add(Double.class);
      types = Collections.unmodifiableSet(types);
      
      for(Class entry : types) {
         String name = entry.getSimpleName();
         String source = SOURCE.replace("%{1}", name);
         Compiler compiler = ClassPathCompilerBuilder.createCompiler();
         System.err.println(source);
         Executable executable = compiler.compile(source);
         executable.execute();
         System.err.println();
      }
   }
}
