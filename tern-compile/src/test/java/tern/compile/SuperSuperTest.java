package tern.compile;

import junit.framework.TestCase;

public class SuperSuperTest extends TestCase {
   
   private static final String SOURCE =
   "class One {\n"+
   "   var x;\n"+
   "   new(x){\n"+
   "      this.x=x;\n"+
   "   }\n"+
   "   dump(){\n"+
   "      return \"One.dump(${x})\";\n"+
   "   }\n"+
   "}\n"+
   "class Two extends One{\n"+
   "   new(x) :super(x){}\n"+
   "   dump(){\n"+
   "      return super.dump();\n"+
   "   }\n"+
   "}\n"+
   "class Three extends Two{\n"+
   "   new(x):super(x){}\n"+
   "   dump(){\n"+
   "      return super.dump();\n"+
   "   }\n"+
   "}\n"+
   "var t = new Three(1);\n"+
   "println(t.dump());\n";
         
   public void testSuper() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new SuperSuperTest().testSuper();
   }
}
