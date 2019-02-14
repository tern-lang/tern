package org.ternlang.core.property;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ternlang.common.Cache;
import org.ternlang.common.LazyBuilder;
import org.ternlang.common.LazyCache;
import org.ternlang.core.Context;
import org.ternlang.core.ModifierType;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class PropertyTableBuilder {
   
   private final Cache<Type, Map<String, Property>> cache;
   private final PropertyCollector collector;   
   
   public PropertyTableBuilder(String[] ignores, boolean complete) {
      this.collector = new PropertyCollector(ignores, complete);
      this.cache = new LazyCache<Type, Map<String, Property>>(collector);
   }
   
   public Map<String, Property> getProperties(Type type) {
      return cache.fetch(type);
   }

   private static class PropertyCollector implements LazyBuilder<Type, Map<String, Property>> {
      
      private final PropertyLocator locator;
      private final String[] ignores;
      
      public PropertyCollector(String[] ignores, boolean complete) {
         this.locator = new PropertyLocator(complete);
         this.ignores = ignores;
      }

      @Override
      public Map<String, Property> create(Type type) {
         Collection<Property> properties = locator.getProperties(type);
         int count = properties.size();
         
         if(count > 0) {
            Map<String, Property> index = new HashMap<String, Property>(count);
            
            for(Property property : properties) {
               int modifiers = property.getModifiers();
               String name = property.getName();               
               
               if(!ModifierType.isFunction(modifiers) && !ModifierType.isPrivate(modifiers)) {
                  index.put(name, property);
               }
            }
            if(ignores != null) {
               for(String ignore : ignores) {
                  index.remove(ignore);
               }
            }
            return index;
         }
         return Collections.emptyMap();         
      }      
   }  
   
   private static class PropertyLocator {
      
      private final boolean complete;      
      
      public PropertyLocator(boolean complete) {
         this.complete = complete;
      }
      
      public Collection<Property> getProperties(Type type) {
         if(complete) {
            Scope scope = type.getScope();
            Module module = scope.getModule();
            Context context = module.getContext();
            TypeExtractor extractor = context.getExtractor();
            
            return extractor.getProperties(type);
         }
         return type.getProperties();
      }
      
   }
}
