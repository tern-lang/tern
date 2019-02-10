package tern.tree;

import java.util.HashMap;
import java.util.Map;

import tern.core.Context;
import tern.core.type.Type;
import tern.core.type.TypeLoader;

public class InstructionBuilder {
   
   private final InstructionReader reader;
   private final Context context;

   public InstructionBuilder(Context context, String file) {
      this.reader = new InstructionReader(file);
      this.context = context;
   }

   public Map<String, Operation> create() throws Exception{
      Map<String, Operation> table = new HashMap<String, Operation>();   
      
      for(Instruction instruction : reader){
         Operation operation = create(instruction);
         String grammar = instruction.getName();
         
         table.put(grammar, operation);
      } 
      return table;
   }
   
   private Operation create(Instruction instruction) throws Exception{
      TypeLoader loader = context.getLoader();
      String value = instruction.getType();
      Type type = loader.loadType(value);
      String name = instruction.getName();
      
      return new Operation(type, name);
   }
}