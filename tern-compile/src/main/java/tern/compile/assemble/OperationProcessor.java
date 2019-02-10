package tern.compile.assemble;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.module.FilePathConverter;
import tern.core.module.Module;
import tern.core.module.ModuleRegistry;
import tern.core.module.Path;
import tern.core.module.PathConverter;
import tern.parse.Line;

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