package org.ternlang.compile;

import static org.ternlang.core.Reserved.GRAMMAR_SCRIPT;

import org.ternlang.compile.assemble.Application;
import org.ternlang.core.Context;
import org.ternlang.core.ResourceManager;
import org.ternlang.core.error.ThreadExceptionHandler;
import org.ternlang.core.link.Package;
import org.ternlang.core.link.PackageLinker;
import org.ternlang.core.module.EmptyModule;
import org.ternlang.core.module.FilePathConverter;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.module.PathConverter;
import org.ternlang.core.type.extend.ModuleExtender;

public class ResourceCompiler implements Compiler {

   private final ThreadExceptionHandler handler;
   private final ModuleExtender extender;
   private final PathConverter converter;
   private final Context context;   
   private final Module empty;
   
   public ResourceCompiler(Context context) {
      this.extender = new ModuleExtender(context);
      this.handler = new ThreadExceptionHandler();
      this.converter = new FilePathConverter();
      this.empty = new EmptyModule(context);
      this.context = context;
   } 
   
   @Override
   public Executable compile(String resource) throws Exception {
      if(resource == null) {
         throw new NullPointerException("No resource provided");
      }
      ResourceManager manager = context.getManager();
      String source = manager.getString(resource);
      
      extender.extend(empty); // avoid a deadlock
      handler.register(); // catch rogue exceptions

      return compile(resource, source);
   } 
   
   private Executable compile(String resource, String source) throws Exception {
      if(source == null) {
         throw new IllegalArgumentException("Resource '" + resource + "' not found");
      }
      String module = converter.createModule(resource);
      Path path = converter.createPath(resource);
      PackageLinker linker = context.getLinker();
      Package library = linker.link(path, source, GRAMMAR_SCRIPT);
  
      return new Application(context, library, module);
   }
}