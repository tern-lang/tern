package org.ternlang.compile;

import static org.ternlang.core.Reserved.DEFAULT_PACKAGE;
import static org.ternlang.core.Reserved.GRAMMAR_SCRIPT;

import org.ternlang.compile.assemble.Application;
import org.ternlang.core.Context;
import org.ternlang.core.link.Package;
import org.ternlang.core.link.PackageLinker;
import org.ternlang.core.module.FilePathConverter;
import org.ternlang.core.module.Path;
import org.ternlang.core.module.PathConverter;

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