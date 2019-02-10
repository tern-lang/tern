package tern.core.property;

import static tern.core.type.Phase.DEFINE;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import tern.common.Progress;
import tern.core.EntityCache;
import tern.core.type.Phase;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class PropertyExtractor {

   private final EntityCache<Set<Property>> cache;
   private final TypeExtractor extractor;
   
   public PropertyExtractor(TypeExtractor extractor) {
      this.cache = new EntityCache<Set<Property>>();
      this.extractor = extractor;
   }
   
   public Set<Property> findProperties(Type type) {
      Set<Property> properties = cache.fetch(type);
      
      if(properties == null) {
         Progress<Phase> progress = type.getProgress();
         Set<Property> hierarchy = findHierarchy(type);
         
         if(progress.pass(DEFINE)) {
            cache.cache(type, hierarchy);
         }
         return hierarchy;
      }
      return properties;
   }
   
   private Set<Property> findHierarchy(Type type) {
      Set<Type> types = extractor.getTypes(type);
      
      if(!types.isEmpty()) {
         Set<String> done = new LinkedHashSet<String>();
         Set<Property> result = new LinkedHashSet<Property>();
         
         for(Type base : types) {
            Set<Property> map = findProperties(base, done);
            result.addAll(map);
         }
         return result;
      }
      return Collections.emptySet();
   }
   
   private Set<Property> findProperties(Type type, Set<String> done) {
      List<Property> properties = type.getProperties();
      
      if(!properties.isEmpty()) {
         Set<Property> result = new LinkedHashSet<Property>();
         
         for(Property property : properties) {
            if(property != null) {
               String name = property.getName();

               if (done.add(name)) {
                  result.add(property);
               }
            }
         }
         return result;
      }
      return Collections.emptySet();
   }
}
