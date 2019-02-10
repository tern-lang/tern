package tern.parse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ScriptParser {

   private static final String GRAMMAR_FILE = "grammar.txt";
   private static final String ROOT = "C:/Work/development/snapscript/snap-develop/snap-develop/work/android";
   //private static final String FILE = "/mario/screens/GameScreen.tern";
   private static final String FILE = "/mario/objects/mario/Mario.tern";
         
   public static void main(String[] list) throws Exception {
      File file = new File(ROOT + FILE);
      SyntaxCompiler builder = new SyntaxCompiler(GRAMMAR_FILE);
      SyntaxParser parser = builder.compile();
      String text = readText(file);
      String name = file.getName();
      long start = System.currentTimeMillis();
      parser.parse(name, text, "script");
      System.err.println("time=" +(System.currentTimeMillis() - start));
   }
   
   public static String readText(File file) throws Exception {
      InputStream stream = new FileInputStream(file);
      try {
         ByteArrayOutputStream buffer = new ByteArrayOutputStream();
         int count = 0;
         
         while((count = stream.read()) != -1){
            buffer.write(count);
         }
         return buffer.toString();
      } finally {
         stream.close();
      }
   }
}
