package tern.core.link;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class DefaultImportReaderTest extends TestCase {
   
   private static final String SOURCE =
   "lang = java.lang {\n"+
   "   String,\n"+
   "   Integer,\n"+
   "   *\n"+
   "}\n"+
   "\n"+
   "util = java.util {\n"+
   "   Set,\n"+
   "   Map,\n"+
   "   List,\n"+
   "   Collection,\n"+
   "   *\n"+
   "}\n"+
   "rmi=java.rmi,javax.rmi{}\n"+
   "awt=java.awt{}";

   public void testImportHintReader() throws Exception {
      System.err.println(SOURCE);
      final Map<String, byte[]> resources = new HashMap<String, byte[]>();
      
      resources.put("imports.txt", SOURCE.getBytes());
      
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
      
      Map<String, DefaultImport> map = new HashMap<String, DefaultImport>();
      DefaultImportReader reader = new DefaultImportReader("imports.txt");
      
      for(DefaultImport hint : reader){
         String name = hint.getAlias();
         map.put(name, hint);
      }
      assertEquals("java.lang", map.get("lang").getModules().iterator().next());
      assertEquals("java.util", map.get("util").getModules().iterator().next());
      assertEquals("java.rmi", map.get("rmi").getModules().iterator().next());
      assertEquals("java.awt", map.get("awt").getModules().iterator().next());
      
      assertEquals(map.get("lang").getImports().size(), 2);
      assertEquals(map.get("util").getImports().size(), 4);
      assertTrue(map.get("lang").isInclude());
      assertTrue(map.get("util").isInclude());
      assertTrue(map.get("rmi").getImports().isEmpty());
      assertTrue(map.get("awt").getImports().isEmpty());
      
      assertTrue(map.get("lang").getImports().contains("String"));
      assertTrue(map.get("lang").getImports().contains("Integer"));
      
      assertTrue(map.get("util").getImports().contains("Set"));
      assertTrue(map.get("util").getImports().contains("Map"));
      assertTrue(map.get("util").getImports().contains("List"));
      assertTrue(map.get("util").getImports().contains("Collection"));
   }
}
