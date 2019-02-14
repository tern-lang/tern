package org.ternlang.compile;

import static org.ternlang.core.result.Result.NORMAL;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.ternlang.core.Reserved;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.MapModel;
import org.ternlang.core.scope.Model;
import org.ternlang.parse.SourceCode;
import org.ternlang.parse.SourceProcessor;
import org.ternlang.parse.SyntaxCompiler;
import org.ternlang.parse.SyntaxParser;

import com.sun.management.ThreadMXBean;

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
