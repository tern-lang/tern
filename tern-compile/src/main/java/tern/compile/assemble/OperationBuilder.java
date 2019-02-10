package tern.compile.assemble;

import static tern.core.Reserved.DEFAULT_PACKAGE;
import static tern.core.Reserved.DEFAULT_RESOURCE;
import static tern.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.concurrent.Executor;

import tern.core.Context;
import tern.core.error.InternalStateException;
import tern.core.function.resolve.FunctionCall;
import tern.core.function.resolve.FunctionResolver;
import tern.core.module.ContextModule;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.parse.Line;

public class OperationBuilder {
   
   private final OperationProcessor processor;
   private final Module module;
   private final Path path;

   public OperationBuilder(Context context, Executor executor) {
      this.path = new Path(DEFAULT_RESOURCE);
      this.module = new ContextModule(context, executor, path, DEFAULT_PACKAGE, "", 0);
      this.processor = new OperationProcessor(context);
   }
   
   public Object create(Type type, Object[] arguments, Line line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      FunctionResolver resolver = context.getResolver();
      FunctionCall call = resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, arguments);
      
      if(call == null) {
         throw new InternalStateException("No constructor for '" + type + "' at line " + line);
      }
      Object result = call.invoke(scope, type, arguments);
      
      return processor.process(result, line);
   }


}