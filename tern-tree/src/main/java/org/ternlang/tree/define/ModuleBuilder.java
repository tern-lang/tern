package org.ternlang.tree.define;

import org.ternlang.core.Context;
import org.ternlang.core.NameFormatter;
import org.ternlang.core.link.ImportManager;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.ModuleRegistry;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.tree.NameReference;
import org.ternlang.tree.annotation.AnnotationList;

public class ModuleBuilder {

   private final AnnotationList annotations;
   private final NameReference reference;
   private final NameFormatter formatter;
   
   public ModuleBuilder(AnnotationList annotations, ModuleName module) {
      this.reference = new NameReference(module);
      this.formatter = new NameFormatter();
      this.annotations = annotations;
   }

   public Module create(Scope scope) throws Exception {
      String name = reference.getName(scope);
      Module parent = scope.getModule();
      Module module = create(parent, name);
      ImportManager manager = module.getManager();
      String include = parent.getName();
      
      annotations.apply(scope, module);
      manager.addImport(include); // make outer classes accessible
      
      return module;
   }
   
   protected Module create(Module parent, String name) throws Exception {
      Path path = parent.getPath();
      String prefix = parent.getName();
      String type = formatter.formatFullName(prefix, name);
      Context context = parent.getContext();
      ImportManager manager = parent.getManager();
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.addModule(type, path); // create module
      
      manager.addImports(module); // add parent imports
      manager.addImport(type, name); // make module accessible by name
      
      return module;
   }
}