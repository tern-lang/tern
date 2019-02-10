package tern.tree;

import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.resume.Resume;
import tern.core.resume.Yield;

public class CompoundResume extends Suspend<Object, Integer> {
   
   private final Resume parent;
   private final Resume child;
   private final int index;
   
   public CompoundResume(Resume child, Resume parent, int index){
      this.parent = parent;
      this.child = child;
      this.index = index;
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      Result result = child.resume(scope, value);
      
      if(result.isYield()) {
         return suspend(scope, result, parent, index);
      }
      if(result.isNormal()) {
         return parent.resume(scope, index);
      }
      return result;
   } 

   @Override
   public Resume suspend(Result result, Resume resume, Integer value) throws Exception {
      Yield yield = result.getValue();
      Resume child = yield.getResume();
      
      return new CompoundResume(child, resume, index);
   }
}