package org.ternlang.core.resume;

import java.util.Iterator;

import org.ternlang.common.EmptyIterator;
import org.ternlang.core.Context;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public class Yield<T> implements Iterable<T> {

   private final Resume next;
   private final Object result;
   private final Scope scope;
   
   public Yield(Object result) {
      this(result, null, null);
   }
   
   public Yield(Object result, Scope scope, Resume next) {
      this.result = result;
      this.scope = scope;
      this.next = next;
   }
   
   @Override
   public Iterator<T> iterator() {
      if(scope != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();

         return new ResumeIterator<T>(handler, result, scope, next);
      }
      return new EmptyIterator<T>();
   }
   
   public Resume getResume() { // resume statement
      if(next == null) {
         return new NoResume();
      }
      return next;
   }
   
   public <T> T getValue() {
      return (T)result;
   }
   
   private static class ResumeIterator<T> implements Iterator<T> {

      private ErrorHandler handler;
      private Resume resume;
      private Object value;
      private Scope scope;
      
      public ResumeIterator(ErrorHandler handler, Object value, Scope scope, Resume resume) {
         this.handler = handler;
         this.resume = resume;
         this.value = value;
         this.scope = scope;
      }

      @Override
      public boolean hasNext() {
         if(value == null && resume != null) {
            return resume();
         }
         return value != null;
      }

      @Override
      public T next() {
         if(hasNext()) {
            Object result = value;
            value = null;
            return (T)result;
         }
         return null;
      }

      private boolean resume() {
         try{
            Result result = resume.resume(scope, null);

            if(result.isYield()) {
               Yield yield = result.getValue();

               resume = yield.getResume();
               value = yield.getValue();
               return true;
            }
            if(result.isReturn()) {
               value = result.getValue();
               resume = null;
               return true;
            }
         }catch(Throwable e){
            handler.failInternalError(scope, e);
         }
         resume = null;
         return false;
      }

      @Override
      public void remove() {
         value = null;
      }
   }
}
