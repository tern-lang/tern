package tern.tree.function;

import static tern.core.scope.index.AddressType.INSTANCE;
import static tern.core.scope.index.AddressType.MODULE;
import static tern.core.scope.index.AddressType.STATIC;
import static tern.core.scope.index.AddressType.TYPE;

import tern.core.Context;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.error.ErrorHandler;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.scope.index.Address;
import tern.core.scope.index.AddressType;
import tern.core.variable.Value;
import tern.core.variable.bind.VariableBinder;

public class ScopeAllocationBuilder {

   public ScopeAllocationBuilder() {
      super();
   }

   public ScopeAllocation allocate(Scope scope, Address address) throws Exception {
      AddressType type = address.getType();
      ScopeMatcher matcher = match(scope, address);

      if(type == INSTANCE) {
         return new ScopeAllocation(matcher, address, false);
      }
      if(type == STATIC) {
         return new ScopeAllocation(matcher, address, true);
      }
      if(type == TYPE) {
         return new ScopeAllocation(matcher, address, true);
      }
      if(type == MODULE) {
         return new ScopeAllocation(matcher, address, true);
      }
      return null;
   }

   private ScopeMatcher match(Scope scope, Address address) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      ProxyWrapper wrapper = context.getWrapper();
      AddressType type = address.getType();
      String name = address.getName();

      if(type == INSTANCE) {
         return new StateMatcher(handler, wrapper, name);
      }
      if(type == STATIC) {
         return new StateMatcher(handler, wrapper, name);
      }
      if(type == TYPE) {
         return new StaticMatcher(handler, wrapper, name);
      }
      if(type == MODULE) {
         return new StaticMatcher(handler, wrapper, name);
      }
      return null;
   }

   private static class StateMatcher implements ScopeMatcher {
      
      private final VariableBinder binder;   
      private final String name;
      
      public StateMatcher(ErrorHandler handler, ProxyWrapper wrapper, String name) {
         this.binder = new VariableBinder(handler, wrapper, name);
         this.name = name;
      }
      
      @Override
      public Value compile(Scope scope) throws Exception {
         ScopeState state = scope.getState();         
         return state.getValue(name);
      }
      
      @Override
      public Value execute(Scope scope) throws Exception {
         return binder.bind(scope);
      }
   }

   private static class StaticMatcher implements ScopeMatcher {
      
      private final VariableBinder binder;   
      
      public StaticMatcher(ErrorHandler handler, ProxyWrapper wrapper, String name) {
         this.binder = new VariableBinder(handler, wrapper, name);         
      }
      
      @Override
      public Value compile(Scope scope) throws Exception {
         return binder.bind(scope);
      }
      
      @Override
      public Value execute(Scope scope) throws Exception {
         return binder.bind(scope);

      }
   }
}
