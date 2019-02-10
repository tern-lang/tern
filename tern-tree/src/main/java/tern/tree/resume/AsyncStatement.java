package tern.tree.resume;

import static tern.core.result.Result.NORMAL;

import tern.core.Context;
import tern.core.Execution;
import tern.core.ModifierType;
import tern.core.NoExecution;
import tern.core.resume.PromiseWrapper;
import tern.core.Statement;
import tern.core.resume.TaskScheduler;
import tern.core.constraint.Constraint;
import tern.core.module.Module;
import tern.core.scope.Scope;

public class AsyncStatement extends Statement {

   private final PromiseWrapper wrapper;
   private final Statement statement;
   private final Execution empty;
   private final int modifiers;

   public AsyncStatement(Statement statement, int modifiers) {
      this.empty = new NoExecution(NORMAL);
      this.wrapper = new PromiseWrapper();
      this.statement = statement;
      this.modifiers = modifiers;
   }

   @Override
   public void create(Scope scope) throws Exception {
      if(statement != null) {
         statement.create(scope);
      }
   }

   @Override
   public boolean define(Scope scope) throws Exception {
      if(statement != null) {
         return statement.define(scope);
      }
      return false;
   }

   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      if(statement != null) {
         Constraint actual = wrapper.fromPromise(scope, returns);
         Execution execution = statement.compile(scope, actual);

         if (ModifierType.isAsync(modifiers)) {
            Module module = scope.getModule();
            Context context = module.getContext();
            TaskScheduler scheduler = context.getScheduler();

            return new AsyncExecution(scheduler, execution);
         }
         return execution;
      }
      return empty;
   }
}
