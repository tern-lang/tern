package tern.core.platform;

import tern.core.convert.proxy.ProxyWrapper;
import tern.core.stack.ThreadStack;
import tern.core.type.TypeExtractor;

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