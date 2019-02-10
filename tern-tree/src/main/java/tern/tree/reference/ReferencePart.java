package tern.tree.reference;

import tern.core.Compilation;
import tern.core.Evaluation;
import tern.core.module.Module;
import tern.core.module.Path;

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