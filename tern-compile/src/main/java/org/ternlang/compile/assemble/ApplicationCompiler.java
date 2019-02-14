package org.ternlang.compile.assemble;

import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.Context;
import org.ternlang.core.ContextValidator;
import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.link.Package;
import org.ternlang.core.link.PackageDefinition;
import org.ternlang.core.scope.Scope;

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