package org.ternlang.core.type.index;

import static org.ternlang.core.Reserved.ANY_TYPE;
import static org.ternlang.core.Reserved.DEFAULT_MODULE;

import org.ternlang.core.NameFormatter;
import org.ternlang.core.type.Type;

public class PrimitiveLoader {

   private final PrimitiveIndexer generator;
   private final NameFormatter formatter;
   
   public PrimitiveLoader(TypeIndexer indexer) {
      this.generator = new PrimitiveIndexer(indexer);
      this.formatter = new NameFormatter();
   }
   
   public Type loadType(String type) {      
      if(type.endsWith(ANY_TYPE)) {
         String name = formatter.formatFullName(DEFAULT_MODULE, ANY_TYPE);
         
         if(name.equals(type)) {
            return generator.indexAny();
         }
      }
      return null;
   }
}
