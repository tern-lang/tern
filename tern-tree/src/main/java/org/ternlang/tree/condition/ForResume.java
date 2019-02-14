package org.ternlang.tree.condition;

import static org.ternlang.core.result.Result.NORMAL;

import org.ternlang.core.Evaluation;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.resume.Resume;
import org.ternlang.core.resume.Yield;
import org.ternlang.tree.Suspend;

public class ForResume extends Suspend<Object, Object> {
   
   private final Evaluation assignment;
   private final Resume parent;
   private final Resume child;
   
   public ForResume(Resume child, Resume parent, Evaluation assignment){
      this.assignment = assignment;
      this.parent = parent;
      this.child = child;
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      Result result = child.resume(scope, value);
      
      if(result.isYield()) {
         return suspend(scope, result, parent, assignment);
      }
      if(result.isReturn()) {
         return result;
      }
      if(result.isBreak()) {
         return NORMAL;
      }
      if(assignment != null) {
         assignment.evaluate(scope, null);
      }
      return parent.resume(scope, null);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Object value) throws Exception {
      Yield yield = result.getValue();
      Resume child = yield.getResume();
      
      return new ForResume(child, resume, assignment);
   }
}