package tern.compile.staticanalysis;

import java.lang.reflect.Field;

import junit.framework.TestCase;

import tern.common.store.ClassPathStore;
import tern.common.store.Store;
import tern.compile.Compiler;
import tern.compile.Executable;
import tern.compile.StoreContext;
import tern.compile.StringCompiler;
import tern.core.Context;
import tern.core.Reserved;
import tern.core.function.index.FunctionPointer;
import tern.core.function.index.Retention;
import tern.core.function.resolve.FunctionCall;
import tern.core.scope.EmptyModel;
import tern.core.scope.Model;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class FunctionOverloadCheckTest extends TestCase {

   private static final String SOURCE =
   "class Fun{\n"+
   "   foo(i: Integer){\n"+
   "      return i;\n"+
   "   }\n"+
   "}\n"+
   "class Foo extends Fun {\n"+
   "   override foo(i: Integer){\n"+
   "      return i;\n"+
   "   }\n"+
   "   blah(s: String){\n"+
   "      return 'String';\n"+
   "   }\n"+
   "   blah(i: Integer){\n"+
   "      return 'Integer';\n"+
   "   }\n"+
   "   blah(i: Integer, s: String){\n"+
   "      return 'Integer,String';\n"+
   "   }\n"+
   "}\n";

   public void testFunctionResolver() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Compiler compiler = new StringCompiler(context);
      Executable executable = compiler.compile(SOURCE);
      Model model = new EmptyModel();

      executable.execute(model, false);
      assertEquals(getFunction(context,"new Fun()", "foo", 1).getRetention(), Retention.ALWAYS);
      assertEquals(getFunction(context,"new Foo()", "foo", 1).getRetention(), Retention.ALWAYS);
      assertEquals(getFunction(context,"new Foo()", "blah", 1).getRetention(), Retention.NEVER);
      assertEquals(getFunction(context,"new Foo()", "blah", "xx").getRetention(), Retention.NEVER);
      assertEquals(getFunction(context,"new Foo()", "blah", 1, "xx").getRetention(), Retention.ALWAYS);
      assertEquals(getFunction(context,"new Fun()", "toString").getRetention(), Retention.ALWAYS);
      assertEquals(getFunction(context,"new Fun()", "hashCode").getRetention(), Retention.ALWAYS);
      assertEquals(getFunction(context,"new String()", "substring", 1).getRetention(), Retention.ALWAYS);
      assertEquals(getFunction(context,"new String()", "charAt", 1).getRetention(), Retention.ALWAYS);
      assertEquals(getFunction(context,"Integer", "valueOf", 1).getRetention(), Retention.NEVER);
      assertEquals(getFunction(context,"new PrintStream(System.out)", "println", 1).getRetention(), Retention.NEVER);
      assertEquals(getFunction(context,"new PrintStream(System.out)", "flush").getRetention(), Retention.ALWAYS);
   }

   private FunctionPointer getFunction(Context context, String type, String method, Object... args) throws Exception {
      Scope scope = context.getRegistry().addModule(Reserved.DEFAULT_PACKAGE).getScope();
      Object object = context.getEvaluator().evaluate(scope, type);
      FunctionCall call = context.getResolver().resolveInstance(scope, object, method, args);
      if(call == null && object instanceof Type) {
         call = context.getResolver().resolveStatic(scope, (Type)object, method, args);
      }
      Field field = call.getClass().getDeclaredField("pointer");
      field.setAccessible(true);
      return (FunctionPointer)field.get(call);
   }
}
