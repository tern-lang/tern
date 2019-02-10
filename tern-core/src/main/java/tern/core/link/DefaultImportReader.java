package tern.core.link;

import java.util.LinkedHashSet;
import java.util.Set;

import tern.common.io.PropertyReader;
import tern.core.error.InternalStateException;

public class DefaultImportReader extends PropertyReader<DefaultImport>{
   
   private static final String WILD = "*";
   
   public DefaultImportReader(String file) {
      super(file);
   }

   @Override
   protected DefaultImport create(String name, char[] data, int off, int length, int line) {
      Set<String> imports = createImports(data, off, length, line);
      Set<String> modules = createModules(data, off, length, line);

      if(imports.remove(WILD)) {
         return new DefaultImport(imports, modules, name, true);
      }
      return new DefaultImport(imports, modules, name);
   }
   
   private Set<String> createImports(char[] data, int off, int length, int line) {
      Set<String> imports = new LinkedHashSet<String>();
      int count = length + off - 1;
      
      for(int i = count; i >= 0; i--) {
         char next = data[i];
         
         if(delimiter(next)) {
            if(count > i) {
               String token = format(data, i+1, count-i);
               imports.add(token);
               count = i-1;
            }
         } 
         if(group(next)) {
            return imports;
         }
      }
      throw new InternalStateException("Error with imports from '" + file + "' at line " + line);
   }
   
   private Set<String> createModules(char[] data, int off, int length, int line) {
      Set<String> packages = new LinkedHashSet<String>();
      int count = length + off;

      for(int i = off; i < count; i++) {
         char next = data[i];

         if(delimiter(next)) {
            if(i > off) {
               String token = format(data, off, i - off);
               packages.add(token);
               off = i + 1;
            }
         }
         if(group(next)) {
            return packages;
         }
      }
      throw new InternalStateException("Error with package from '" + file + "' at line " + line);
   }
   
   private boolean group(char value) {
      return value == '{';
   }
   
   private boolean delimiter(char value) {
      return value == ',' || value == '{';
   }
   
   @Override
   protected boolean terminal(char value) {
      return value == '}';
   }

}