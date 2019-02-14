package org.ternlang.common.store;

import java.io.InputStream;

public class ClassPathLoader {
   
   private final Class type;
   
   public ClassPathLoader(Class type) {
      this.type = type;
   }

   public InputStream loadResource(String path) {
      Thread thread = Thread.currentThread();
      ClassLoader context = thread.getContextClassLoader();
      InputStream source = context.getResourceAsStream(path);
      
      if(source == null) {
         ClassLoader caller = type.getClassLoader();
         
         if(caller != context) {
            return caller.getResourceAsStream(path);
         }
      }  
      return source;
   }
}
