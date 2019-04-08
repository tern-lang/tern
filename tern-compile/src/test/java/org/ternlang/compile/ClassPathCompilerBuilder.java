package org.ternlang.compile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.Executor;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.core.Context;

public class ClassPathCompilerBuilder {   
   
   public static Compiler createCompiler() {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      return new StringCompiler(context);
   }
   
   public static Compiler createCompiler(Map<String, String> resources, Executor executor) {
      Store store = new MapStore(resources);
      Context context = new StoreContext(store, executor);
      return new ResourceCompiler(context);
   }
   
   private static class MapStore implements Store {
      
      private final Map<String, String> sources;
      
      public MapStore(Map<String, String> sources){
         this.sources = sources;
      }

      @Override
      public InputStream getInputStream(String name) {
         String source = sources.get(name);
         if(source != null) {
            try {
               byte[] data = source.getBytes("UTF-8");
               return new ByteArrayInputStream(data);
            }catch(Exception e){
               throw new IllegalStateException("Could not load " + name, e);
            }
         }
         return null;
      }

      @Override
      public OutputStream getOutputStream(String name) {
         return null;
      }
      
   }
}
