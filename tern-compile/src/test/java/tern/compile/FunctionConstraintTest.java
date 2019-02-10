package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class FunctionConstraintTest extends TestCase {
   
   private static final String SOURCE_1=
   "invoke((a, b) -> a + b);\n"+
   "\n"+
   "function invoke(r: (a, b)) {\n"+
   "   println(r(22, 33));\n"+
   "}\n";
   
   private static final String SOURCE_2=
   "invoke((a: Integer, b) -> a + b);\n"+
   "\n"+
   "function invoke(r: (a: String, b)) {\n"+
   "   println('string='+r('22', 33));\n"+
   "}\n"+
   "function invoke(r: (a: Integer, b)) {\n"+
   "   println('integer='+r(22, 33));\n"+
   "}\n";

   private static final String SOURCE_3=
   "function x(f: (a, b)) {\n"+
   "   println(f(11,22));\n"+
   "}\n"+
   "function x(f: (a)) {\n"+
   "   println(f(113));\n"+
   "}\n"+
   "x((a,b)->a+b);\n";
   
   private static final String SOURCE_4=
   "function x(f: (a: String, b)) {\n"+
   "   println(f(11,22));\n"+
   "}\n"+
   "function x(f: (a)) {\n"+
   "   println(f(113));\n"+
   "}\n"+
   "function x(f: (a: String)) {\n"+
   "   return \"x:\" + f(113);\n"+
   "}\n"+   
   "function x(f: (a: Integer)) {\n"+
   "   throw 'illegal argument';\n"+
   "}\n"+     
   "var res = x((a:String)->\"res=${a}\");\n"+
   "println(res);\n";
   
   private static final String SOURCE_5=
   "function x(f: (a: String, b)) {\n"+
   "   println('x(f: (a: String, b))->' + f(11,22));\n"+
   "}\n"+
   "function x(f: (a, b)) {\n"+
   "   println('x(f: (a, b))->' + f(11,22));\n"+
   "}\n"+
   "x((a:String, b:String)->a+'='+b);\n";
   
   private static final String SOURCE_6 =
   "class Foo{\n"+
   "   const f;\n"+
   "   new(f){\n"+
   "      this.f=f;\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "fun(['a',11,2, new Foo(3)]);\n"+
   "\n"+
   "function fun(a: []) {\n"+
   "   println('fun(a:[])');\n"+
   "}\n"+
   "\n"+
   "function fun(a:Foo[]) {\n"+
   "   println('fun(a:Foo[])');\n"+
   "}\n";
   
   private static final String SOURCE_7 =
   "class Foo{\n"+
   "   const f;\n"+
   "   new(f){\n"+
   "      this.f=f;\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "fun(new Foo[1]);\n"+
   "\n"+
   "function fun(a: []) {\n"+
   "   println('fun(a:[])');\n"+
   "}\n"+
   "\n"+
   "function fun(a:Foo[]) {\n"+
   "   println('fun(a:Foo[])');\n"+
   "}\n";
   
   private static final String SOURCE_8 =
   "var f: () = () -> println('hello');\n"+
   "\n"+
   "function run(r: Runnable){\n"+
   "   r.run();\n"+
   "}\n"+
   "run(f);\n";
         
   private static final String SOURCE_9 =
   "var f: Runnable = () -> println('hello');\n"+
   "\n"+
   "function run(r: ()){\n"+
   "   r();\n"+
   "}\n"+
   "run(f);\n";
         
   public void testAnyConstraints() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      System.err.println(SOURCE_1);
      executable.execute();
   }
   
   public void testTypeConstraints() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      System.err.println(SOURCE_2);
      executable.execute();
   }
   
   public void testTypeConstraintsOnWidth() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      System.err.println(SOURCE_3);
      executable.execute();
   }
   
   public void testTypeConstraintsOnWidthAndType() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      System.err.println(SOURCE_4);
      executable.execute();
   }   
   
   public void testTypeBindingScore() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_5);
      System.err.println(SOURCE_5);
      executable.execute();
   }   
   
   public void testArrayBindingScore() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_6);
      System.err.println(SOURCE_6);
      executable.execute();
   } 
   
   public void testArrayBinding() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_7);
      System.err.println(SOURCE_7);
      executable.execute();
   } 
   
   public void testFunctionToInterface() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_8);
      System.err.println(SOURCE_8);
      executable.execute();
   }   
   
   public void testInterfaceToFunction() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_9);
      System.err.println(SOURCE_9);
      executable.execute();
   }   
   
}
