package tern.core.function.index;

import java.util.List;

import tern.core.EntityCache;
import tern.core.type.Type;
import tern.core.convert.TypeInspector;
import tern.core.convert.proxy.Delegate;
import tern.core.function.Function;
import tern.core.stack.ThreadStack;
import tern.core.type.TypeExtractor;

public class DelegateIndexer {
   
   private final EntityCache<FunctionIndex> indexes;
   private final FunctionPointerConverter converter;
   private final FunctionIndexBuilder builder;
   private final FunctionPathFinder finder;
   private final TypeInspector inspector;
   private final TypeExtractor extractor;
   
   public DelegateIndexer(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new FunctionIndexBuilder(extractor, stack);
      this.converter = new FunctionPointerConverter(stack);
      this.indexes = new EntityCache<FunctionIndex>();
      this.finder = new FunctionPathFinder();
      this.inspector = new TypeInspector();
      this.extractor = extractor;
   }
   
   public FunctionPointer match(Type type, String name, Type... values) throws Exception { 
      FunctionIndex match = indexes.fetch(type);
      
      if(match == null) {
         List<Type> path = finder.findPath(type); 
         FunctionIndex table = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            
            if(!inspector.isProxy(entry)) {
               List<Function> functions = entry.getFunctions();
   
               for(Function function : functions){
                  if(!inspector.isSuperConstructor(type, function)) {
                     FunctionPointer pointer = converter.convert(function);
                     table.index(pointer);
                  }
               }
            }
         }
         indexes.cache(type, table);
         return table.resolve(name, values);
      }
      return match.resolve(name, values);
   }
   
   public FunctionPointer match(Delegate value, String name, Object... values) throws Exception { 
      Type type = extractor.getType(value);
      FunctionIndex match = indexes.fetch(type);
      
      if(match == null) {
         List<Type> path = finder.findPath(type); 
         FunctionIndex table = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            
            if(!inspector.isProxy(entry)) {
               List<Function> functions = entry.getFunctions();
   
               for(Function function : functions){
                  if(!inspector.isSuperConstructor(type, function)) {
                     FunctionPointer pointer = converter.convert(function);
                     table.index(pointer);
                  }
               }
            }
         }
         indexes.cache(type, table);
         return table.resolve(name, values);
      }
      return match.resolve(name, values);
   }
}