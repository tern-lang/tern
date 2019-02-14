package org.ternlang.core.function.index;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.type.Type;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;

public class FunctionIndex {

   private FunctionIndexPartition[] partitions;
   private FunctionIndexPartition variable;
   private FunctionKeyBuilder builder;
   private FunctionReducer reducer;
   private int limit; 
   
   public FunctionIndex(FunctionReducer reducer, FunctionKeyBuilder builder) {
      this(reducer, builder, 20);
   }
   
   public FunctionIndex(FunctionReducer reducer, FunctionKeyBuilder builder, int limit) {
      this.variable = new FunctionIndexPartition(reducer, builder);
      this.partitions = new FunctionIndexPartition[2];
      this.reducer = reducer;
      this.builder = builder;
      this.limit = limit;
   }

   public List<FunctionPointer> resolve(int modifiers) {
      List<FunctionPointer> matches = new ArrayList<FunctionPointer>();
      
      for(FunctionIndexPartition partition : partitions){
         if(partition != null) {
            List<FunctionPointer> group = partition.resolve(modifiers);
            
            if(group != null) {
               matches.addAll(group);
            }
         }
      }
      return matches;
   }

   public FunctionPointer resolve(String name, Type... arguments) throws Exception {
      int size = arguments.length;
      
      if(size < partitions.length) {
         FunctionIndexPartition partition = partitions[size];
         
         if(partition != null) {
            return partition.resolve(name, arguments);
         }
      }
      return variable.resolve(name, arguments);
   }

   public FunctionPointer resolve(String name, Object... arguments) throws Exception {
      int size = arguments.length;
      
      if(size < partitions.length) {
         FunctionIndexPartition partition = partitions[size];
         
         if(partition != null) {
            return partition.resolve(name, arguments);
         }
      }
      return variable.resolve(name, arguments);
   }

   public void index(FunctionPointer pointer) throws Exception {
      Function function = pointer.getFunction();
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      int size = parameters.size();
      
      if(signature.isVariable()) {
         int maximum = size + limit;
         int minimum = size -1; // vargs with no value
         
         for(int i = maximum; i >= minimum; i--) { // limit variable arguments
            index(pointer, i);
         }
      } else {
         index(pointer, size);
      }
   }
   
   private void index(FunctionPointer pointer, int size) throws Exception {
      Function function = pointer.getFunction();
      Signature signature = function.getSignature();

      if(size >= partitions.length) {
         FunctionIndexPartition[] copy = new FunctionIndexPartition[size + 1];

         for(int i = 0; i < partitions.length; i++){
            copy[i] = partitions[i];
         }
         partitions = copy;
      }
      FunctionIndexPartition cache = partitions[size];

      if(cache == null) {
         cache = partitions[size] = new FunctionIndexPartition(reducer, builder);
      }
      if(signature.isVariable()) {
         variable.index(pointer);
      }
      cache.index(pointer);
   }
}