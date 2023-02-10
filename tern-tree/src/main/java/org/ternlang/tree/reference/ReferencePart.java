package org.ternlang.tree.reference;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.tree.PlaceHolder;

public class ReferencePart implements Compilation {

   private final Evaluation evaluation;
   private final PlaceHolder holder;
   
   public ReferencePart(PlaceHolder holder) {
      this.evaluation = null;
      this.holder = holder;
   }

   public ReferencePart(Evaluation evaluation) {
      this.evaluation = evaluation;
      this.holder = null;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      return evaluation == null ? holder : evaluation;
   }
}