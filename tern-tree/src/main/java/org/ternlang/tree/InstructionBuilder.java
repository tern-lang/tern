package org.ternlang.tree;

import org.ternlang.core.Context;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InstructionBuilder {
   
   private final InstructionReader reader;
   private final Context context;

   public InstructionBuilder(Context context, String file) {
      this.reader = new InstructionReader(file);
      this.context = context;
   }

   public Map<String, Operation> create() throws Exception{
      Map<String, Operation> table = new HashMap<String, Operation>();   
      
      for(Set<Instruction> instructions : reader){
         for(Instruction instruction : instructions) {
            Operation operation = create(instruction);
            String grammar = instruction.getName();

            table.put(grammar, operation);
         }
      } 
      return table;
   }
   
   private Operation create(Instruction instruction) throws Exception{
      TypeLoader loader = context.getLoader();
      String value = instruction.getType();
      Type type = loader.loadType(value);
      String name = instruction.getName();

      if(type == null) {
         throw new InternalStateException("Instruction '" + name + "' bound to missing type '" + value + "'");
      }
      return new Operation(type, name);
   }
}