package tern.core.function.index;

import java.util.List;

import tern.core.EntityCache;
import tern.core.ModifierType;
import tern.core.convert.TypeInspector;
import tern.core.function.Function;
import tern.core.stack.ThreadStack;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class FunctionIndexer {
   
   private final EntityCache<TypeIndex> indexes;
   private final FunctionPointerConverter converter;
   private final FunctionIndexBuilder builder;
   private final FunctionPathFinder finder;
   private final TypeInspector inspector;
   
   public FunctionIndexer(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new FunctionIndexBuilder(extractor, stack);
      this.converter = new FunctionPointerConverter(stack);
      this.indexes = new EntityCache<TypeIndex>();
      this.finder = new FunctionPathFinder();
      this.inspector = new TypeInspector();
   }
   
   public List<FunctionPointer> index(Type type, int modifiers) throws Exception { 
      TypeIndex match = indexes.fetch(type);

      if(match == null) {
         TypeIndex structure = build(type);
         
         indexes.cache(type, structure);
         return structure.get(modifiers);
      }
      return match.get(modifiers);
   }
   
   public FunctionPointer index(Type type, String name, Type... types) throws Exception { 
      TypeIndex match = indexes.fetch(type);

      if(match == null) {
         TypeIndex structure = build(type);
         
         indexes.cache(type, structure);
         return structure.get(name, types);
      }
      return match.get(name, types);
   }

   public FunctionPointer index(Type type, String name, Object... values) throws Exception { 
      TypeIndex match = indexes.fetch(type);

      if(match == null) {
         TypeIndex structure = build(type);
         
         indexes.cache(type, structure);
         return structure.get(name, values);
      }
      return match.get(name, values);
   }
   
   private TypeIndex build(Type type) throws Exception { 
      List<Type> path = finder.findPath(type); 
      FunctionIndex implemented = builder.create(type);
      FunctionIndex abstracts = builder.create(type);
      int size = path.size();

      for(int i = size - 1; i >= 0; i--) {
         Type entry = path.get(i);
         List<Function> functions = entry.getFunctions();

         for(Function function : functions){
            int modifiers = function.getModifiers();
            
            if(!inspector.isSuperConstructor(type, function)) {
               FunctionPointer call = converter.convert(function);
               
               if(!ModifierType.isAbstract(modifiers)) {  
                  implemented.index(call);
               } else {
                  abstracts.index(call);
               }
            }
         }
      }
      return new TypeIndex(implemented, abstracts);
   }
   
   private static class TypeIndex {
      
      private final FunctionIndex implemented;
      private final FunctionIndex abstracts;
      
      public TypeIndex(FunctionIndex implemented, FunctionIndex abstracts) {
         this.implemented = implemented;
         this.abstracts = abstracts;
      }
      
      public List<FunctionPointer> get(int modifiers) throws Exception {
         if(ModifierType.isAbstract(modifiers)) { 
            return abstracts.resolve(modifiers);
         }
         return implemented.resolve(modifiers);
      }
      
      public FunctionPointer get(String name, Type... types) throws Exception {
         FunctionPointer call = implemented.resolve(name, types);
         
         if(call == null) {
            return abstracts.resolve(name, types);
         }
         return call;
      }
      
      public FunctionPointer get(String name, Object... values) throws Exception {
         return implemented.resolve(name, values);
      }
   }
}