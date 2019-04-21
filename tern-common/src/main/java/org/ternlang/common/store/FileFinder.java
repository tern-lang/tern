package org.ternlang.common.store;

import java.io.File;

public class FileFinder {
   
   private final File[] roots;
   
   public FileFinder(File... roots) {
      this.roots = roots;
   }

   public File makeFile(String path) {
      for(File root : roots) {
         File resource = new File(root, path);

         if(resource.exists()) {
            resource.delete();
         }
         if(!root.exists()) {
            root.mkdirs();
         }
         return resource;
      }
      return null;
   }
   
   public File findFile(String path) {
      for(File root : roots) {
         File resource = new File(root, path);

         if(resource.exists()) {
            try {
               File file = resource.getCanonicalFile();
               String name = file.getName();
               
               if(path.endsWith(name)) { // make sure case matches on Windows
                  return file;
               }
            } catch(Exception e) {
               continue;
            }
         }
      }
      return null;
   }
}
