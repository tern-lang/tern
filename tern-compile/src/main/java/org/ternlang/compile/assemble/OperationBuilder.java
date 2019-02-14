package org.ternlang.compile.assemble;

import static org.ternlang.core.Reserved.DEFAULT_PACKAGE;
import static org.ternlang.core.Reserved.DEFAULT_RESOURCE;
import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.concurrent.Executor;

import org.ternlang.core.Context;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.module.ContextModule;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.parse.Line;

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