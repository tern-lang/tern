package org.ternlang.compile;

import static org.ternlang.core.result.Result.NORMAL;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.ternlang.core.Reserved;
import org.ternlang.core.scope.EmptyModel;
import org.ternlang.core.scope.MapModel;
import org.ternlang.core.scope.Model;
import org.ternlang.parse.SourceCode;
import org.ternlang.parse.SourceProcessor;
import org.ternlang.parse.SyntaxCompiler;
import org.ternlang.parse.SyntaxParser;

import com.sun.management.ThreadMXBean;

import junit.framework.TestCase;

/*
Time taken to parse /perf/perf4.tern was 506 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 330 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 313 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 284 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 278 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 295 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 294 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 265 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 265 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 263 size was 69220 compressed to 48660 and 2799 lines
Time taken to compile /perf/perf4.tern was 827 size was 69220 compressed to 48660 and 2799 lines
Time taken to compile /perf/perf4.tern was 431 size was 69220 compressed to 48660 and 2799 lines
Time taken to compile /perf/perf4.tern was 374 size was 69220 compressed to 48660 and 2799 lines
Time taken to compile /perf/perf4.tern was 315 size was 69220 compressed to 48660 and 2799 lines
Time taken to compile /perf/perf4.tern was 318 size was 69220 compressed to 48660 and 2799 lines
Time taken to compile /perf/perf4.tern was 446 size was 69220 compressed to 48660 and 2799 lines
Time taken to compile /perf/perf4.tern was 302 size was 69220 compressed to 48660 and 2799 lines
Time taken to compile /perf/perf4.tern was 334 size was 69220 compressed to 48660 and 2799 lines
Time taken to compile /perf/perf4.tern was 323 size was 69220 compressed to 48660 and 2799 lines
Time taken to compile /perf/perf4.tern was 315 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 258 size was 69220 compressed to 48660 and 2799 lines
parse memory=243,812,552
Time taken to parse /perf/perf4.tern was 258 size was 69220 compressed to 48660 and 2799 lines
parse memory=243,812,552
Time taken to parse /perf/perf4.tern was 258 size was 69220 compressed to 48660 and 2799 lines
parse memory=243,812,552
Time taken to parse /perf/perf4.tern was 259 size was 69220 compressed to 48660 and 2799 lines
parse memory=243,812,552
parse memory=243,812,552
Time taken to parse /perf/perf4.tern was 256 size was 69220 compressed to 48660 and 2799 lines
Time taken to parse /perf/perf4.tern was 256 size was 69220 compressed to 48660 and 2799 lines
parse memory=243,812,552
Time taken to parse /perf/perf4.tern was 261 size was 69220 compressed to 48660 and 2799 lines
parse memory=243,812,488
Time taken to parse /perf/perf4.tern was 267 size was 69220 compressed to 48660 and 2799 lines
parse memory=243,808,520
Time taken to parse /perf/perf4.tern was 255 size was 69220 compressed to 48660 and 2799 lines
parse memory=243,808,520
Time taken to parse /perf/perf4.tern was 254 size was 69220 compressed to 48660 and 2799 lines
parse memory=243,808,520

After bitset + sparse array
parse memory=222,557,832
             227,486,024
             214,916,328
             198,752,824
             181,242,224
              15,694,712 <-- check + build steps (two phase compiler)
Time taken to parse /perf/perf4.tern was 161 size was 69220 compressed to 48660 and 2799 lines
 */

//Assembly time  took 376
//Binary assemble time was 2004 normal was 376
//Time taken to compile  was 2989 size was 57071
public class CompilePerformanceTest extends TestCase {   

   private static final int ITERATIONS = 10;
   public static void main(String[] l)throws Exception{
      new CompilePerformanceTest().testCompilerPerformance();
   }
   public void testCompilerPerformance() throws Exception {
      //compileScript("perf4.js");  
     // compileScript("/script/script13.tern");
      
      compileScript("/perf/perf4.tern");
      compileScript("/perf/perf3.tern");
      compileScript("/perf/perf2.tern");
      compileScript("/perf/perf1.tern");
 /*     executeScript("perf2.js");    
      executeScript("perf3.js"); */   
      
      reuseCompileScript("/perf/perf5.tern");
   }

   public static void reuseCompileScript(String source) throws Exception {
      String script = ClassPathReader.load(source);
      SourceProcessor processor = new SourceProcessor(100);
      SourceCode code = processor.process(script);
      int maxLine = code.getLines()[code.getLines().length -1];
      Map<String, String> resources = new HashMap<String, String>();
      ExecutorService executor = Executors.newFixedThreadPool(20);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler(resources, executor);
      
      for(int j=0;j<ITERATIONS;j++){
         String path = String.format("/script%s.tern", j);
         
         resources.put(path, script);
         compileScriptWithExistingCompiler(compiler, path, maxLine);
      }
      Map<String, String> project = generateSource();
      Set<Map.Entry<String, String>> entries = project.entrySet();
      int totalCount = 0;
      
      for(Map.Entry<String, String> entry : entries) {
         String text = entry.getValue();
         
         code = processor.process(text);
         totalCount += code.getLines()[code.getLines().length -1];
      }
      resources.putAll(project);
      compileScriptWithExistingCompiler(compiler, "/task/Task0.tern", totalCount);
      executor.shutdown();
   }
   
