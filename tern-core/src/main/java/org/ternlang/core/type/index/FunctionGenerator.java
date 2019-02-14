package org.ternlang.core.type.index;

import java.lang.reflect.Method;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Signature;
import org.ternlang.core.platform.Platform;
import org.ternlang.core.platform.PlatformProvider;
import org.ternlang.core.type.Type;

public class FunctionGenerator {
   
   private final GenericConstraintExtractor extractor;
   private final SignatureGenerator generator;
   private final DefaultMethodChecker checker;
   private final PlatformProvider provider;
   
   public FunctionGenerator(PlatformProvider provider) {
      this.extractor = new GenericConstraintExtractor(); 
      this.generator = new SignatureGenerator();     
      this.checker = new DefaultMethodChecker();
      this.provider = provider;
   }

   public Function generate(Type type, Method method, String name, int modifiers) {
      Signature signature = generator.generate(type, method);

      try {
         Platform platform = provider.create();
         Invocation invocation = platform.createMethod(type, method);
         
         if(checker.check(method)) {
            invocation = new DefaultMethodInvocation(method);
         } else {
            invocation = new MethodInvocation(invocation, method);
         }
         Constraint constraint = extractor.extractReturn(method, modifiers);
         
         if(!method.isAccessible()) {
            method.setAccessible(true);
         }
         return new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + method, e);
      }
   }
}