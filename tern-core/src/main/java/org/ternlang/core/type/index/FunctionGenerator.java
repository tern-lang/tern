package org.ternlang.core.type.index;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.Module;
import org.ternlang.core.platform.Platform;
import org.ternlang.core.platform.PlatformProvider;
import org.ternlang.core.type.Type;

import java.lang.reflect.Method;

public class FunctionGenerator {

   private static final String[] IGNORE = {
        "java.lang.Object::finalize",
        "java.lang.Object::clone"
   };

   private final GenericConstraintExtractor extractor;
   private final SignatureGenerator generator;
   private final MethodAccessFilter filter;
   private final DefaultMethodChecker checker;
   private final PlatformProvider provider;

   public FunctionGenerator(PlatformProvider provider) {
      this.filter = new MethodAccessFilter(IGNORE);
      this.extractor = new GenericConstraintExtractor();
      this.generator = new SignatureGenerator();
      this.checker = new DefaultMethodChecker();
      this.provider = provider;
   }

   public Function generate(Type type, Method method, String name, int modifiers) {
      Module module = type.getModule();
      Context context = module.getContext();
      Signature signature = generator.generate(type, method);

      try {
         Platform platform = provider.create();
         Invocation invocation = platform.createMethod(type, method);
         ProxyWrapper wrapper = context.getWrapper();

         if (checker.check(method)) {
            invocation = new DefaultMethodInvocation(wrapper, method);
         } else {
            invocation = new MethodInvocation(wrapper, invocation, method);
         }
         Constraint constraint = extractor.extractReturn(method, modifiers);

         if (filter.accept(method)) {
            method.setAccessible(true);
         }
         return new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      } catch (Exception e) {
         throw new InternalStateException("Could not create function for " + method, e);
      }
   }

}