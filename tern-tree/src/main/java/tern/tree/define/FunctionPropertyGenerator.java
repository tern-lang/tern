package tern.tree.define;

import java.util.List;
import java.util.Map;
import java.util.Set;

import tern.core.function.Function;
import tern.core.property.Property;
import tern.core.property.PropertyTableBuilder;
import tern.core.type.Type;
import tern.core.type.index.FunctionPropertyCollector;

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