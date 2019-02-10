package tern.tree;

import tern.core.Execution;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.resume.Resume;

public class StatementResume extends Execution implements Resume {
   
   private final Execution statement;
   
   public StatementResume(Execution statement) {
      this.statement = statement;
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      return statement.execute(scope);
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return statement.execute(scope);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Object value) throws Exception {
      return null;
   }
   
}
