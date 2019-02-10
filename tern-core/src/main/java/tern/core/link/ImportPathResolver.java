package tern.core.link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImportPathResolver {
  
   private final ImportPathSource source;
   
   public ImportPathResolver(String file) {
      this.source = new ImportPathSource(file);
   }
   
   public String resolveName(String resource) {
      ImportPath path = source.getPath();
      Map<String, Set<String>> aliases = path.getAliases();
      Set<String> names = aliases.keySet();
      
      for(String name : names) {
         Set<String> modules = aliases.get(name);

         for(String module : modules) {
            if (resource.startsWith(module)) {
               return resource.replace(module, name);
            }
         }
      }
      return resource;
   }
   
   public List<String> resolvePath(String resource) {
      int index = resource.indexOf('.');
      
      if(index != -1) {
         return resolveAliasPath(resource, index);
      }
      return resolveTypePath(resource);
   }
   
   private List<String> resolveAliasPath(String resource, int index) {
      ImportPath path = source.getPath();
      Map<String, Set<String>> aliases = path.getAliases();
      String token = resource.substring(0, index);
      Set<String> modules = aliases.get(token); // 'sql' -> { 'java.sql.', 'javax.sql.' }
      
      if(modules != null) {
         String remainder = resource.substring(index);
         int count = modules.size();

         if(count > 0) {
            List<String> list = new ArrayList<String>();
            StringBuilder builder = new StringBuilder();

            for (String module : modules) {
               builder.append(module);
               builder.append(remainder);

               String absolute = builder.toString();

               list.add(absolute);
               builder.setLength(0);
            }
            list.add(resource);
            return list; // lang.String -> [java.lang.String, lang.String]
         }
         return Collections.emptyList();
      }
      return Collections.singletonList(resource); // com.w3c.Document -> [com.w3c.Document]
   }
   
   private List<String> resolveTypePath(String resource) {
      ImportPath path = source.getPath();
      Map<String, Set<String>> types = path.getTypes();
      Set<String> modules = types.get(resource); // String -> java.lang.
      
      if(modules != null) {
         int count = modules.size();

         if(count > 0) {
            List<String> list = new ArrayList<String>();
            StringBuilder builder = new StringBuilder();

            for(String module : modules) {
               builder.append(module);
               builder.append(".");
               builder.append(resource);

               String absolute = builder.toString();

               list.add(absolute);
               builder.setLength(0);
            }
            return list; // Connection -> [ java.sql.Connection, javax.sql.Connection ]
         }
         return Collections.emptyList();
      }
      return resolveDefaultPath(resource);
   }
   
   private List<String> resolveDefaultPath(String resource) {
      ImportPath path = source.getPath();
      Set<String> defaults = path.getDefaults();
      
      if(resource != null) {
         List<String> list = new ArrayList<String>();
         StringBuilder builder = new StringBuilder();
         
         for(String prefix : defaults){
            builder.append(prefix);
            builder.append(".");
            builder.append(resource);
            
            String entry = builder.toString();
            
            list.add(entry);
            builder.setLength(0);
         }
         return list; // String -> [java.lang.String, java.net.String, java.io.String]
      }
      return Collections.emptyList();
   }
}