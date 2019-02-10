package tern.compile.assemble;

import tern.compile.Executable;
import tern.core.Context;
import tern.core.Execution;
import tern.core.error.ErrorHandler;
import tern.core.link.Package;
import tern.core.scope.EmptyModel;
import tern.core.scope.Model;
import tern.core.scope.Scope;

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