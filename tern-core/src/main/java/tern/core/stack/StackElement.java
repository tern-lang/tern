package tern.core.stack;

import tern.core.NameFormatter;
import tern.core.function.Function;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.trace.Trace;
import tern.core.type.Type;

public class StackElement {
   
   private static final String MAIN_FUNCTION = "main";
   
   private final NameFormatter formatter;
   private final Function function;
   private final Trace trace;
   
   public StackElement(Trace trace) {
      this(trace, null);
   }
   
   public StackElement(Trace trace, Function function) {
      this.formatter = new NameFormatter();
      this.function = function;
      this.trace = trace;
   }
   
   public StackTraceElement build() {
      Module module = trace.getModule();
      String name = module.getName();
      Path path = trace.getPath();
      int line = trace.getLine();
      
      return create(name, path, line);
   }
   
   private StackTraceElement create(String module, Path path, int line) {
      String resource = path.getPath();
      
      if(function != null) {
         String name = function.getName();
         Type source = function.getSource();
         
         if(source != null) {
            Module parent = source.getModule();
            String prefix = parent.getName();
            String suffix = source.getName(); // module functions have no type name
            String qualifier = formatter.formatFullName(prefix, suffix);
            
            return new StackTraceElement(qualifier, name, resource, line);
         }
         return new StackTraceElement(module, name, resource, line);
      }
      return new StackTraceElement(module, MAIN_FUNCTION, resource, line);
   }
}