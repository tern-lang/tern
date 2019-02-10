package tern.compile;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import tern.core.scope.MapModel;
import tern.core.scope.Model;
import tern.core.variable.Value;

public class ModelScopeTest extends TestCase {
   
   private static final String SOURCE =
   "var x = 3;\n"+
   "function foo(){\n"+
   "   assert x + modelVar >= 10;\n"+
   "   out.println(x+modelVar);\n"+
   "   other();\n"+
   "}\n"+
   "function other(){\n"+
   "   assert modelVar == 22;\n"+
   "   out.println(modelVar);\n"+
   "}\n"+
   "foo();\n";

   public void testModelScope() throws Exception {
      Map map = new HashMap<String,Value>();
      map.put("out",System.out);
      map.put("modelVar", 22);
      Model s = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();;
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(s);
   }
}
