package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class TryCatchTest extends TestCase {
   
   private static final String SOURCE_1=
   "try {\n"+
   "   println(1);\n"+
   "}finally{\n"+
   "   println(2);\n"+
   "}\n";
   
   private static final String SOURCE_2=
   "try {\n"+
   "  throw \"this is some text 2 - catch Integer\";\n"+
   "}catch(e){\n"+
   "   println(e.class);\n"+
   "   System.out.println(\"caught in last block \"+e);\n"+
   "}finally{\n"+
   "   println(22);\n"+
   "}";
   
   private static final String SOURCE_3=
   "try {\n"+
   "   try {\n"+
   "      throw \"this is some text 2 - catch Integer\";\n"+
   "   }catch(e: Integer){\n"+
   "      System.out.println(\"problem!!!! \"+e);\n"+
   "   }\n"+
   "}catch(e){\n"+
   "   println(e.class);\n"+
   "   System.out.println(\"caught in last block \"+e);\n"+
   "}\n";
   
   private static final String SOURCE_4=
   "try {\n}catch(e){\n}finally{\n}";

   public void testFinally() throws Exception{
      System.err.println(SOURCE_1);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testCatchFinally() throws Exception{
      System.err.println(SOURCE_2);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
   
   public void testCatch() throws Exception{
      System.err.println(SOURCE_3);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      executable.execute();
   }
   
   public void testEmptyBlocks() throws Exception{
      System.err.println(SOURCE_4);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      executable.execute();
   }
}
