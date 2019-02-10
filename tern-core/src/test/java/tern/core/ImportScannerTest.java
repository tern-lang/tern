package tern.core;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import tern.core.link.ImportScanner;

import junit.framework.TestCase;

public class ImportScannerTest extends TestCase {

   public void testScanner() throws Exception {
      ImportScanner scanner = new ImportScanner(null);
      
      assertEquals(scanner.importType("lang.String"), String.class);
      assertEquals(scanner.importType("lang.String"), String.class);
      assertEquals(scanner.importType("util.HashMap"), HashMap.class);
      assertEquals(scanner.importType("java.lang.String"), String.class);
      assertEquals(scanner.importType("util.Map"), Map.class);
      
      assertEquals(scanner.importName(String.class), "lang.String");
      assertEquals(scanner.importName(Map.class), "util.Map");
      assertEquals(scanner.importName(Color.class), "awt.Color");
      assertEquals(scanner.importName(JFrame.class), "swing.JFrame");
      
      assertEquals(scanner.importType("awt.Color"), Color.class);
      assertEquals(scanner.importType("swing.JFrame"), JFrame.class);
   }
}
