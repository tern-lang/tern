package org.ternlang.core.platform;

import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.type.TypeExtractor;

public class PlatformProvider {

   private PlatformBuilder loader;
   private Platform builder;
   
   public PlatformProvider(TypeExtractor extractor, ProxyWrapper wrapper, ThreadStack stack) {
      this.loader = new PlatformBuilder(extractor, wrapper, stack);
   }

   public Platform create() {
      if(builder == null) {
         builder = loader.create();
      }
      return builder;
   }
}