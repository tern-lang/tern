package tern.common.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStore implements Store {

   private final ClassPathStore store;
   private final File[] roots;
   
   public FileStore(File... roots) {
      this.store = new ClassPathStore();
      this.roots = roots;
   }
   
   @Override
   public InputStream getInputStream(String path) {
      for(File root : roots) {
         File resource = new File(root, path);
         
         if(resource.exists()) {
            try {
               return new FileInputStream(resource);
            } catch(Exception e) {
               throw new StoreException("Could not read resource '" + path + "'", e);
            }
         }
      }
      return store.getInputStream(path);
   }

   @Override
   public OutputStream getOutputStream(String path) {
      for(File root : roots) {
         File resource = new File(root, path);
         
         if(resource.exists()) {
            resource.delete();
         }
         if(!root.exists()) {
            root.mkdirs();
         }
         try {
            return new FileOutputStream(resource);
         } catch(Exception e) {
            throw new StoreException("Could not write resource '" + path + "'", e);
         }
      }
      return store.getOutputStream(path);
   }
}