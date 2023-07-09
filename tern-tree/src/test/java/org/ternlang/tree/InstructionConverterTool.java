package org.ternlang.tree;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class InstructionConverterTool {

   public static void main(String[] args) throws Exception {
      InputStream in = new FileInputStream("C:\\Work\\development\\tern-lang\\tern\\tern-scala\\src\\main\\resources\\tru.instruction");
      Properties p = new Properties();
      p.load(in);
      in.close();
      //System.err.println(p);

      Map<String, Map<String, String>> groups = new TreeMap<>();
      p.stringPropertyNames().forEach(n -> {
         String v = p.getProperty(n);
         String namespace = v.substring(0, v.lastIndexOf('.'));
         String name = v.substring(v.lastIndexOf('.') + 1);
         //System.err.println("name="+name + " namsapce="+namespace + " n="+n);

         groups.computeIfAbsent(namespace, ignore -> new TreeMap<>()).put(n, name);
      });

      groups.entrySet().forEach(g -> {
         String key = g.getKey();
         System.err.println("use " + key + " {");
         int index = 0;
         for(Map.Entry<String, String> pp : g.getValue().entrySet()) {
            if(index++ > 0) {
               System.err.println(",");
            }
            System.err.print("   " + pp.getKey() + " = " + pp.getValue());
         }
         System.err.println();
         System.err.println("}");
         System.err.println();
      });

   }
}
