package tern.compile.assemble;

import java.util.concurrent.atomic.AtomicReference;

import tern.core.Context;
import tern.core.ContextValidator;
import tern.core.Execution;
import tern.core.Statement;
import tern.core.link.Package;
import tern.core.link.PackageDefinition;
import tern.core.scope.Scope;

public class ApplicationCompiler {
   
   private final AtomicReference<Execution> cache;
   private final Package library;
   private final Context context;
   
   public ApplicationCompiler(Context context, Package library){
      this.cache = new AtomicReference<Execution>();
      this.library = library;
      this.context = context;
   }

   public Execution compile(Scope scope) throws Exception{ 
      Execution execution = cache.get();
      
      if(execution == null) {
         ContextValidator validator = context.getValidator();
         PackageDefinition definition = library.create(scope); // create all types
         Statement statement = definition.define(scope, null); // define tree
         Execution result = statement.compile(scope, null);
         
         validator.validate(context); // validate program
         cache.set(result);
         
         return result;
      }
      return execution;
   }
}