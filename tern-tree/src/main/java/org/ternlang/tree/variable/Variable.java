package org.ternlang.tree.variable;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.link.ImplicitImportLoader;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.LocalValueFinder;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.variable.Value;
import org.ternlang.core.variable.bind.VariableBinder;
import org.ternlang.tree.NameReference;

import java.util.concurrent.atomic.AtomicReference;

public class Variable implements Compilation {
   
   private final NameReference reference;
   private final boolean expand;
   
   public Variable(Evaluation identifier) {
      this(identifier, false);
   }

   public Variable(Evaluation identifier, boolean expand) {
      this.reference = new NameReference(identifier);
      this.expand = expand;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      ProxyWrapper wrapper = context.getWrapper();
      String name = reference.getName(scope);
      
      return new CompileResult(handler, wrapper, name, expand);
   }
   
   private static class CompileResult extends Evaluation {
      
      private final AtomicReference<Address> location;
      private final ImplicitImportLoader loader;
      private final LocalValueFinder finder;
      private final VariableBinder binder;
      private final String name;
      private final boolean expand;
      
      public CompileResult(ErrorHandler handler, ProxyWrapper wrapper, String name, boolean expand) {
         this.binder = new VariableBinder(handler, wrapper, name);
         this.location = new AtomicReference<Address>();
         this.finder = new LocalValueFinder(name);
         this.loader = new ImplicitImportLoader();
         this.expand = expand;
         this.name = name;
      }

      @Override
      public boolean expansion(Scope scope) throws Exception {
         return expand;
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