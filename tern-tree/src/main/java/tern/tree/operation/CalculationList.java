package tern.tree.operation;

import tern.core.Compilation;
import tern.core.Evaluation;
import tern.core.module.Module;
import tern.core.module.Path;

public class CalculationList implements Compilation { 
   
   private CalculationPart[] parts; 

   public CalculationList(CalculationPart... parts) {
      this.parts = parts;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Calculator calculator = new Calculator();
      
      for(CalculationPart part : parts) {
         calculator.update(part);
      }
      return calculator.create();
   }
}