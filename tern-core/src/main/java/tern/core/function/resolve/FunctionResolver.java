package tern.core.function.resolve;

import tern.core.convert.proxy.Delegate;
import tern.core.function.index.DelegateIndexer;
import tern.core.function.index.FunctionIndexer;
import tern.core.function.index.FunctionPointer;
import tern.core.function.index.LocalIndexer;
import tern.core.function.index.ModuleIndexer;
import tern.core.function.index.TypeInstanceIndexer;
import tern.core.function.index.TypeStaticIndexer;
import tern.core.function.index.ValueIndexer;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.stack.ThreadStack;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;
import tern.core.variable.Value;

public class FunctionResolver {   
   
   private final TypeInstanceIndexer instances;
   private final TypeStaticIndexer statics;
   private final DelegateIndexer delegates;
   private final ModuleIndexer modules;
   private final ValueIndexer values;
   private final LocalIndexer scopes;
   
   public FunctionResolver(TypeExtractor extractor, ThreadStack stack, FunctionIndexer indexer) {
      this.instances = new TypeInstanceIndexer(extractor, indexer);
      this.statics = new TypeStaticIndexer(extractor, stack);
      this.delegates = new DelegateIndexer(extractor, stack);
      this.modules = new ModuleIndexer(extractor, stack);
      this.scopes = new LocalIndexer(stack, indexer);
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