package tern.core.type.index;

import static tern.core.Reserved.ANY_TYPE;
import static tern.core.Reserved.DEFAULT_PACKAGE;

import tern.core.NameFormatter;
import tern.core.type.Type;

public class PrimitiveLoader {

   private final PrimitiveIndexer generator;
   private final NameFormatter formatter;
   
   public PrimitiveLoader(TypeIndexer indexer) {
      this.generator = new PrimitiveIndexer(indexer);
      this.formatter = new NameFormatter();
   }
   
   public Type loadType(String type) {      
      if(type.endsWith(ANY_TYPE)) {
         String name = formatter.formatFullName(DEFAULT_PACKAGE, ANY_TYPE);
         
         if(name.equals(type)) {
            return generator.indexAny();
         }
      }
      return null;
   }
}
