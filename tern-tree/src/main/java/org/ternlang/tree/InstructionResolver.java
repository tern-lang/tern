package org.ternlang.tree;

import java.util.Map;

import org.ternlang.core.Context;

public class InstructionResolver implements OperationResolver {
   
   private volatile Map<String, Operation> operations;
   private volatile InstructionBuilder builder;
   
   public InstructionResolver(Context context, String file) {
      this.builder = new InstructionBuilder(context, file);
   }

   @Override
   public Operation resolve(String name) throws Exception {
      if(operations == null) {            
         operations = builder.create();         
      }      
      return operations.get(name);
   }
}
