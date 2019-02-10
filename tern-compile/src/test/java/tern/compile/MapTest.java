package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class MapTest extends TestCase {
   
   private static final String SOURCE_1 =
   "var id = 123;\n"+
   "var date = new Date();\n"+
   "var map1 = {'id': id, 'date': date};\n"+
   "var map2 = {'id': 'id', 'date': 'date'};\n"+
   "var map3 = {id: 'id', date: 'date'};\n"+
   "var map4 = {id: id, date: date};\n"+
   "\n"+
   "println(map1+' quoted keys');\n"+
   "println(map2+' quoted values and keys');\n"+
   "println(map3+' quoted values');\n"+
   "println(map4+' nothing quoted');\n";
   
   private static final String SOURCE_2 =
   "var id = 123;\n"+
   "var date = new Date();\n"+
   "var map1 = {'id': id, 'date': date};\n"+
   "var map2 = {'id': 'id', 'date': 'date'};\n"+
   "var map3 = {'id': [1, 2, 3], 'date': date};\n"+
   "\n"+
   "assert map1.id == 123;\n"+
   "assert map2.id == 'id';\n"+
   "assert map3.id == [1,2,3];\n"+
   "assert map3.id[0] == 1;\n";
   
   private static final String SOURCE_3 =
   "var id = 123;\n"+
   "var date = new Date();\n"+
   "var map1 = {'id': id, 'date': date};\n"+
   "var map2 = {'id': 'id', 'date': 'date'};\n"+
   "var map3 = {'id': [1, 2, 3], 'date': date};\n"+
   "\n"+
   "assert map1.id == 123;\n"+   
   "assert map2.id == 'id';\n"+

   "assert map3.id == [1,2,3];\n"+
   "assert map3.id[0] == 1;\n"+
   "\n"+
   "map1.id = 222;\n"+
   "map2.id = 'foo';\n"+
   "map3.blah = 2222222;\n"+      
   "\n"+
   "assert map1.id == 222;\n"+
   "assert map2.id == 'foo';\n"+
   "assert map3.blah == 2222222;\n"+
   "assert map3['blah'] == 2222222;\n"+    
   "assert map3.id == [1,2,3];\n"+
   "assert map3.id[0] == 1;\n"+
   "\n"+
   "map1['id'] = 444;\n"+
   "map2['id'] = 'lala';\n"+
   "\n"+
   "assert map1.id == 444;\n"+
   "assert map2.id == 'lala';\n"+
   "assert map3.blah == 2222222;\n"+
   "assert map3['blah'] == 2222222;\n"+      
   "assert map3.id == [1,2,3];\n"+
   "assert map3.id[0] == 1;\n";      

   public void testMap() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testMapVariables() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
   
   public void testMapAssignments() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      executable.execute();
   }
}
