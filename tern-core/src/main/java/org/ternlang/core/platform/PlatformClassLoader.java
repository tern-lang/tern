package org.ternlang.core.platform;

import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.Any;
import org.ternlang.core.ContextClassLoader;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.function.index.FunctionIndexer;

public class PlatformClassLoader {
   
   private final AtomicReference<Constructor> reference;
   private final PlatformNameBuilder builder;
   private final ClassLoader loader;
   
   public PlatformClassLoader() {
      this.reference = new AtomicReference<Constructor>();
      this.loader = new ContextClassLoader(Any.class);
      this.builder = new PlatformNameBuilder();
   }

   public Constructor loadConstructor(){
      Constructor constructor = reference.get();
      
      if(constructor == null) {
         try {
            PlatformType platform = PlatformType.resolveType();
            String type = builder.createFullName(platform);
            Class value = loader.loadClass(type);
            
            constructor = value.getDeclaredConstructor(FunctionIndexer.class, ProxyWrapper.class);
            reference.set(constructor);
         }catch(Exception e) {
            throw new IllegalStateException("Could not load constructor", e);
         }
      }
      return constructor;
   }
}