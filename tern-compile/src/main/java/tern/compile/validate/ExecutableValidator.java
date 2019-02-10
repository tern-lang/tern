package tern.compile.validate;

import java.util.List;

import tern.compile.verify.Verifier;
import tern.core.Context;
import tern.core.ContextValidator;
import tern.core.constraint.transform.ConstraintTransformer;
import tern.core.convert.ConstraintMatcher;
import tern.core.function.index.FunctionIndexer;
import tern.core.module.Module;
import tern.core.module.ModuleRegistry;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class ExecutableValidator implements ContextValidator {

   private final ModuleValidator modules;
   private final TypeValidator types;
   private final Verifier verifier;
   
   public ExecutableValidator(ConstraintMatcher matcher, ConstraintTransformer transformer, TypeExtractor extractor, FunctionIndexer indexer, Verifier verifier) {
      this.types = new TypeValidator(matcher, transformer, extractor, indexer);
      this.modules = new ModuleValidator(types);
      this.verifier = verifier;
   }
   
   @Override
   public void validate(Context context) throws Exception {
      ModuleRegistry registry = context.getRegistry();
      List<Module> available = registry.getModules();
    
      verifier.verify();
    
      try {
         for(Module module : available) {
            modules.validate(module);
         }  
      } finally {
         verifier.verify();
      }
   }

   @Override
   public void validate(Type type) throws Exception {
      try {
         types.validate(type);
      } finally {
         verifier.verify();
      }
   }

   @Override
   public void validate(Module module) throws Exception {
      try {
         modules.validate(module);
      } finally {
         verifier.verify();
      }
   }
}