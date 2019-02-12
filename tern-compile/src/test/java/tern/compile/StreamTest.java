package tern.compile;

import junit.framework.TestCase;

import tern.core.scope.EmptyModel;

public class StreamTest extends TestCase{
   
   private static final String SOURCE_1 =
   "import util.stream.Collectors;\n"+
   "var l = ['true', 'false', 'true', 'false', 'false'];\n"+
   "var s = l.stream()\n"+
   "          .map(Boolean::parseBoolean)\n"+
   "          .collect(Collectors.toList());\n"+
   "\n"+
   "println(s);\n";
   
   private static final String SOURCE_2 =
   "import util.stream.IntStream;\n"+
   "IntStream.range(0, 10).limit(10).forEach(i -> println(`IntStream::range: ${i}`));";
   
   private static final String SOURCE_3 =
   "import util.stream.IntStream;\n"+
   "IntStream.of(0, 1, 2, 3, 4, 51, 6, 7, 18, 91, 1011).limit(100).forEach(i -> println(`IntStream::of: ${i}`));";
   
   private static final String SOURCE_4 =
   "import util.stream.IntStream;\n"+         
   "IntStream.iterate(0, i -> i + 2).limit(30).forEach(i -> println(`IntStream::iterate: ${i}`));"; 
   
   private static final String SOURCE_5 =
   "import util.function.Function;\n"+  
   "var upper: Function = x -> {\n"+
   "   println(`x=${x}`);\n"+
   "   return 'x=' + x.toUpperCase();\n"+
   "};\n"+
   "var str: String = upper.apply('hello');\n"+
   "println(str);\n"+
   "assert str == 'x=HELLO';";   
   
   private static final String SOURCE_6 =
   "import util.function.Function;\n"+  
   "var upper: Function = x -> {\n"+
   "   println(`x=${x}`);\n"+
   "   return x.toUpperCase();\n"+
   "};\n"+
   "var str: String = upper.andThen(x -> `${x}a`).andThen(x -> `a${x}`).apply('hello');\n"+
   "println(str);\n";
   
   
   private static final String SOURCE_7 =
   "import util.stream.IntStream;\n"+              
   "IntStream.range(0, i -> i + 2).limit(10).forEach(i -> println(i));";

   private static final String SOURCE_8 =
   "trait HtmlElement {\n"+
    "   render(b: StringBuilder);\n"+
    "}\n"+
    "class HeaderElement with HtmlElement {\n"+
    "\n"+
    "   override render(b: StringBuilder) {\n"+
    "      b.append('<h1>Hello</h1>');\n"+
    "   }\n"+
    "}\n"+
    "class ParagraphElement with HtmlElement {\n"+
    "\n"+
    "   override render(b: StringBuilder) {\n"+
    "      b.append('<p>Talk</p>');\n"+
    "   }\n"+
    "}\n"+
    "let builder = StringBuilder();\n"+
    "let list = [];\n"+
    "\n"+
    "list.add(HeaderElement());\n"+
    "list.add(ParagraphElement());\n"+
    "\n"+
    "list.stream().forEach(e -> {\n"+
    "   e.render(builder);\n"+
    "});\n";


   public void testScriptTypeDescription() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute(new EmptyModel());
   }
   
   public void testIntStreamRange() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute(new EmptyModel());
   }

   public void testIntStreamOf() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      compiler.compile(SOURCE_3).execute(new EmptyModel());
   }
   
   public void testIntStreamIterator() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_4);
      compiler.compile(SOURCE_4).execute(new EmptyModel());
   }
   
   public void testFunctionApply() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_5);
      compiler.compile(SOURCE_5).execute(new EmptyModel());
   }

   public void testFunctionAndThen() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_6);
      compiler.compile(SOURCE_6).execute(new EmptyModel());
   }

   public void testIntStream() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      
      try {
         System.err.println(SOURCE_7);
         compiler.compile(SOURCE_7).execute(new EmptyModel());
      }catch(Exception e) {
         e.printStackTrace();
         failure = true;
         assertEquals(e.getMessage(), "Function 'range(lang.Integer, default.anonymous)' not found for 'util.stream.IntStream'");
      }
      assertTrue("Exception should have been thrown", failure);
   }

   public void testElementsInForEach() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_8);
      compiler.compile(SOURCE_8).execute(new EmptyModel());
   }
}
