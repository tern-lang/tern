package org.ternlang.tree.define;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ternlang.core.function.Function;
import org.ternlang.core.property.Property;
import org.ternlang.core.property.PropertyTableBuilder;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.index.FunctionPropertyCollector;

public class FunctionPropertyGenerator {
   
   private final FunctionPropertyCollector collector;
   private final PropertyTableBuilder builder;
   
   public FunctionPropertyGenerator(String... ignore) {
      this.builder = new PropertyTableBuilder(ignore, false);
      this.collector = new FunctionPropertyCollector();
   }
   
   public void generate(Type type) throws Exception {
      List<Function> functions = type.getFunctions();
      
      if(!functions.isEmpty()) {
         List<Property> properties = type.getProperties();
         Map<String, Property> available = builder.getProperties(type);
         Set<String> ignore = available.keySet();
         List<Property> extended = collector.collect(functions, ignore);
         
         if(!extended.isEmpty()) {
            properties.addAll(extended);
         }
      }
   }
}