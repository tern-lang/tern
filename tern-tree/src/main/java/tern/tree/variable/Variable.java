package tern.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.error.ErrorHandler;
import tern.core.link.ImplicitImportLoader;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.scope.index.Address;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.LocalValueFinder;
import tern.core.variable.Value;
import tern.core.variable.bind.VariableBinder;
import tern.tree.NameReference;

public class Variable implements Compilation {
   
   private final NameReference reference;
   
   public Variable(Evaluation identifier) {
      this.reference = new NameReference(identifier);
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      ProxyWrapper wrapper = context.getWrapper();
      String name = reference.getName(scope);
      
      return new CompileResult(handler, wrapper, name);
   }
   
   private static class CompileResult extends Evaluation {
      
      private final AtomicReference<Address> location;
      private final ImplicitImportLoader loader;
      private final LocalValueFinder finder;
      private final VariableBinder binder;
      private final String name;
      
      public CompileResult(ErrorHandler handler, ProxyWrapper wrapper, String name) {
         this.binder = new VariableBinder(handler, wrapper, name);
         this.location = new AtomicReference<Address>();
         this.finder = new LocalValueFinder(name);
         this.loader = new ImplicitImportLoader();
         this.name = name;
      }
   
      @Override
      public void define(Scope scope) throws Exception{
         ScopeIndex index = scope.getIndex();
         Address address = index.get(name);
         
         if(address == null) {
            loader.loadImports(scope, name); // static reference
         } else {
            location.set(address);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception{
         Address address = location.get();
         Value value = finder.findValue(scope, address);
         
         if(value == null) {
            return binder.compile(scope);
         }
         return value.getConstraint();
      } 
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception{
         Address address = location.get();
         Value value = finder.findValue(scope, address);
         
         if(value == null) {
            return binder.bind(scope);
         }
         return value;
      } 
   }
}