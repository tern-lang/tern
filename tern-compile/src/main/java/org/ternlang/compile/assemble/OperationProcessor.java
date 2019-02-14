package org.ternlang.compile.assemble;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.module.FilePathConverter;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.ModuleRegistry;
import org.ternlang.core.module.Path;
import org.ternlang.core.module.PathConverter;
import org.ternlang.parse.Line;

public class OperationProcessor {

   private final PathConverter converter;
   private final Context context;
   
   public OperationProcessor(Context context) {
      this.converter = new FilePathConverter();
      this.context = context;
   }
   
   public Object process(Object value, Line line) throws Exception {
      if(Compilation.class.isInstance(value)) {
         Compilation compilation = (Compilation)value;
         String resource = line.getResource();
         Path path = converter.createPath(resource);
         String name = converter.createModule(resource);
         ModuleRegistry registry = context.getRegistry();
         Module module = registry.addModule(name);
         int number = line.getNumber();
         
         return compilation.compile(module, path, number);
      }
      return value;
   }
}