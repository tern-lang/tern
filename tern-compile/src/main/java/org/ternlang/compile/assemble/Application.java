package org.ternlang.compile.assemble;

import org.ternlang.compile.Executable;
import org.ternlang.core.Context;
import org.ternlang.core.Execution;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.link.Package;
import org.ternlang.core.scope.EmptyModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.Scope;

public class Application implements Executable{
   
   private final ApplicationCompiler compiler;
   private final ModelScopeBuilder builder;
   private final Context context;
   private final String module;
   private final Model empty;
   
   public Application(Context context, Package library, String module){
      this.compiler = new ApplicationCompiler(context, library);
      this.builder = new ModelScopeBuilder(context);
      this.empty = new EmptyModel();
      this.context = context;
      this.module = module;
   }
   
   @Override
   public void execute() throws Exception {
      execute(empty);
   }

   @Override
   public void execute(Model model) throws Exception{
      execute(model, false);
   }
   
   @Override
   public void execute(Model model, boolean test) throws Exception{
      Scope scope = builder.create(model, module); 
      ErrorHandler handler = context.getHandler();
      Execution execution = compiler.compile(scope); // create all types
      
      try {
         if(!test) {
            execution.execute(scope);
         }
      } catch(Throwable cause) {
         handler.failExternalError(scope, cause);
      }
   }
}