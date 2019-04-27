package org.ternlang.core.function.index;

import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class TypeInstanceIndexer {
   
   private final FunctionIndexer indexer;
   private final TypeExtractor extractor;
   
   public TypeInstanceIndexer(TypeExtractor extractor, FunctionIndexer indexer) {
      this.extractor = extractor;
      this.indexer = indexer;
   }

   public FunctionPointer index(Type type, String name, Type... values) throws Exception { 
      return indexer.index(type, name, values);
   }
   
   public FunctionPointer index(Object value, String name, Object... values) throws Exception {
      Type type = extractor.getType(value);
      return indexer.index(type, name, values);
   }
}