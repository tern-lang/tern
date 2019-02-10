package tern.tree;

import tern.common.io.PropertyReader;

public class InstructionReader extends PropertyReader<Instruction>{
   
   public InstructionReader(String file) {
      super(file);
   }

   @Override
   protected Instruction create(String name, char[] data, int off, int length, int line) {
      String type = format(data, off, length);
      
      if(type != null) {      
         return new Instruction(type, name);
      }
      return null;
   }
}