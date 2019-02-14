package org.ternlang.core.link;

import java.util.concurrent.FutureTask;

import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;

public class FutureStatement extends Statement {
   
   private final FutureTask<Statement> result;
   private final Path path;
   
   public FutureStatement(FutureTask<Statement> result, Path path) {
      this.result = result;
      this.path = path;
   }

   @Override
   public void create(Scope scope) throws Exception {
      Statement definition = result.get();
      
      if(definition == null) {
         throw new InternalStateException("Could not define '" + path + "'");
      }
      definition.create(scope);
   }
   
   @Override
   public boolean define(Scope scope) throws Exception {
      Statement definition = result.get();
      
      if(definition == null) {
         throw new InternalStateException("Could not compile '" + path + "'");
      }
      return definition.define(scope);
   }
   
   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      Statement definition = result.get();
      
      if(definition == null) {
         throw new InternalStateException("Could not validate '" + path + "'");
      }
      return definition.compile(scope, returns);
   }
}