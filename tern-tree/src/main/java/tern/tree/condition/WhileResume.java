package tern.tree.condition;

import static tern.core.result.Result.NORMAL;

import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.resume.Resume;
import tern.core.resume.Yield;
import tern.tree.Suspend;

public class WhileResume extends Suspend<Object, Object> {
   
   private final Resume parent;
   private final Resume child;
   
   public WhileResume(Resume child, Resume parent){
      this.parent = parent;
      this.child = child;
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      Result result = child.resume(scope, value);
      
      if(result.isYield()) {
         return suspend(scope, result, parent, null);
      }
      if(result.isReturn()) {
         return result;
      }
      if(result.isBreak()) {
         return NORMAL;
      }
      return parent.resume(scope, null);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Object value) throws Exception {
      Yield yield = result.getValue();
      Resume child = yield.getResume();
      
      return new WhileResume(child, resume);
   }
}