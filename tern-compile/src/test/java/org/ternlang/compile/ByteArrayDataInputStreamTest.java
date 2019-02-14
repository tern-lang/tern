package org.ternlang.compile;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.ternlang.core.scope.MapModel;
import org.ternlang.core.scope.Model;

public class ByteArrayDataInputStreamTest extends TestCase {
   
   private static final String SOURCE_1 =
   "import org.ternlang.compile.ByteArrayDataInputStreamTest;\n"+
   "class TextReader {\n"+
   "   dumpBytes(s: String) {\n"+
   "      var arr: Byte[] = new Byte[s.length()];\n"+
   "      var buf = new ByteArrayInputStream(s.getBytes());\n"+
   "      var buf2 = new ByteArrayInputStream(s.getBytes());\n"+  
   "      ByteArrayDataInputStreamTest.dumpStreamBytes(buf2);\n"+
   "      var is = new DataInputStream(buf);\n"+
   "      is.readFully(arr);\n"+
   "      for(var i = 0; i < arr.length; i++){\n"+
   "         println(i+'='+arr[i]);\n"+
   "      }\n"+
   "      println();\n"+
   "   }\n"+
   "}\n"+
   "new TextReader().dumpBytes(\"hello world\");\n";
   
   private static final String SOURCE_2 =
   "var arr1: %{1}[] = new %{2}[10];\n"+ 
   "var arr2 = input;\n"+ // pass in parameter
   "var arr3 = new %{2}[10];\n"+
   "var arr4: %{1}[] = input;\n"+
   "println(arr1.class);\n"+
   "println(arr2.class);\n"+
   "println(arr3.class);\n"+  
   "assert arr4 == arr2;\n"+    
   "assert arr4 === arr2;\n"+      
   "assert arr2.class == arr3.class;\n";     
   
   public void testReadFully() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
      System.out.println("-------------------------- JAVA BELOW ------------------------");
      dumpStreamBytes(new ByteArrayInputStream("hello world".getBytes()));
      dumpBytes("hello world");
   }
   
   public void testByteArrayBackingClass() throws Exception {
      Map<Class, Class> map = new HashMap<Class, Class>();
      map.put(int.class, Integer.class);
      map.put(long.class, Long.class);
      map.put(float.class, Float.class);
      map.put(short.class, Short.class);
      map.put(byte.class, Byte.class);
      map.put(double.class, Double.class);
      map.put(char.class, Character.class);
      map = Collections.unmodifiableMap(map);
      
      // single dimensions
      for(Entry<Class, Class> entry : map.entrySet()) {
         Object input = Array.newInstance(entry.getKey(), 10);
         String match = entry.getValue().getSimpleName();
         String source = SOURCE_2.replace("%{1}", match).replace("%{2}", match);
         System.err.println(source);
         Model model = new MapModel(Collections.singletonMap("input", input));
         Compiler compiler = ClassPathCompilerBuilder.createCompiler();
         Executable executable = compiler.compile(source);
         
         executable.execute(model);
         System.err.println();
      }
      // double dimensions
      for(Entry<Class, Class> entry : map.entrySet()) {
         Object input = Array.newInstance(entry.getKey(), 10, 10);
         String match = entry.getValue().getSimpleName();
         String source = SOURCE_2.replace("%{1}", match+"[]").replace("%{2}", match+"[10]");
         System.err.println(source);
         Model model = new MapModel(Collections.singletonMap("input", input));
         Compiler compiler = ClassPathCompilerBuilder.createCompiler();
         Executable executable = compiler.compile(source);
        
         executable.execute(model);
         System.err.println();
      }
   }
   
   
   public static void dumpStreamBytes(InputStream is) throws Exception {
      int count = 0;
      int index = 0;
      while((count = is.read()) != -1){
         System.out.println("["+(index++)+"]->"+count);
      }
   }
   
   private static void dumpBytes(String s) throws Exception {
      byte[] arr = new byte[s.length()];
      ByteArrayInputStream buf = new ByteArrayInputStream(s.getBytes());
      DataInput is = new DataInputStream(buf);
      is.readFully(arr);
      for(int i = 0; i < arr.length; i++){
         System.out.println(i+"="+arr[i]);
      }
      System.out.println();
      System.out.println();
   }

}
