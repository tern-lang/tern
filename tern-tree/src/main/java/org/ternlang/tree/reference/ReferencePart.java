package org.ternlang.tree.reference;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;

public class ReferencePart implements Compilation {
   
   private final Evaluation evaluation;
   
   public ReferencePart(Evaluation evaluation) {
      this.evaluation = evaluation;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      return evaluation;
   }
}