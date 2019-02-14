package org.ternlang.core.module;

import static org.ternlang.core.Reserved.SCRIPT_EXTENSION;

import org.ternlang.common.Cache;
import org.ternlang.common.CopyOnWriteCache;

public class FilePathConverter implements PathConverter {

   private final Cache<String, String> modules;
   private final Cache<String, Path> paths;
   private final String extension;
   
   public FilePathConverter() {
      this(SCRIPT_EXTENSION);
   }
   
   public FilePathConverter(String extension) {
      this.modules = new CopyOnWriteCache<String, String>();
      this.paths = new CopyOnWriteCache<String, Path>();
      this.extension = extension;
   }
   
   @Override
   public Path createPath(String resource) {
      Path path = paths.fetch(resource);
      
      if(path == null) {
         Path match = convertModule(resource);
         String alias = match.getPath();
         
         paths.cache(resource, match);
         paths.cache(alias, match);
         
         return match;
      }
      return path;
   }

   @Override
   public String createModule(String resource) {
      String module = modules.fetch(resource);
      
      if(module == null) {
         String match = convertPath(resource);
         
         modules.cache(resource, match);
         modules.cache(match, match);
         
         return match;
      }
      return module;
   }
   
   private Path convertModule(String resource) {
      int suffix = resource.indexOf(extension);
      int prefix = resource.indexOf("/");
      
      if(suffix == -1) {
         int slash = resource.indexOf('.');
      
         if(slash != -1) {
            resource = resource.replace('.', '/');
         }
         return new Path("/" + resource + extension);
      }
      if(prefix != 0) {
         return new Path("/" + resource);
      }
      return new Path(resource);
   }
   
   private String convertPath(String path) {
      String module = convertResource(path);
      int index = module.lastIndexOf('.');
      
      if(index != -1) {
         char value = module.charAt(index+1);
         
         if(Character.isUpperCase(value)) {
            return module.substring(0,index);
         }
      }
      return module;
      
   }
   
   private String convertResource(String path) {
      int suffix = extension.length();
      int length = path.length();

      if(path.endsWith(extension)) {
         path = path.substring(0, length - suffix);
      }
      if(path.startsWith("/")) {
         path = path.substring(1);
      }
      if(path.startsWith("\\")) {
         path = path.substring(1);
      }
      if(path.contains("\\")) {
         path = path.replace("\\", ".");
      }
      return path.replace('/',  '.');
   }
}