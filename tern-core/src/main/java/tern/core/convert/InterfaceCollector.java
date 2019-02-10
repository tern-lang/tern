package tern.core.convert;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tern.core.Any;
import tern.core.Context;
import tern.core.EntityCache;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class InterfaceCollector {

   private final EntityCache<Class[]> cache;
   private final Class[] include;
   private final Class[] empty;
   
   public InterfaceCollector(Class... include) {
      this.cache = new EntityCache<Class[]>();
      this.empty = new Class[]{};
      this.include = include;
   }
   
   public Class[] collect(Scope scope) {
      Type type = scope.getHandle();
      
      if(type != null) {
         return collect(type);
      }
      return empty;
   }
   
   public Class[] collect(Type type) {
      Class[] interfaces = cache.fetch(type);
      
      if(interfaces == null) {
         Set<Class> types = traverse(type);
         Class[] result = convert(types);
         
         cache.cache(type, result);
         return result;
      }
      return interfaces;
   }
   
   public Class[] collect(Class... types) {
      if(types.length > 0) {
         Set<Class> interfaces = new HashSet<Class>();
         
         for(Class entry : types) {
            if(entry != null) {
               if(entry.isInterface()) {
                  interfaces.add(entry);
               }
            }
         }
         return convert(interfaces);
      }
      return empty;
   }
   
   private Set<Class> traverse(Type type) {
      Module module = type.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> types = extractor.getTypes(type);
      
      if(!types.isEmpty()) {
         Set<Class> interfaces = new HashSet<Class>();
      
         for(Type entry : types) {
            Class part = entry.getType();
            
            if(part != null) {
               int modifiers = part.getModifiers();
               
               if(part.isInterface() && Modifier.isPublic(modifiers)) {
                  interfaces.add(part);
               }
            }
         }
         for(Class entry : include) {
            interfaces.add(entry);
         }
         interfaces.add(Any.class);
         return interfaces;
      }
      return Collections.<Class>singleton(Any.class);
   }

   private Class[] convert(Set<Class> types) {
      int size = types.size();

      if(size > 0) {
         Class[] array = new Class[size];
         int index = 0;

         for(Class type : types) {
            array[index++] = type;
         }
         return array;
      }
      return empty;
   }
}