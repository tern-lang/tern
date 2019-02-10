package tern.tree.resume;

import tern.core.resume.Promise;
import tern.core.result.Result;
import tern.core.resume.Resume;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.tree.Suspend;

public class AwaitResume extends Suspend<Object, Resume> {

   private final Resume child;
   private final Value state;

   public AwaitResume(Resume child, Value state){
      this.child = child;
      this.state = state;
   }

   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      if(state != null) {
         Object object = state.getValue();

         if(Promise.class.isInstance(object)) {
            Promise promise = (Promise)object;
            Object result = promise.value();
            Value state = Value.getTransient(result);

            return child.resume(scope, state);
         }
         return child.resume(scope, state);
      }
      return child.resume(scope, null);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Resume value) throws Exception {
      return null;
   }
}
