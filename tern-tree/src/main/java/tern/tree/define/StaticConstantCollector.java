package tern.tree.define;

import static tern.core.Reserved.TYPE_CLASS;

import java.util.List;
import java.util.Set;

import tern.core.Context;
import tern.core.ModifierType;
import tern.core.module.Module;
import tern.core.property.Property;
import tern.core.property.PropertyValue;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class StaticConstantCollector {

   private final StaticConstantIndexer indexer;
   
   public StaticConstantCollector() {
      this.indexer = new StaticConstantIndexer(TYPE_CLASS);
   }
   
   public void collect(Type type) throws Exception {
      Module module = type.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> types = extractor.getTypes(type); // get hierarchy
      
      if(!types.isEmpty()) {
         Set<String> names = indexer.index(type);
         Scope scope = type.getScope();
         ScopeState state = scope.getState();
   
         for(Type next : types) {
            if(next != type) {
               List<Property> properties = next.getProperties();
               
               for(Property property : properties) {
                  String name = property.getName();
                  String alias = property.getAlias();
                  int modifiers = property.getModifiers();
                  
                  if(ModifierType.isStatic(modifiers)) {
                     PropertyValue value = new PropertyValue(property, null, name);
                     
                     if(names.add(alias)) { // ensure only supers are added
                        state.addValue(alias, value);
                     }
                  }
               }
            }
         }
      }
   }
}