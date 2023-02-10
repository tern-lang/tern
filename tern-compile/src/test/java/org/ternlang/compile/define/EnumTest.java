package org.ternlang.compile.define;

import java.util.HashMap;
import java.util.Map;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;
import org.ternlang.core.Bug;
import org.ternlang.core.scope.MapModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.variable.Value;

import junit.framework.TestCase;

public class EnumTest extends TestCase{
   private static final String SOURCE_1=
   "enum Animal {\n"+
   "   DOG,\n"+
   "   CAT,\n"+
   "   FISH;\n"+
   "}\n"+
   "out.println('enum for DOG='+Animal.DOG.name+' class='+Animal.DOG.class+' ordinal='+Animal.DOG.ordinal);\n"+
   "out.println('enum for CAT='+Animal.CAT.name+' class='+Animal.CAT.class+' ordinal='+Animal.CAT.ordinal);\n"+
   "out.println('enum for FISH='+Animal.FISH.name+' class='+Animal.FISH.class+' ordinal='+Animal.FISH.ordinal);\n"+
   "assert Animal.DOG.name == 'DOG';\n"+
   "assert Animal.CAT.name == 'CAT';\n"+
   "assert Animal.FISH.name == 'FISH';\n";

   private static final String SOURCE_2=
   "enum Person {\n"+
   "   JIM('Jim',23),\n"+
   "   JIM_2('Jim',23),\n"+
   "   TOM('Tom',33),\n"+
   "   BOB('Bob',21);\n"+
   "   var title;\n"+
   "   var age;\n"+
   "   new(title,age){\n"+
   "      this.title=title;\n"+
   "      this.age=age;\n"+
   "   }\n"+
   "}\n"+
   "out.println('enum for JIM='+Person.JIM.name+' class='+Person.JIM.class+' ordinal='+Person.JIM.ordinal+' age='+Person.JIM.age+' title='+Person.JIM.title);\n"+
   "out.println('enum for TOM='+Person.TOM.name+' class='+Person.TOM.class+' ordinal='+Person.TOM.ordinal+' age='+Person.TOM.age+' title='+Person.TOM.title);\n"+
   "out.println('enum for BOB='+Person.BOB.name+' class='+Person.BOB.class+' ordinal='+Person.BOB.ordinal+' age='+Person.BOB.age+' title='+Person.BOB.title);\n"+
   "assert Person.JIM.name == 'JIM';\n"+
   "assert Person.TOM.name == 'TOM';\n"+
   "assert Person.BOB.name == 'BOB';\n"+
   "assert Person.JIM.title == 'Jim';\n"+
   "assert Person.TOM.title == 'Tom';\n"+
   "assert Person.BOB.title == 'Bob';\n";
   
   private static final String SOURCE_3=
   "enum Rubbish {\n"+
   "   OK('ok'),\n"+
   "   ERROR('error', 1);\n"+
   "   FISH;\n"+
   "   var code;\n"+
   "   new(code){\n"+
   "      this.code=code;\n"+
   "   }\n"+   
   "}\n"+
   "out.println('enum for OK='+Rubbish.OK.name);\n";
   
   private static final String SOURCE_4 =
   "enum Word{\n"+
   "   A('a', 1),\n"+
   "   B('b', 2),\n"+
   "   C('d', 3);\n"+
   "\n"+
   "   var char;\n"+
   "   var index;\n"+
   "\n"+
   "   new(char, index){\n"+
   "      this.char = char;\n"+
   "      this.index = index;\n"+
   "   }\n"+
   "   static const WORDS =[A,B,C];\n"+
   "}\n"+
   "\n"+
   "assert Word.WORDS[0].char == 'a';\n";

   public void testSimpleEnum() throws Exception {
      Map map = new HashMap<String,Value>();
      map.put("out",System.out);
      Model s = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      //compiler.compile(SOURCE_1).execute(s);
      System.err.println();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute(s);
      System.err.println();     
   }
   
   public void testComplexEnum() throws Exception {
      Map map = new HashMap<String,Value>();
      map.put("out",System.out);
      Model s = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      //compiler.compile(SOURCE_1).execute(s);
      System.err.println();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute(s);
      System.err.println();      

   }
   
   public void testBadConstructorEnum() throws Exception {
      boolean failure = false;
      try {
         Map map = new HashMap<String,Value>();
         map.put("out",System.out);
         Model s = new MapModel(map);
         Compiler compiler = ClassPathCompilerBuilder.createCompiler();
         //compiler.compile(SOURCE_1).execute(s);
         System.err.println();
         System.err.println(SOURCE_3);
         compiler.compile(SOURCE_3).execute(s);
         System.err.println();
      }catch(Exception e) {
         e.printStackTrace();
         failure = true;
      }
      assertTrue(failure);

   }
   
   @Bug
   public void testStaticListOfEnumValues() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      //compiler.compile(SOURCE_1).execute(s);
//      System.err.println();
//      System.err.println(SOURCE_4);
//      compiler.compile(SOURCE_4).execute();
//      System.err.println();      

   }
}
