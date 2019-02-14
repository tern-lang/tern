package org.ternlang.compile.define;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;
import org.ternlang.core.scope.MapModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.variable.Value;

public class StaticVariableTest extends TestCase{
   public static class Pet{
      public static final String DOG="Dog";
      public static final String CAT="Cat";
      public static final String FISH="Fish";
   }
   private static final String SOURCE_1=
   "import com.zuooh.script.build.define.StaticVariableTest$Pet;\n"+
   "\n"+
   "out.println('dog='+StaticVariableTest$Pet.DOG);\n"+
   "out.println('cat='+StaticVariableTest$Pet.CAT);\n"+
   "out.println('fish='+StaticVariableTest$Pet.FISH);\n";

   private static final String SOURCE_2=
   "class Pet{\n"+
   "   static var DOG='Dog';\n"+
   "   static var CAT='Cat';\n"+
   "   static var FISH='Fish';\n"+
   "}\n"+
   "\n"+
   "out.println('Pet.DOG='+Pet.DOG);\n"+
   "out.println('Pet.CAT='+Pet.CAT);\n"+
   "out.println('Pet.FISH='+Pet.FISH);\n";
   
   public static void main(String[] l)throws Exception{
      new StaticVariableTest().testEnums();
   }
   public void testEnums() throws Exception {
      Map map = new HashMap<String,Value>();
      map.put("out",System.out);
      Model s = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure=false;
      System.err.println(SOURCE_1);
      //compiler.compile(SOURCE_1).execute(s);
      System.err.println();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute(s);
      System.err.println();      

   }
}
