package org.ternlang.core.function.index;

import org.ternlang.core.module.Module;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class FunctionIndexBuilder {

   private final FunctionKeyBuilder builder;
   private final FunctionReducer reducer;
   private final int limit; 
   
   public FunctionIndexBuilder(TypeExtractor extractor) {
      this(extractor, 20);
   }
   
   public FunctionIndexBuilder(TypeExtractor extractor, int limit) {
      this.builder = new FunctionKeyBuilder(extractor);
      this.reducer = new FunctionReducer();
      this.limit = limit;
   }
   
   public FunctionIndex create(Module module) {
      return new FunctionIndex(reducer, builder, limit);
   }
   
   public FunctionIndex create(Type type) {
      return new FunctionIndex(reducer, builder, limit);
   }
}