   public static Map<String, String> generateSource() {
      Map<String, String> resources = new HashMap<String, String>();
      int count = 100;
      
      for(int i = 0; i < count; i++) {
         String name = String.format("Task%s", i);
         String script = generateSimpleScript(name);
         String file = String.format("/task/%s.tern", name);
         
         resources.put(file, script);
      }
      for(int i = 0; i < count; i++) {
         StringBuilder builder = new StringBuilder();
         
         for(int j = 0; j < count; j++) {
            if(j != i) {
               builder.append("import task.Task");
               builder.append(j);
               builder.append(";\n");
            }
         }
         String name = String.format("Task%s", i);
         String file = String.format("/task/%s.tern", name);
         String script = resources.get(file);
         
         builder.append(script);
         script = builder.toString();
         resources.put(file, script);
      }
      return resources;
   }
   
   public static String generateSimpleScript(String name) {
       StringBuilder builder = new StringBuilder();
       
       builder.append("\n");
       builder.append("class ");
       builder.append(name);
       builder.append(" with Runnable {\n");
       builder.append("\n");
       builder.append("   public override run() {\n");
       builder.append("      let a = 0;\n");
       
       for(int i = 0; i < 1000; i++) {
          builder.append("      a = ");
          builder.append(i);
          builder.append(";\n");
          builder.append("      println(a);\n");
       }
       builder.append("   }\n");
       builder.append("}\n");

       return builder.toString();
   }
   
   private static void compileScriptWithExistingCompiler(Compiler compiler, String path, double lines) throws Exception {
      Model model = new EmptyModel();
      long start=System.nanoTime();
      compiler.compile(path).execute(model, true);
      long finish=System.nanoTime();
      double duration=TimeUnit.NANOSECONDS.toMillis(finish-start);
      double linesPerMillis = lines/duration;
      // how much time for 1000000 lines
      long linesPerSec = Math.round(linesPerMillis * 1000.0d);
      System.err.println("Time taken to compile "+path+" was " + duration+" for "+Math.round(lines)+" lines, which is " + linesPerSec + " lines per second (including static analysis)");
      start=System.currentTimeMillis();
      finish=System.currentTimeMillis();
      duration=finish-start;
   }
   
   public static void compileScript(String source) throws Exception {
      executeScript(source, false);
      
   }
   public static Object executeScript(String source) throws Exception {
      return executeScript(source, true);
   }
   public static Object executeScript(String source, boolean execute) throws Exception {
      String script = ClassPathReader.load(source);
      SourceProcessor processor = new SourceProcessor(100);
      SourceCode code = processor.process(script);
      int compressed = code.getSource().length;
      int maxLine = code.getLines()[code.getLines().length -1];
      
      for(int j=0;j<ITERATIONS;j++){
         parseScript(source, script, compressed, maxLine);
      }
      for(int j=0;j<ITERATIONS;j++){
         compileScript(source, script, compressed, maxLine);
      }
      for(int j=0;j<ITERATIONS;j++){
         checkMemory(source, script, compressed, maxLine);
      }
      for(int j=0;j<ITERATIONS;j++){
         checkMemoryForParseOnly(source, script, compressed, maxLine);
      }
      return NORMAL;
   } 
   
   private static void compileScript(String source, String script, int compressed, int maxLine) throws Exception {
      long start=System.currentTimeMillis();
      Map<String, Object> map = new HashMap<String, Object>();
      Model model = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();

      map.put("out", System.out);
      map.put("err", System.err);
      map.put("count", 100);
      
      
      Executable e=compiler.compile(script);
      long finish=System.currentTimeMillis();
      long duration=finish-start;
      System.err.println("Time taken to compile "+source+" was " + duration+" size was "+script.length() + " compressed to " + compressed + " and " + maxLine + " lines");
      start=System.currentTimeMillis();
      finish=System.currentTimeMillis();
      duration=finish-start;
   }
   
   private static void parseScript(String source, String script, int compressed, int maxLine) throws Exception {
      long start=System.currentTimeMillis();
      SyntaxParser p=new SyntaxCompiler(Reserved.GRAMMAR_FILE).compile();
      p.parse(source, script, "script");
      long finish=System.currentTimeMillis();
      long duration=finish-start;
      System.err.println("Time taken to parse "+source+" was " + duration+" size was "+script.length() + " compressed to " + compressed + " and " + maxLine + " lines");
   }
   
   private static void checkMemory(String source, String script, int compressed, int maxLine) throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      ThreadMXBean bean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
      Thread thread = Thread.currentThread();
      long id = thread.getId();
      System.gc();
      System.gc();
      Thread.sleep(100);
      long before = bean.getThreadAllocatedBytes(id);
      parseScript(source, script, compressed, maxLine);
      long after = bean.getThreadAllocatedBytes(id);
      System.out.println("parse memory=" + format.format(after - before));
   }
   
   private static void checkMemoryForParseOnly(String source, String script, int compressed, int maxLine) throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      SyntaxParser p=new SyntaxCompiler(Reserved.GRAMMAR_FILE).compile();
      ThreadMXBean bean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
      Thread thread = Thread.currentThread();
      long id = thread.getId();
      System.gc();
      System.gc();
      Thread.sleep(100);
      long before = bean.getThreadAllocatedBytes(id);
      long start=System.currentTimeMillis();
      p.parse(source, script, "script");
      long finish=System.currentTimeMillis();
      long duration=finish-start;
      long after = bean.getThreadAllocatedBytes(id);
      System.out.println("Time taken to parse "+source+" was " + duration+" size was "+script.length() + " compressed to " + 
               compressed + " and " + maxLine + " lines and memory used " + format.format(after - before));
   }
}
