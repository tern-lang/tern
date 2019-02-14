package org.ternlang.core.type.index;

import static org.ternlang.core.constraint.Constraint.TYPE;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.property.ClassProperty;
import org.ternlang.core.property.Property;
import org.ternlang.core.type.Type;

public class ClassPropertyBuilder {

   private final TypeIndexer indexer;
   
   public ClassPropertyBuilder(TypeIndexer indexer){
      this.indexer = indexer;
   }

   public List<Property> create(Class source) throws Exception {
      Type type = indexer.loadType(source);
      
      if(type == null) {
         throw new InternalStateException("Could not load type for " + source);
      }
      List<Property> properties = new ArrayList<Property>();
      Property property = new ClassProperty(type, TYPE);
      
      properties.add(property);
      
      return properties;        
   }
}