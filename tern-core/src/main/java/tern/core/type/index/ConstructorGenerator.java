package tern.core.type.index;

import static tern.core.Reserved.TYPE_CONSTRUCTOR;

import java.lang.reflect.Constructor;

import tern.core.type.Type;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.Function;
import tern.core.function.Invocation;
import tern.core.function.InvocationFunction;
import tern.core.function.Signature;
import tern.core.platform.Platform;
import tern.core.platform.PlatformProvider;

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