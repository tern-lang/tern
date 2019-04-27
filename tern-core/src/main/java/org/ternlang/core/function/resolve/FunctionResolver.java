package org.ternlang.core.function.resolve;

import org.ternlang.core.convert.proxy.Delegate;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.function.index.DelegateIndexer;
import org.ternlang.core.function.index.FunctionIndexer;
import org.ternlang.core.function.index.FunctionPointer;
import org.ternlang.core.function.index.LocalIndexer;
import org.ternlang.core.function.index.ModuleIndexer;
import org.ternlang.core.function.index.TypeInstanceIndexer;
import org.ternlang.core.function.index.TypeStaticIndexer;
import org.ternlang.core.function.index.ValueIndexer;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.variable.Value;

public class FunctionResolver {   
   
   private final TypeInstanceIndexer instances;
   private final TypeStaticIndexer statics;
   private final DelegateIndexer delegates;
   private final ModuleIndexer modules;
   private final ValueIndexer values;
   private final LocalIndexer scopes;
   
   public FunctionResolver(TypeExtractor extractor, ProxyWrapper wrapper, ThreadStack stack, FunctionIndexer indexer) {
      this.instances = new TypeInstanceIndexer(extractor, indexer);
      this.statics = new TypeStaticIndexer(extractor, stack);
      this.delegates = new DelegateIndexer(extractor, stack);
      this.modules = new ModuleIndexer(extractor, stack);
      this.scopes = new LocalIndexer(wrapper, stack, indexer);
      this.values = new ValueIndexer(stack);
   }

   public FunctionCall resolveInstance(Scope scope, Type source, String name, Type... list) throws Exception {
      FunctionPointer pointer = instances.index(source, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }

   public FunctionCall resolveInstance(Scope scope, Object source, String name, Object... list) throws Exception {
      FunctionPointer pointer = instances.index(source, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }
   
   public FunctionCall resolveStatic(Scope scope, Type type, String name, Type... list) throws Exception {
      FunctionPointer pointer = statics.index(type, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }
   
   public FunctionCall resolveStatic(Scope scope, Type type, String name, Object... list) throws Exception {
      FunctionPointer pointer = statics.index(type, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }

   public FunctionCall resolveModule(Scope scope, Module module, String name, Type... list) throws Exception {
      FunctionPointer pointer = modules.index(module, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }

   public FunctionCall resolveModule(Scope scope, Module module, String name, Object... list) throws Exception {
      FunctionPointer pointer = modules.index(module, name, list);

      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }
   
   public FunctionCall resolveFunction(Scope scope, Type delegate, String name, Type... list) throws Exception {
      FunctionPointer pointer = delegates.match(delegate, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }
   
   public FunctionCall resolveFunction(Scope scope, Delegate delegate, String name, Object... list) throws Exception {
      FunctionPointer pointer = delegates.match(delegate, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }
   
   public FunctionCall resolveScope(Scope scope, String name, Type... list) throws Exception { // function variable
      FunctionPointer pointer = scopes.index(scope, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }
   
   public FunctionCall resolveScope(Scope scope, String name, Object... list) throws Exception { // function variable
      FunctionPointer pointer = scopes.index(scope, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }
   
   public FunctionCall resolveValue(Value value, Object... list) throws Exception { // closures
      FunctionPointer pointer = values.index(value, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer);
      }
      return null;
   }
}