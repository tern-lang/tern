package tern.compile;

import junit.framework.TestCase;

public class SimpleConstructorTest extends TestCase {

   private static final String SOURCE_1 =
   "class Foo{\n"+
   "   static const FOO=10;\n"+
   "   private static var c=['xx'];\n"+
   "   new(a):this(a,FOO){}\n"+
   "   new(x,y){\n"+
   "      assert x == 1;\n"+
   "      assert y == 10;\n"+
   "      assert FOO == 10;\n"+
   "      assert c[0]=='xx';\n"+
   "      println(c[0]);\n"+
   "   }\n"+
   "}\n"+
   "new Foo(1);\n";
   
   private static final String SOURCE_2 =
   "class Base {\n"+
   "   private static var j=['xx'];\n"+
   "   new(a,z,h){\n"+
   "      assert a ==12;\n"+
   "      assert z =='Z';\n"+
   "      assert h == 44;\n"+
   "      assert j[0] == 'xx';\n"+
   "   }\n"+
   "}\n"+
   "class Foo extends Base{\n"+
   "   static const VAR = 'Z';\n"+
   "   new(a):super(a,VAR,44){\n"+
   "      assert VAR == 'Z';\n"+
   "   }\n"+
   "}\n"+
   "new Foo(12);\n";
   
   private static final String SOURCE_3 =
   "class Base {\n"+
   "   new(a,z,h){\n"+
   "      assert a ==12;\n"+
   "      assert z =='Z';\n"+
   "      assert h == 44;\n"+
   "   }\n"+
   "}\n"+
   "class Foo extends Base{\n"+
   "   static const VAR = 'TT';\n"+
   "   new(a):super(a,'Z',44){\n"+
   "      println(VAR);\n"+
   "      VAR.substring(1);\n"+
   "      assert VAR == 'TT';\n"+
   "   }\n"+
   "}\n"+
   "new Foo(12);\n";
   
   public void testConstructorDelegate() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      System.err.println(SOURCE_1);
      executable.execute();
   }
   
   public void testSuperConstructorDelegate() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      System.err.println(SOURCE_2);
      executable.execute();
   }
   
   public void testReferenceConstInConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      System.err.println(SOURCE_3);
      executable.execute();
   }
}
