package tern.compile.assemble;

import static tern.core.type.Phase.COMPILE;

import tern.common.Progress;
import tern.core.Context;
import tern.core.module.Module;
import tern.core.module.ModuleRegistry;
import tern.core.scope.Model;
import tern.core.scope.ModelScope;
import tern.core.scope.Scope;
import tern.core.type.Phase;

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