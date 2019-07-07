package org.ternlang.tree.function;

import static org.ternlang.core.scope.index.AddressType.INSTANCE;
import static org.ternlang.core.scope.index.AddressType.MODULE;
import static org.ternlang.core.scope.index.AddressType.STATIC;
import static org.ternlang.core.scope.index.AddressType.TYPE;

import org.ternlang.core.Context;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.AddressType;
import org.ternlang.core.variable.Value;
import org.ternlang.core.variable.bind.VariableBinder;

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
         return new InstanceMatcher(handler, wrapper, name);
      }
      if(type == STATIC) {
         return new StateMatcher(handler, wrapper, name);
      }
      if(type == TYPE) {
         return new ConstantMatcher(handler, wrapper, name);
      }
      if(type == MODULE) {
         return new ConstantMatcher(handler, wrapper, name);
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
   
   private static class InstanceMatcher implements ScopeMatcher {
      
      private final VariableBinder binder;   
      private final String name;
      
      public InstanceMatcher(ErrorHandler handler, ProxyWrapper wrapper, String name) {
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
         Scope parent = scope.getParent();
         return binder.bind(parent);
      }
   }

   private static class ConstantMatcher implements ScopeMatcher {
      
      private final VariableBinder binder;   
      
      public ConstantMatcher(ErrorHandler handler, ProxyWrapper wrapper, String name) {
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
