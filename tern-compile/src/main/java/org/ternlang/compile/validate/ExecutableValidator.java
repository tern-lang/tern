package org.ternlang.compile.validate;

import java.util.List;

import org.ternlang.compile.verify.Verifier;
import org.ternlang.core.Context;
import org.ternlang.core.ContextValidator;
import org.ternlang.core.constraint.transform.ConstraintTransformer;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.function.index.FunctionIndexer;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.ModuleRegistry;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

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