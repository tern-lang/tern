package org.ternlang.compile.assemble;

import static org.ternlang.core.Reserved.GRAMMAR_PACKAGE;

import java.util.concurrent.Executor;

import org.ternlang.common.Cache;
import org.ternlang.common.LeastRecentlyUsedCache;
import org.ternlang.core.Context;
import org.ternlang.core.link.Package;
import org.ternlang.core.link.PackageLinker;
import org.ternlang.core.module.Path;

public class ApplicationLinker implements PackageLinker {
   
   private final Cache<Path, Package> cache;
   private final PackageBuilder builder;  
   
   public ApplicationLinker(Context context, Executor executor) {
      this.cache = new LeastRecentlyUsedCache<Path, Package>();
      this.builder = new PackageBuilder(context, executor);
   }
   
   @Override
   public Package link(Path path, String source) throws Exception {
      return link(path, source, GRAMMAR_PACKAGE);
   }
   
   @Override
   public Package link(Path path, String source, String grammar) throws Exception {
      Package linked = cache.fetch(path);
      
      if(linked == null) {
         linked = builder.create(path, source, grammar); 
         cache.cache(path, linked);
      }
      return linked; 
   } 
}