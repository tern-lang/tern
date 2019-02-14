package org.ternlang.compile.assemble;

import static org.ternlang.core.type.Phase.COMPILE;

import org.ternlang.common.Progress;
import org.ternlang.core.Context;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.ModuleRegistry;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.ModelScope;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Phase;

public class ModelScopeBuilder {

   private final Context context;
   
   public ModelScopeBuilder(Context context) {
      this.context = context;
   }
  
   public Scope create(Model model, String name) {
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.addModule(name);
      Progress<Phase> progress = module.getProgress();
      Scope outer = module.getScope();
      
      progress.done(COMPILE);

      return new ModelScope(model, module, outer);
   }
}