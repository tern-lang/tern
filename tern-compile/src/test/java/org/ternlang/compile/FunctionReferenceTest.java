package org.ternlang.compile;

import junit.framework.TestCase;

import org.ternlang.compile.verify.VerifyException;
import org.ternlang.core.scope.EmptyModel;

public class FunctionReferenceTest extends TestCase {

   private static final String SOURCE_1 =
   "var s = \"hello\";\n"+
   "//var f = i -> s.substring(i);\n"+
   "var f = s::substring;\n"+
   "var r = f(1);\n"+
   "\n"+
   "println(f);\n";
   
   private static final String SOURCE_2 =
   "class Foo {\n"+
   "   var a,b;\n"+
   "   new(a,b){\n"+
   "      this.a=a;\n"+
   "      this.b=b;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"${a},${b}\";"+
   "   }\n"+
   "}\n"+
   "function create(f) {\n"+
   "   f(1,2);\n"+
   "}\n"+
   "var res = create(Foo::new);\n"+
   "println(res);\n"+
   "assert res.toString() == '1,2';\n";
   
   private static final String SOURCE_3 =
   "var f = String::new;\n"+
   "\n"+
   "assert '' == f();\n"+
   "assert 'hello' == f('hello');\n";
   
   private static final String SOURCE_4 =
   "import util.stream.Collectors;\n"+
   "\n"+
   "const result = ['a', 'b', 'c', null, 'd'].stream()\n"+
   "   .filter(Objects::nonNull)\n"+
   "   .collect(Collectors.toList());\n"+
   "\n"+
   "assert result.length == 4;\n"+
   "assert result[0] == 'a';\n"+
   "assert result[1] == 'b';\n"+
   "assert result[2] == 'c';\n"+
   "assert result[3] == 'd';\n";
   
   private static final String SOURCE_5 =
   "import util.stream.Collectors;\n"+
   "\n"+
   "const result = ['a', 'b', 'c', null, 'd']\n"+
   "   .stream()\n"+
   "   .filter(Thing::isNotNull)\n"+
   "   .map(Thing::toUpper)\n"+
   "   .collect(Collectors.toList());\n"+
   "\n"+
   "assert result[0] == 'A';\n"+
   "assert result[1] == 'B';\n"+
   "assert result[2] == 'C';\n"+
   "assert result[3] == 'D';\n"+
   "\n"+
   "module Thing {\n"+
   "   isNotNull(o){\n"+
   "      return o != null;\n"+
   "   }\n"+
   "   toUpper(o){\n"+
   "      return `${o}`.toUpperCase();\n"+
   "   }\n"+
   "}\n";

   private static final String SOURCE_6 =   
   "import util.stream.Collectors;\n"+
   "\n"+
   "const blah = new Foo();\n"+
   "const result = ['a', 'b', 'c', null, 'd']\n"+
   "   .stream()\n"+
   "   .filter(blah::isNotNull)\n"+
   "   .map(blah::toUpper)\n"+
   "   .collect(Collectors.toList());\n"+
   "\n"+
   "assert result[0] == 'A';\n"+
   "assert result[1] == 'B';\n"+
   "assert result[2] == 'C';\n"+
   "assert result[3] == 'D';\n"+
   "\n"+
   "class Foo {\n"+
   "   isNotNull(o){\n"+
   "      return o != null;\n"+
   "   }\n"+
   "   toUpper(o){\n"+
   "      return `${o}`.toUpperCase();\n"+
   "   }\n"+
   "}\n";   
         
   private static final String SOURCE_7 =
   "var p = System.err::println;\n"+
   "assert p(`hello`) == null;\n";
   
   private static final String SOURCE_8 =
   "f(1,2, Math::max); // method handles\n"+
   "function f(x, y, f: (a,b)) {\n"+
   "   var result = f(x, y);\n"+
   "   println(result);\n"+
   "}\n";
   
   
   public void testFunctionReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute(new EmptyModel());
   }
   
   public void testFunctionReferenceConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute(new EmptyModel());
   }
   
   public void testFunctionReferenceNativeConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      compiler.compile(SOURCE_3).execute(new EmptyModel());
   }
   
   public void testFunctionReferenceObjectNotNull() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_4);
      compiler.compile(SOURCE_4).execute(new EmptyModel());
   }
   
   public void testFunctionReferenceInModule() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_5);
      try {
         compiler.compile(SOURCE_5).execute(new EmptyModel());
      }catch(VerifyException e){
         e.getErrors().get(0).getCause().printStackTrace();
         throw e;
      }
   }  
   
   public void testFunctionReferenceInClassInstance() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_6);
      compiler.compile(SOURCE_6).execute(new EmptyModel());
   }  
   
   public void testFunctionReferenceForStaticVariable() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_7);
      compiler.compile(SOURCE_7).execute(new EmptyModel());
   }   
   
   public void testFunctionConstraint() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_8);
      compiler.compile(SOURCE_8).execute(new EmptyModel());
   }  
}
