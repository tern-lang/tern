package tern.tree.collection;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import tern.core.Context;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.array.ArrayBuilder;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.error.InternalArgumentException;

public class IterationConverter {

   private final ArrayBuilder builder;
   
   public IterationConverter() {
      this.builder = new ArrayBuilder();
   }
   
   public Iteration convert(Scope scope, Object value) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Class type = value.getClass();
   
      if(type.isArray()) {
         return new ArrayIteration(wrapper, value);
      } 
      if(Sequence.class.isInstance(value)) {
         return new SequenceIteration(value);
      }
      if(Iterable.class.isInstance(value)) {
         return new IterableIteration(wrapper, value);
      } 
      if(Map.class.isInstance(value)) {
         return new MapIteration(wrapper, value);
      }
      if(Enumeration.class.isInstance(value)) {
         return new EnumerationIteration(wrapper, value);
      }
      throw new InternalArgumentException("Iteration for " + type + " is not possible");
   }
   
   private class ArrayIteration implements Iteration {
      
      private final ProxyWrapper wrapper;
      private final Object value;
      
      public ArrayIteration(ProxyWrapper wrapper, Object value) {
         this.wrapper = wrapper;
         this.value = value;
      }
      
      @Override
      public Type getEntry(Scope scope) throws Exception {
         Module module = scope.getModule();
         Class type = value.getClass();
         Class entry = type.getComponentType();
         
         return module.getType(entry);
      }
      
      @Override
      public Iterable getIterable(Scope scope) throws Exception {
         List list = builder.convert(value);
         
         if(!list.isEmpty()) {
            return new ProxyIterable(wrapper, list);
         }
         return list;
      }
   }
   
   private class MapIteration implements Iteration {
      
      private final ProxyWrapper wrapper;
      private final Object value;
      
      public MapIteration(ProxyWrapper wrapper, Object value) {
         this.wrapper = wrapper;
         this.value = value;
      }
      
      @Override
      public Type getEntry(Scope scope) throws Exception {
         Module module = scope.getModule();
         return module.getType(Entry.class);
      }
      
      @Override
      public Iterable getIterable(Scope scope) throws Exception {
         Map map = (Map)value;
         Set set = map.entrySet();
         
         if(!set.isEmpty()) {
            return new ProxyIterable(wrapper, set);
         }
         return set;
      }
   }
   
   private class SequenceIteration implements Iteration {
      
      private final Object value;
      
      public SequenceIteration(Object value) {
         this.value = value;
      }
      
      @Override
      public Type getEntry(Scope scope) throws Exception {
         Module module = scope.getModule();
         return module.getType(Number.class);
      }
      
      @Override
      public Iterable getIterable(Scope scope) throws Exception {
         if(value != null) {
            return (Iterable)value;
         }
         return Collections.emptyList();
      }
   }
   
   private class IterableIteration implements Iteration {
      
      private final ProxyWrapper wrapper;
      private final Object value;
      
      public IterableIteration(ProxyWrapper wrapper, Object value) {
         this.wrapper = wrapper;
         this.value = value;
      }
      
      @Override
      public Type getEntry(Scope scope) throws Exception {
         Module module = scope.getModule();
         return module.getType(Object.class);
      }
      
      @Override
      public Iterable getIterable(Scope scope) throws Exception {
         Iterable iterable = (Iterable)value;
         
         if(value != null) {
            return new ProxyIterable(wrapper, iterable);
         }
         return Collections.emptyList();
      }
   }
   
   private class EnumerationIteration implements Iteration {
      
      private final ProxyWrapper wrapper;
      private final Object value;
      
      public EnumerationIteration(ProxyWrapper wrapper, Object value) {
         this.wrapper = wrapper;
         this.value = value;
      }
      
      @Override
      public Type getEntry(Scope scope) throws Exception {
         Module module = scope.getModule();
         return module.getType(Object.class);
      }
      
      @Override
      public Iterable getIterable(Scope scope) throws Exception {
         Enumeration list = (Enumeration)value;
         
         if(list != null) {
            EnumerationIterable iterable = new EnumerationIterable(list);
            ProxyIterable proxy = new ProxyIterable(wrapper, iterable);
            
            return proxy;
         }
         return Collections.emptyList();
      }
      
      private class EnumerationIterable implements Iterable {
         
         private final Enumeration list;
         
         public EnumerationIterable(Enumeration list) {
            this.list = list;
         }
         
         @Override
         public Iterator iterator() {
            return new EnumerationIterator(list);
         }
      }
      
      private class EnumerationIterator implements Iterator {
         
         private final Enumeration list;
         
         public EnumerationIterator(Enumeration list) {
            this.list = list;
         }

         @Override
         public boolean hasNext() {
            return list.hasMoreElements();
         }

         @Override
         public Object next() {
            return list.nextElement();
         }
         
         @Override
         public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
         }
      }
   }
}