package tern.common.store;

import java.io.InputStream;
import java.io.OutputStream;

public class ClassPathStore implements Store {
   
   private final ClassPathLoader loader;

   public ClassPathStore() {
      this.loader = new ClassPathLoader(Store.class);
   }
   
   @Override
   public InputStream getInputStream(String path) {
      InputStream source = loader.loadResource(path);
      
      if(source == null) {
         int index = path.indexOf('/');
         int length = path.length();
         
         if(index == 0 && length > 0) {
            String relative = path.substring(1);
            int remainder = relative.length();
            
            if(remainder > 0) {
               return getInputStream(relative);
            }
         }
         throw new NotFoundException("Could not find '" + path + "'");
      }  
      return source;
   }
   
   @Override
   public OutputStream getOutputStream(String path) {
      return null;
   }
}