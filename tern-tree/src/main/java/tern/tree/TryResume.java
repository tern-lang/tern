package tern.tree;

import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.resume.Resume;

public class TryResume extends Suspend<Object, Object> {
   
   private final Resume parent;
   private final Resume child;
   
   public TryResume(Resume child, Resume parent){
      this.parent = parent;
      this.child = child;
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return parent.resume(scope, child);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Object value) throws Exception {
      return null;
   }
}