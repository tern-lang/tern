package org.ternlang.core.scope.index;

import static org.ternlang.core.scope.index.AddressType.LOCAL;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ternlang.common.ArrayStack;
import org.ternlang.common.CompoundIterator;
import org.ternlang.common.EmptyIterator;
import org.ternlang.common.Stack;
import org.ternlang.core.scope.Scope;

public class StackIndex implements ScopeIndex {

   private StackResolver resolver;
   private Scope scope;

   public StackIndex(Scope scope) {
      this.scope = scope;
   }

   @Override
   public Iterator<Address> iterator() {
      if (resolver == null) {
         return new EmptyIterator<Address>();
      }
      return resolver.iterator();
   }

   @Override
   public Address get(String name) {
      if (resolver == null) {
         resolver = new StackResolver(scope);
      }
      return resolver.resolve(name);
   }

   @Override
   public Address index(String name) {
      if (resolver == null) {
         resolver = new StackResolver(scope);
      }
      return resolver.index(name);
   }

   @Override
   public boolean contains(String name) {
      if (resolver == null) {
         resolver = new StackResolver(scope);
      }
      return resolver.contains(name);
   }

   @Override
   public void reset(int value) {
      if (resolver != null) {
         resolver.reset(value);
      }
   }

   @Override
   public int size() {
      if (resolver != null) {
         return resolver.size();
      }
      return 0;
   }

   @Override
   public String toString() {
      return String.valueOf(resolver);
   }

   private static class StackResolver {

      private final Map<String, Address> externals;
      private final Map<String, Address> locals;
      private final AddressResolver resolver;
      private final Stack<String> stack;

      public StackResolver(Scope scope) {
         this.resolver = new AddressResolver(scope);
         this.externals = new LinkedHashMap<String, Address>();
         this.locals = new LinkedHashMap<String, Address>();
         this.stack = new ArrayStack<String>(0); // do not use default
      }

      public Iterator<Address> iterator() {
         Iterator<Address> local = locals.values().iterator();
         Iterator<Address> reference = externals.values().iterator();

         return new CompoundIterator(local, reference);
      }

      public Address resolve(String name) {
         Address address = locals.get(name);

         if (address == null) {
            address = externals.get(name);
         }
         if (address == null) {
            int count = externals.size();
            Address external = resolver.resolve(name, count);

            if (external != null) {
               externals.put(name, external);
               return external;
            }
         }
         return address;
      }

      public Address index(String name) {
         Address address = locals.get(name);

         if (address != null) {
            throw new IllegalStateException("Duplicate variable '" + name + "' in scope");
         }
         int size = locals.size();
         Address local = LOCAL.getAddress(name, size);

         if(name != null) {
            locals.put(name, local);
            stack.push(name);
         }
         return local;
      }

      public boolean contains(String name) {
         return locals.containsKey(name);
      }

      public void reset(int index) {
         int size = locals.size();

         for (int i = size; i > index; i--) {
            String name = stack.pop();
            locals.remove(name);
         }
      }

      public int size() {
         return locals.size();
      }

      @Override
      public String toString() {
         return String.valueOf(locals);
      }
   }
}