package org.ternlang.core.stack;

import static org.ternlang.core.Reserved.METHOD_MAIN;

import org.ternlang.core.NameFormatter;
import org.ternlang.core.function.Function;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.type.Type;

public class StackElement {
   
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
      return new StackTraceElement(module, METHOD_MAIN, resource, line);
   }
}