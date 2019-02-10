package tern.tree.define;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tern.core.ModifierType;
import tern.core.type.Type;
import tern.core.property.Property;

public class StaticConstantIndexer {
   
   private final String[] reserved;
   
   public StaticConstantIndexer(String... reserved) {
      this.reserved = reserved;
   }
   
   public Set<String> index(Type type) {
      Set<String> names = new HashSet<String>();
      
      if(type != null) {
         List<Property> properties = type.getProperties();

         for(Property property : properties) {
            int modifiers = property.getModifiers();
            String name = property.getName();
            
            if(ModifierType.isStatic(modifiers)) {
               names.add(name);
            }
         }
      }
      for(String name : reserved) {
         names.add(name);
      }
      return names;
   }
}