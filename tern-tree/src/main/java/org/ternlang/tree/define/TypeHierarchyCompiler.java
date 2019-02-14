package org.ternlang.tree.define;

import static org.ternlang.core.Reserved.ENUM_NAME;
import static org.ternlang.core.Reserved.ENUM_ORDINAL;
import static org.ternlang.core.Reserved.ENUM_VALUES;
import static org.ternlang.core.Reserved.TYPE_CLASS;

import java.util.Map;
import java.util.Set;

import org.ternlang.core.property.Property;
import org.ternlang.core.property.PropertyTableBuilder;
import org.ternlang.core.type.Type;

public class TypeHierarchyCompiler {
   
   private final PropertyTableBuilder builder;
   
   public TypeHierarchyCompiler() {
      this(TYPE_CLASS, ENUM_NAME, ENUM_ORDINAL, ENUM_VALUES);
   }
   
   public TypeHierarchyCompiler(String... ignores) {
      this.builder = new PropertyTableBuilder(ignores, true);
   }

   public void compile(Type type, Type base) {
      Map<String, Property> other = builder.getProperties(base);
      Map<String, Property> all = builder.getProperties(type);
      Set<String> names = other.keySet();
      
      for(String name : names) {
         Property actual = other.get(name);
         Property duplicate = all.get(name);
         
         if(duplicate != null && duplicate != actual) {
            throw new IllegalStateException("Type '" + type + "' has a duplicate property '" + name + "'");
         }
      }
   }
}
