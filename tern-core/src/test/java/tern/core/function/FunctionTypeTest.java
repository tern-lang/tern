package tern.core.function;

import static java.util.Collections.EMPTY_LIST;
import static tern.core.function.Origin.DEFAULT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import tern.core.module.ContextModule;
import tern.core.module.Module;
import tern.core.module.Path;

public class FunctionTypeTest extends TestCase {
   
   public void testFunctionType() throws Exception {
      Module module = createModule("Mod");
      Signature signature = createSignature("foo", module);
      FunctionType type1 = new FunctionType(signature, module, null);
      FunctionType type2 = new FunctionType(signature, module);
      String name1 = type1.toString();
      String name2 = type2.toString();
      
      assertEquals(name1, "Mod");
      assertEquals(name2, "Mod.anonymous");
      
   }
   
   public static Module createModule(String moduleName){
      return new ContextModule(null, null, new Path(moduleName), moduleName, "", -1);
   }
   
   public static Signature createSignature(String functionName, Module module){
      List<Parameter> parameters = new ArrayList<Parameter>();
      return new FunctionSignature(parameters, EMPTY_LIST, module, null, DEFAULT, true);
   }
}
