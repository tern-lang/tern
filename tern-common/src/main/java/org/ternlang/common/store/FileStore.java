package org.ternlang.common.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStore implements Store {

   private final ClassPathStore store;
   private final FileFinder loader;
   
   public FileStore(File... roots) {
      this.store = new ClassPathStore();
      this.loader = new FileFinder(roots);
   }
   
   @Override
   public InputStream getInputStream(String path) {
      File file = loader.findFile(path);
      
      if(file != null) {
         try {
            return new FileInputStream(file);
         } catch(Exception e) {
            throw new StoreException("Could not read resource '" + path + "'", e);
         }
      }
      return store.getInputStream(path);
   }

   @Override
   public OutputStream getOutputStream(String path) {
      File file = loader.makeFile(path);
      
      if(file != null) {
         try {
            return new FileOutputStream(file);
         } catch(Exception e) {
            throw new StoreException("Could not write resource '" + path + "'", e);
         }
      }
      return store.getOutputStream(path);
   }
}