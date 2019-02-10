package tern.core.link;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class ImportPathSelectorTest extends TestCase {

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
   "rmi = java.rmi, javax.rmi {}\n"+
   "security = java.security, javax.security {}\n"+
   "sql = java.sql, javax.sql {}\n"+
   "text = java.text {}\n"+
   "time = java.time {}\n"+
   "util = java.util {*}\n"; // default import
   
   public void testImportPathSelector() throws Exception {
      System.err.println(SOURCE);
      final Map<String, byte[]> resources = new HashMap<String, byte[]>();
      
      resources.put("imports.txt", SOURCE.getBytes());
      resources.put("/mario/core/Mario.tern", new byte[]{});
      resources.put("/mario/core/MarioGame.tern", new byte[]{});
      
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
      
      ImportPathResolver selector = new ImportPathResolver("imports.txt");
      
      assertEquals(selector.resolvePath("mario.core.Mario").size(), 1);
      assertEquals(selector.resolvePath("mario.core.MarioGame").size(), 1);
      
      assertEquals(selector.resolvePath("java.lang.Integer").size(), 1);
      assertEquals(selector.resolvePath("java.lang.Integer").get(0), "java.lang.Integer");
      assertEquals(selector.resolvePath("lang.Integer").size(), 2);
      assertEquals(selector.resolvePath("lang.Integer").get(0), "java.lang.Integer");
      assertEquals(selector.resolvePath("lang.Integer").get(1), "lang.Integer");
      
      assertEquals(selector.resolvePath("java.lang.Boolean").size(), 1);
      assertEquals(selector.resolvePath("java.lang.Boolean").get(0), "java.lang.Boolean");
      assertEquals(selector.resolvePath("lang.Boolean").size(), 2);
      assertEquals(selector.resolvePath("lang.Boolean").get(0), "java.lang.Boolean");
      assertEquals(selector.resolvePath("lang.Boolean").get(1), "lang.Boolean");

      assertEquals(selector.resolvePath("java.sql.Connection").size(), 1);
      assertEquals(selector.resolvePath("java.sql.Connection").get(0), "java.sql.Connection");
      assertEquals(selector.resolvePath("sql.Connection").get(0), "java.sql.Connection");
      assertEquals(selector.resolvePath("sql.Connection").get(1), "javax.sql.Connection");
      assertEquals(selector.resolvePath("sql.Connection").get(2), "sql.Connection");
      
      assertEquals(selector.resolvePath("String").size(), 3);
      assertEquals(selector.resolvePath("String").get(0), "java.lang.String");
      assertEquals(selector.resolvePath("String").get(1), "java.io.String");
      assertEquals(selector.resolvePath("String").get(2), "java.util.String");
      
      assertEquals(selector.resolveName("java.lang.String"), "lang.String");
      assertEquals(selector.resolveName("java.util.concurrency.ConcurrentHashMap"), "util.concurrency.ConcurrentHashMap");

      assertEquals(selector.resolveName("java.sql.Connection"), "sql.Connection");
      assertEquals(selector.resolveName("java.sql.ResultSet"), "sql.ResultSet");

      assertEquals(selector.resolveName("javax.sql.PooledConnection"), "sql.PooledConnection");
      assertEquals(selector.resolveName("javax.sql.XAConnection"), "sql.XAConnection");
   }

}
