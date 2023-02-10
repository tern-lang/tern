package org.ternlang.tree.operation;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;

public class CalculationList implements Compilation { 
   
   private final CalculationPart[] parts;
   private final Calculator calculator;

   public CalculationList(CalculationPart... parts) {
      this.calculator = new Calculator();
      this.parts = parts;
   }

   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      
      for(CalculationPart part : parts) {
         calculator.update(part);
      }
      return calculator.create(scope);
   }
}