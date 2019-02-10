package tern.core.function.index;

import tern.core.module.Module;
import tern.core.stack.ThreadStack;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class FunctionIndexBuilder {

   private final FunctionKeyBuilder builder;
   private final FunctionReducer reducer;
   private final int limit; 
   
   public FunctionIndexBuilder(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, 20);
   }
   
   public FunctionIndexBuilder(TypeExtractor extractor, ThreadStack stack, int limit) {
      this.builder = new FunctionKeyBuilder(extractor);
      this.reducer = new FunctionReducer(stack);
      this.limit = limit;
   }
   
   public FunctionIndex create(Module module) {
      return new FunctionIndex(reducer, builder, limit);
   }
   
   public FunctionIndex create(Type type) {
      return new FunctionIndex(reducer, builder, limit);
   }
}