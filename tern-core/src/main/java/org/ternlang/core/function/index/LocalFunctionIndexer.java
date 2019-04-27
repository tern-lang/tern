package org.ternlang.core.function.index;

import static org.ternlang.core.Reserved.TYPE_CLASS;
import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.TypeParameterConstraint;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class LocalFunctionIndexer {

   private final FunctionIndexer indexer;
   private final LocalTypeFinder finder;
   
   public LocalFunctionIndexer(ProxyWrapper wrapper, FunctionIndexer indexer) {
      this.finder = new LocalTypeFinder(wrapper);
      this.indexer = indexer;
   }
   
   public FunctionPointer index(Scope scope, String name, Type... types) throws Exception { 
      Type type = finder.findType(scope, name);
      
      if(type != null) {
         return index(scope, type, types);
      }
      return null;
   }
   
   public FunctionPointer index(Scope scope, Type type, Type... types) throws Exception { 
      if(type != null) {
         FunctionPointer pointer = resolve(type, TYPE_CONSTRUCTOR, types);
         
         if(pointer != null) {
            return new ConstructorPointer(pointer, type);
         }
      }
      return null;
   }
   
   private FunctionPointer resolve(Type type, String name, Type... types) throws Exception { 
      Module module = type.getModule();
      Class real = type.getType();
      
      if(real == null) {
         Type[] array = new Type[types.length + 1];
         
         for(int i = 0; i < types.length; i++) {
            array[i + 1] = types[i];
         }
         array[0] = module.getType(Type.class);
         types = array;
      }
      return indexer.index(type, name, types);      
   }
   
   public FunctionPointer index(Scope scope, String name, Object... values) throws Exception {
      Type type = finder.findType(scope, name);
      
      if(type != null) {
         return index(scope, type, values);
      }
      return null;
   }
   
   public FunctionPointer index(Scope scope, Type type, Object... values) throws Exception { 
      if(type != null) {
         FunctionPointer pointer = resolve(type, TYPE_CONSTRUCTOR, values);
         
         if(pointer != null) {
            return new ConstructorPointer(pointer, type);
         }
      }
      return null;
   }
   
   private FunctionPointer resolve(Type type, String name, Object... values) throws Exception {
      Class real = type.getType();
         
      if(real == null) {
         Object[] array = new Object[values.length + 1];
         
         for(int i = 0; i < values.length; i++) {
            array[i + 1] = values[i];
         }
         array[0] = type;
         values = array;
      }
      return indexer.index(type, TYPE_CONSTRUCTOR, values);      
   }
   
   private static class ConstructorPointer implements FunctionPointer {
      
      private final FunctionPointer pointer;
      private final Type type;
      
      public ConstructorPointer(FunctionPointer pointer, Type type) {
         this.pointer = pointer;
         this.type = type;
      }

      @Override
      public ReturnType getType(Scope scope) {
         ReturnType delegate = pointer.getType(scope);
         Class real = type.getType();
         
         if(real == null) {
            return new ConstructorType(delegate, type);
         }
         return delegate;
      }

      @Override
      public Invocation getInvocation() {
         Invocation delegate = pointer.getInvocation();
         Class real = type.getType();
         
         if(real == null) {
            return new ConstructorInvocation(delegate, type);
         }
         return delegate;
      }

      @Override
      public Function getFunction() {
         return pointer.getFunction();
      }
      
      @Override
      public Retention getRetention() {
         return pointer.getRetention();
      }
   }
   
   private static class ConstructorType implements ReturnType {
      
      private final ReturnType delegate;
      private final Constraint type;
      
      public ConstructorType(ReturnType delegate, Type type) {
         this.type = new TypeParameterConstraint(type, TYPE_CLASS);
         this.delegate = delegate;
      }

      @Override
      public Constraint check(Constraint left, Constraint[] list) throws Exception {
         Constraint[] array = new Constraint[list.length + 1];
         
         for(int i = 0; i < list.length; i++) {
            array[i + 1] = list[i];
         }
         array[0] = type; 
         return delegate.check(left, array);
      }
   }
   
   private static class ConstructorInvocation implements Invocation {
      
      private final Invocation delegate;
      private final Type type;
      
      public ConstructorInvocation(Invocation delegate, Type type) {
         this.delegate = delegate;
         this.type = type;
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Object[] array = new Object[list.length + 1];
         
         for(int i = 0; i < list.length; i++) {
            array[i + 1] = list[i];
         }
         array[0] = type;         
         return delegate.invoke(scope, null, array);
      }
   }
}
