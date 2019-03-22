package org.ternlang.tree.function;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

import junit.framework.TestCase;
import org.ternlang.core.Context;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.EmptyModule;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.ModuleScope;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.index.SignatureGenerator;
import org.ternlang.tree.MockContext;
import org.ternlang.tree.MockType;

public class ParameterExtractorTest extends TestCase {

   private static final int ITERATIONS = 10000000;

   public void testParameterExtractor() throws Exception {
      call(String.class, "regionMatches", boolean.class, int.class, String.class, int.class, int.class).iterations(ITERATIONS).execute(true, 0, "foo", 2, 3);
      call(String.class, "substring", int.class).iterations(ITERATIONS).execute(1);
      call(String.class, "substring", int.class, int.class).iterations(ITERATIONS).execute(1, 2);
      call(String.class, "indexOf", String.class).iterations(ITERATIONS).execute("foo");
      call(String.class, "indexOf", String.class, int.class).iterations(ITERATIONS).execute("foo", 2);
   }

   private static MethodCall call(Class type, String name, Class... params) throws Exception {
      String id = type.getName();
      Context context = new MockContext();
      Module module = new EmptyModule(context);
      ModuleScope scope = new ModuleScope(module);
      MockType mockType = new MockType(module, id, null, type);
      SignatureGenerator generator = new SignatureGenerator();
      Method method = type.getDeclaredMethod(name, params);
      Signature signature = generator.generate(mockType, method);
      ParameterExtractor extractor = new ParameterExtractor(signature);

      return new MethodCall(extractor, signature, name, scope);
   }

   private static class MethodCall {

      private ParameterExtractor extractor;
      private Signature signature;
      private Scope scope;
      private String name;
      private int iterations;

      public MethodCall(ParameterExtractor extractor, Signature signature, String name, Scope scope){
         this.extractor = extractor;
         this.signature = signature;
         this.scope = scope;
         this.name = name;
      }

      public MethodCall iterations(int iterations) {
         this.iterations = iterations;
         return this;
      }

      public MethodCall execute(Object... arguments) throws Exception {
         long start = System.currentTimeMillis();

         try {
            for (int i = 0; i < iterations; i++) {
               extractor.extract(scope, arguments);
            }
         } finally {
            DecimalFormat format = new DecimalFormat("###,###,###,###,###,###,###");
            long finish = System.currentTimeMillis();
            long duration = finish - start;

            System.err.println(name + signature + ": " + format.format(iterations) + " in " + duration + " ms");
         }
         return this;
      }
   }
}
