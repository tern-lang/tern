package org.ternlang.compile;



public class ThisAndSuperCallTest {

   private static final String SOURCE =
   "class Foo{\n"+
   "}\n"+
   "\n"+
   "class Base extends Foo{\n"+
   "   var a;\n"+
   "   new(a):super(){\n"+
   "      System.err.println(\"Blah(\"+a+\")\");\n"+
   "      this.a=a;\n"+
   "   }\n"+
   "   dump(){\n"+
   "      System.err.println(\"a=\"+a);\n"+
   "   }\n"+
   "}\n"+
   "class Example extends Base{\n"+
   "   var aa;\n"+
   "   var bb;\n"+
   "   var cc;\n"+
   "   new(a, b) : this(a, b, 22){\n"+
   "      System.err.println(\"Example(\"+a+\", \"+b+\")\");\n"+
   "   }\n"+
   "\n"+
   "   new(a, b, c) :super(a){\n"+
   "       System.err.println(\"Example(\"+a+\", \"+b+\", \"+c+\")\");\n"+
   "       this.aa=a;\n"+
   "       this.bb=b;\n"+
   "       this.cc=c;\n"+
   "   }\n"+
   "\n"+
   "   toString(){\n"+
   "      return \"a=${a} aa=${aa} bb=${bb} cc=${cc}\";\n"+
   "   }\n"+
   "}\n"+
   "var e = new Example(3,4);\n"+
   "System.err.println(e);\n";
    
   public void testThisAndSuperCall() throws Exception{
      System.out.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new ThisAndSuperCallTest().testThisAndSuperCall();
   }   
}
