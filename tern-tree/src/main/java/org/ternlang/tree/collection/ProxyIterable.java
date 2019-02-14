package org.ternlang.tree.collection;

import java.util.Iterator;
import java.util.Map.Entry;

import org.ternlang.core.convert.proxy.ProxyWrapper;

public class ProxyIterable implements Iterable {

   private final ProxyWrapper wrapper;
   private final Iterable iterable;
   
   public ProxyIterable(ProxyWrapper wrapper, Iterable iterable) {
      this.iterable = iterable;
      this.wrapper = wrapper;
   }
   
   @Override
   public Iterator iterator() {
      Iterator iterator = iterable.iterator();
      return new ProxyIterator(iterator);
   }
   
   private class ProxyIterator implements Iterator {
      
      private final Iterator iterator;
      
      public ProxyIterator(Iterator iterator) {
         this.iterator = iterator;
      }

      @Override
      public boolean hasNext() {
         return iterator.hasNext();
      }

      @Override
      public Object next() {
         Object value = iterator.next();
         
         if(value != null) {
            if(Entry.class.isInstance(value)) {
               return new ProxyEntry((Entry)value);
            }
         }
         return wrapper.fromProxy(value);
      }
      
      @Override
      public void remove() {
         iterator.remove();
      }
   }
   
   private class ProxyEntry implements Entry {
      
      private final Entry entry;
      
      public ProxyEntry(Entry entry) {
         this.entry = entry;
      }
      
      @Override
      public Object getKey() {
         Object key = entry.getKey();
         
         if(key != null) {
            return wrapper.fromProxy(key);
         }
         return key;
      }
      
      @Override
      public Object getValue() {
         Object value = entry.getValue();
         
         if(value != null) {
            return wrapper.fromProxy(value);
         }
         return value;
      }

      @Override
      public Object setValue(Object value) { 
         Object proxy = wrapper.toProxy(value);
         Object previous = entry.setValue(proxy);
         
         if(previous != null) {
            return wrapper.fromProxy(previous);
         }
         return previous;
      }
   }
}