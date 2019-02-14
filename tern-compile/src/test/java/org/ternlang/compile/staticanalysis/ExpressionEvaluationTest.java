package org.ternlang.compile.staticanalysis;

import junit.framework.TestCase;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;

public class ExpressionEvaluationTest extends TestCase {

   private static final String SOURCE_1 =
   "function call(){\n"+      
   "  eval('fun()');\n"+
   "}\n"+      
   "function fun(){\n"+
   "   try {\n"+
   "        var x = `xx`;\n"+
   "        x++;\n"+
   "   } catch(e) {\n"+
   "     e.printStackTrace();\n"+
   "   }\n"+
   "}\n"+
   "call();\n";

   private static final String SOURCE_2 =
   "function call(){\n"+      
   "  eval('fun()');\n"+
   "}\n"+      
   "function fun(){\n"+
   "   try {\n"+
   "        var x = `xx`;\n"+
   "        x++;\n"+
   "   } catch(e) {\n"+
   "     e.printStackTrace();\n"+
   "   }\n"+
   "}\n"+
   "eval('call()');\n";
   
   public void testEvaluation() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute();
   }
   
   public void testDoubleEvaluation() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute();
   }
}
