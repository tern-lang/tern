package tern.core.link;

import static tern.core.Reserved.IMPORT_FILE;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

public class DefaultImportTest extends TestCase {
   
   private static final String SOURCE =
   "# default imports\n"+
   "lang = java.lang {*}\n"+ // default import
   "applet = java.applet {}\n"+
   "awt = java.awt {}\n"+
   "beans = java.beans {}\n"+
   "io = java.io {*}\n"+ // default import
   "math = java.math {}\n"+
   "net = java.net {}\n"+
   "nio = java.nio {}\n"+
   "rmi = java.rmi {}\n"+
   "security = java.security {}\n"+
   "sql = java.sql {}\n"+
   "text = java.text {}\n"+
   "time = java.time {}\n"+
   "util = java.util {*}\n"; // default import
   
   public void testDefaultImports() throws Exception {
      System.err.println(SOURCE);
      final Map<String, byte[]> resources = new HashMap<String, byte[]>();
      
      resources.put(IMPORT_FILE, SOURCE.getBytes());
      
      final ClassLoader loader = new URLClassLoader(new URL[]{}) {
         @Override
         public InputStream getResourceAsStream(String name) {
            byte[] data = resources.get(name);
            if(data != null) {
               return new ByteArrayInputStream(data);
            }
            return null;
         }
      };
      Thread.currentThread().setContextClassLoader(loader);
      DefaultImportReader reader = new DefaultImportReader(IMPORT_FILE);
      StringBuilder builder = new StringBuilder();

      for(DefaultImport hint : reader){
         String prefix = hint.getModules().iterator().next();
         Set<String> types = hint.getImports();
         Iterator<String> iterator = types.iterator();
         
         while(iterator.hasNext()) {
            String type = iterator.next();
            Class real = loader.loadClass(prefix + "." + type);
            int modifiers = real.getModifiers();
            
            if(!Modifier.isPublic(modifiers)){
               iterator.remove();
            }
         }
      }
      builder.append("# default imports\n");
      for(DefaultImport hint : reader){
         String alias = hint.getAlias();
         String prefix = hint.getModules().iterator().next();
         Set<String> types = hint.getImports();
         
         if(!types.isEmpty()) {
            builder.append("\n");
         }
         builder.append(alias);
         builder.append(" = ");
         
         if(prefix.endsWith(".")) {
            int length = prefix.length();
            prefix = prefix.substring(0, length -1);
         } 
         builder.append(prefix);
         
         if(!types.isEmpty()) {
            builder.append(" {\n   ");
            
            List<String> list = new ArrayList<String>(types);
            Collections.sort(list);
            
            if(hint.isInclude()) {
               list.add("*");
            }
            
            for(int i = 0; i < list.size(); i++) {
               String type = list.get(i);
               
               if(i > 0) {
                  if(i % 5 == 0) {
                     builder.append(",\n   ");
                  }else {
                     builder.append(", ");
                  }
               }
               builder.append(type);
            }
         }else {
            builder.append(" {");
         }
         builder.append("\n}\n");
      }
      System.out.println(builder);
      
      for(DefaultImport hint : reader){
         String prefix = hint.getModules().iterator().next();
         Set<String> types = hint.getImports();
         
         for(String type : types) {
            Class real = loader.loadClass(prefix + "." + type);
            
            assertNotNull(real);
            assertTrue((real.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC);
         }
      }
   }

}
