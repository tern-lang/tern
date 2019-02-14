package org.ternlang.compile;

import junit.framework.TestCase;

public class CastTest extends TestCase {

   private static final String SOURCE_1 =
   "var builder = new StringBuilder();\n"+
   "\n"+
   "builder.append(97 as Character);\n"+   
   "builder.append(98 as Character);\n"+   
   "builder.append(99 as Character);\n"+   
   "builder.append(100 as Character);\n"+   
   "builder.append(101 as Character);\n"+   
   "builder.append(102 as Character);\n"+
   "\n"+
   "var text = builder.toString();\n"+
   "println(text);\n"+
   "assert text == 'abcdef';\n";
   
   private static final String SOURCE_2 =
   "const x = 11 as Character;\n"+
   "const y = '22.0' as Integer;\n"+
   "\n"+
   "assert x.class == Character.class;\n"+
   "assert y.class == Integer.class;\n";
   
   private static final String SOURCE_3 =
   "function foo(x): String{\n"+
   "   return x as String;\n"+
   "}\n"+
   "assert foo(1).class == String.class;\n";
   
   private static final String SOURCE_4 =
   "function foo(){\n"+
   "   for(i in 0..10)\n" +
   "     if(i % 2 == 0)\n"+
   "        yield i as String;\n"+
   "     else\n"+
   "        yield i as Integer;\n"+
   "}\n"+
   "var it = foo();\n"+
   "var iterator = it.iterator();\n"+
   "assert iterator.next().class == String.class;\n"+
   "assert iterator.next().class == Integer.class;\n"+ 
   "assert iterator.next().class == String.class;\n"+
   "assert iterator.next().class == Integer.class;\n"+ 
   "assert iterator.next() == `4`;\n"+
   "assert iterator.next() == 5;\n";
   
   public void testCharacterCast() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   } 
   
   public void testDeclarationCast() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
   
   public void testReturnCast() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      executable.execute();
   }
   
   public void testYieldCast() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_4);
      Executable executable = compiler.compile(SOURCE_4);
      executable.execute();
   } 
}
