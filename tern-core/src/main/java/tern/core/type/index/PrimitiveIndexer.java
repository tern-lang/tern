package tern.core.type.index;

import static tern.core.ModifierType.ANY;
import static tern.core.ModifierType.CLASS;
import static tern.core.Reserved.ANY_TYPE;
import static tern.core.Reserved.DEFAULT_PACKAGE;
import static tern.core.Reserved.METHOD_EQUALS;
import static tern.core.Reserved.METHOD_HASH_CODE;
import static tern.core.Reserved.METHOD_NOTIFY;
import static tern.core.Reserved.METHOD_NOTIFY_ALL;
import static tern.core.Reserved.METHOD_TO_STRING;
import static tern.core.Reserved.METHOD_WAIT;
import static tern.core.Reserved.TYPE_CONSTRUCTOR;
import static tern.core.constraint.Constraint.BOOLEAN;
import static tern.core.constraint.Constraint.INTEGER;
import static tern.core.constraint.Constraint.NONE;
import static tern.core.constraint.Constraint.STRING;
import static tern.core.type.Phase.COMPILE;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import tern.common.Progress;
import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.function.Invocation;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.type.Phase;
import tern.core.type.Type;

public class PrimitiveIndexer{
   
   private final AtomicReference<Type> reference;
   private final PrimitiveFunctionGenerator generator;
   private final TypeIndexer indexer;
   
   public PrimitiveIndexer(TypeIndexer indexer){
      this.generator = new PrimitiveFunctionGenerator();
      this.reference = new AtomicReference<Type>();
      this.indexer = indexer;
   }

   public Type indexAny() {
      Type type  = reference.get();
      
      if(type == null) {
         Type result = indexer.defineType(DEFAULT_PACKAGE, ANY_TYPE, CLASS.mask | ANY.mask);
         Progress<Phase> progress = result.getProgress();
         List<Function> functions = result.getFunctions();
         Function constructor = generator.generate(result, NONE, TYPE_CONSTRUCTOR, NewInvocation.class, Object.class);
         Function hashCode = generator.generate(result, INTEGER, METHOD_HASH_CODE, HashCodeInvocation.class);
         Function toString = generator.generate(result, STRING, METHOD_TO_STRING, ToStringInvocation.class);
         Function equals = generator.generate(result, BOOLEAN, METHOD_EQUALS, EqualsInvocation.class, Object.class);
         Function wait = generator.generate(result, NONE, METHOD_WAIT, WaitInvocation.class);
         Function waitFor = generator.generate(result, NONE, METHOD_WAIT, WaitForInvocation.class, Long.class);
         Function notify = generator.generate(result, NONE, METHOD_NOTIFY, NotifyInvocation.class);
         Function notifyAll = generator.generate(result, NONE, METHOD_NOTIFY_ALL, NotifyAllInvocation.class);
         
         functions.add(constructor);
         functions.add(wait);
         functions.add(waitFor);
         functions.add(notify);
         functions.add(notifyAll);
         functions.add(hashCode);
         functions.add(equals);
         functions.add(toString);
         progress.done(COMPILE);
         reference.set(type);
         
         return result;
      }
      return type;
   }
   
   private static class NewInvocation implements Invocation<Object> {
      
      private final PrimitiveInstanceBuilder constructor;
      
      public NewInvocation() {
         this.constructor = new PrimitiveInstanceBuilder();
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Type real = (Type)list[0];
         Constraint constraint = Constraint.getConstraint(real);
         
         return constructor.create(scope, real);
      }
   }
   
   private static class WaitInvocation implements Invocation<Object> {
      
      public WaitInvocation() {
         super();
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         object.wait();
         return null;
      }
   }
   
   private static class WaitForInvocation implements Invocation<Object> {
      
      public WaitForInvocation() {
         super();
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Number argument = (Number)list[0];
         long time = argument.longValue();
         
         object.wait(time);
         return null;
      }
   }
   
   private static class NotifyInvocation implements Invocation<Object> {
      
      public NotifyInvocation() {
         super();
      }
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         object.notify();
         return null;
      }
   }
   
   private static class NotifyAllInvocation implements Invocation<Object> {
      
      public NotifyAllInvocation() {
         super();
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         object.notifyAll();
         return null;
      }
   }
   
   private static class HashCodeInvocation implements Invocation<Object> {
      
      public HashCodeInvocation() {
         super();
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         return object.hashCode();
      }
   }
   
   private static class EqualsInvocation implements Invocation<Object> {
      
      public EqualsInvocation() {
         super();
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         return object.equals(list[0]);
      }
   }
   
   private static class ToStringInvocation implements Invocation<Object> {
      
      public ToStringInvocation() {
         super();
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         return object + "@" + object.hashCode();
      }
   }
}