package tern.compile;

import static tern.core.Reserved.DEFAULT_PACKAGE;
import static tern.core.Reserved.GRAMMAR_SCRIPT;

import tern.compile.assemble.Application;
import tern.core.Context;
import tern.core.link.Package;
import tern.core.link.PackageLinker;
import tern.core.module.FilePathConverter;
import tern.core.module.Path;
import tern.core.module.PathConverter;

public class StringCompiler implements Compiler {
   
   private final PathConverter converter;
   private final Context context;   
   private final String module;
   
   public StringCompiler(Context context) {
      this(context, DEFAULT_PACKAGE);
   }
   
   public StringCompiler(Context context, String module) {
      this.converter = new FilePathConverter();
      this.context = context;
      this.module = module;
   } 
   
   @Override
   public Executable compile(String source) throws Exception {
      if(source == null) {
         throw new NullPointerException("No source provided");
      }
      Path path = converter.createPath(module);
      PackageLinker linker = context.getLinker();
      Package library = linker.link(path, source, GRAMMAR_SCRIPT);
      
      return new Application(context, library, module);
   } 
}