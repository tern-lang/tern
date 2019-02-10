package tern.core.function.index;

import java.util.List;

import tern.common.CopyOnWriteSparseArray;
import tern.common.SparseArray;
import tern.core.type.Type;
import tern.core.function.Function;
import tern.core.module.Module;
import tern.core.stack.ThreadStack;
import tern.core.type.TypeExtractor;

public class ModuleIndexer {
   
   private final SparseArray<FunctionIndex> indexes;
   private final FunctionPointerConverter converter;
   private final FunctionIndexBuilder builder;
   
   public ModuleIndexer(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, 10000);
   }
   
   public ModuleIndexer(TypeExtractor extractor, ThreadStack stack, int capacity) {
      this.indexes = new CopyOnWriteSparseArray<FunctionIndex>(capacity);
      this.builder = new FunctionIndexBuilder(extractor, stack);
      this.converter = new FunctionPointerConverter(stack);
   }

   public FunctionPointer index(Module module, String name, Type... types) throws Exception { 
      int index = module.getOrder();
      FunctionIndex match = indexes.get(index);
      
      if(match == null) {
         List<Function> functions = module.getFunctions();
         FunctionIndex table = builder.create(module);
         
         for(Function function : functions){
            FunctionPointer pointer = converter.convert(function);
            table.index(pointer);
         }
         indexes.set(index, table);
         return table.resolve(name, types);
      }
      return match.resolve(name, types);
   }
   
   public FunctionPointer index(Module module, String name, Object... values) throws Exception { 
      int index = module.getOrder();
      FunctionIndex match = indexes.get(index);
      
      if(match == null) {
         List<Function> functions = module.getFunctions();
         FunctionIndex table = builder.create(module);
         
         for(Function function : functions){
            FunctionPointer pointer = converter.convert(function);
            table.index(pointer);
         }
         indexes.set(index, table);
         return table.resolve(name, values);
      }
      return match.resolve(name, values);
   }
}