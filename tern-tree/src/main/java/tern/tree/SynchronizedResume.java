package tern.tree;

import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.resume.Resume;

public class SynchronizedResume extends Suspend<Object, Resume> {
   
   private final Resume parent;
   private final Resume child;
   
   public SynchronizedResume(Resume child, Resume parent){
      this.parent = parent;
      this.child = child;
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return parent.resume(scope, child);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Resume value) throws Exception {
      return null;
   }
}