package org.ternlang.core.link;

import static org.ternlang.core.link.ImportType.EXPLICIT;
import static org.ternlang.core.link.ImportType.IMPLICIT;

import org.ternlang.core.NameFormatter;
import org.ternlang.core.error.InternalStateException;
  
public class PackageManager {
   
   private final NameFormatter formatter;
   private final ImportScanner scanner;
   private final PackageLoader loader;
   
   public PackageManager(PackageLoader loader, ImportScanner scanner) {
      this.formatter = new NameFormatter();
      this.scanner = scanner;
      this.loader = loader;
   }
   
   public Package importPackage(String module) {
      Object result = scanner.importPackage(module);
      
      if(result == null) {
         try {
            return loader.load(IMPLICIT, module); // import some.package.*
         } catch(Exception e){
            throw new InternalStateException("Problem importing '" + module + "'", e);
         }
      }
      return new NoPackage();
   }
   
   public Package importType(String module, String name) {
      String type  = formatter.formatFullName(module, name);            
      Object result = scanner.importType(type);
      
      if(result == null) {
         String outer  = formatter.formatTopName(module, name); 
         
         try {
            return loader.load(EXPLICIT, outer, module); // import some.package.Blah
         } catch(Exception e){
            throw new InternalStateException("Problem importing '" + module + "." + name + "'", e);
         }
      }
      return new NoPackage();
   }
   
   public Package importType(String type) {           
      Object result = scanner.importType(type);
      
      if(result == null) {
         String outer  = formatter.formatTopName(type); 
         
         try {
            return loader.load(EXPLICIT, outer); 
         } catch(Exception e){
            throw new InternalStateException("Problem importing '" + type + "'", e);
         }
      }
      return new NoPackage();
   }
}