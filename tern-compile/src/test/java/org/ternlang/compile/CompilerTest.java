package org.ternlang.compile;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.ternlang.compile.verify.VerifyException;
import org.ternlang.core.scope.MapModel;
import org.ternlang.core.scope.Model;

public class CompilerTest extends TestCase {
   public static void main(String[] l) throws Exception {
      new CompilerTest().testCompilerPerformance();
   }

   public void testCompilerPerformance() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      compileScripts(compiler);
   }

   private void compileScripts(Compiler compiler) throws Exception {
      int iterations = 100;
      for (int i = 0; i < iterations; i++) {
         String source = ClassPathReader.load("/script/script" + i + ".tern");
         if (source != null) {
            try {
               long start = System.currentTimeMillis();
               long last = start;
               for (int j = 0; j < iterations; j++) {
                  compiler.compile(source);
                  last = System.currentTimeMillis();
               }
               long finish = System.currentTimeMillis();
               long duration = finish - start;
               long once = finish - last;

               System.err.println("Time taken to COMPILE [/script/script" + i + ".js] " + iterations + " times was " + duration + " last was " + once);
            } catch (Exception e) {
               System.err.println(e);
            }
         }
      }
   }

   public void testCompiler() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile("var x=\"xx\";x.toString();");
      executable.execute();
   }

   public void testCompilerWithArgument() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile("map.put('y',x.substring(1));");
      map.put("map", map);
      map.put("x", "blah");
      executable.execute(model);
      assertEquals(map.get("y"), "lah");
   }

   public void testImportStatic() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      try{
         Executable executable = compiler.compile("import static lang.Math.*;var x = 1.6; var y = round(x); map.put('x',x); map.put('y',y);");
         map.put("map", map);
         executable.execute(model);
         assertEquals(map.get("x"), 1.6d);
         assertEquals(map.get("y"), 2l);
      }catch(VerifyException e){
         e.getErrors().get(0).getCause().printStackTrace();
         throw e;
      }
   }

   public void testImport() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile("import security.SecureRandom; var rand = new SecureRandom(); var val = rand.nextInt(10); map.put('rand', rand); map.put('val', val);");
      map.put("map", map);
      executable.execute(model);
      assertEquals(map.get("rand").getClass(), java.security.SecureRandom.class);
      assertEquals(map.get("val").getClass(), Integer.class);
   }

   public void testTypeConstraint() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile("var num : Number = 1.0d; var decimal : Double = 5*num; map.put('num', num); map.put('decimal', decimal);");
      map.put("map", map);
      executable.execute(model);
      assertEquals(map.get("num"), 1.0);
      assertEquals(map.get("decimal"), 5.0);
   }

   public void testTypeConstraintFailure() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile("var num : Number = 1.0d; map.put('num',num); var decimal : Boolean = num*2;");
      map.put("map", map);
      boolean failure = false;
      try {
         executable.execute(model);
      } catch (Throwable e) {
         failure = true;
         e.printStackTrace();
      }
      assertTrue(failure);
   }

   public void testParameterTypeConstraint() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile("function fun(x:String){return \"done=\"+x;}var y =fun(\"x\");map.put('y',y);");

      map.put("map", map);
      executable.execute(model);
      assertEquals(map.get("y"), "done=x");
   }

   public void testParameterTypeConstraintFailure() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile("function fun(x:Date){return \"done=\"+x;}var y =fun(11.2);");
      boolean failure = false;
      try {
         executable.execute(model);
      } catch (Throwable e) {
         e.printStackTrace();
         failure = true;
      }
      assertTrue(failure);
   }
}
