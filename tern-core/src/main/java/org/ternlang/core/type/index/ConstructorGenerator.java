package org.ternlang.core.type.index;

import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import java.lang.reflect.Constructor;

import org.ternlang.core.type.Type;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Signature;
import org.ternlang.core.platform.Platform;
import org.ternlang.core.platform.PlatformProvider;

public class ConstructorGenerator {

   private final SignatureGenerator generator;
   private final PlatformProvider provider;
   
   public ConstructorGenerator(PlatformProvider provider) {
      this.generator = new SignatureGenerator();
      this.provider = provider;
   }
   
   public Function generate(Type type, Constructor constructor, Class[] types, int modifiers) {
      Platform platform = provider.create();
      Signature signature = generator.generate(type, constructor);
      Invocation invocation = platform.createConstructor(type, constructor);
      Constraint constraint = Constraint.getConstraint(type);
      
      try {
         invocation = new ConstructorInvocation(invocation, constructor);
         
         if(!constructor.isAccessible()) {
            constructor.setAccessible(true);
         }
         return new InvocationFunction(signature, invocation, type, constraint, TYPE_CONSTRUCTOR, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + constructor, e);
      }
   } 
}