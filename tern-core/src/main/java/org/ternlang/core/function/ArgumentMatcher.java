package org.ternlang.core.function;

import java.util.List;

import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;

public class ArgumentMatcher {

   private final ArgumentConverterBuilder builder;
   private final Signature signature;
   private final Module module;
   
   public ArgumentMatcher(Signature signature, Module module) {
      this.builder = new ArgumentConverterBuilder();
      this.signature = signature;
      this.module = module;
   }
   
   public ArgumentConverter getConverter() throws Exception {
      Scope scope = module.getScope();
      List<Parameter> parameters = signature.getParameters();
      boolean variable = signature.isVariable();

      return builder.create(scope, parameters, variable);
   }
}