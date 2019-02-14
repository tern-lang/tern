package org.ternlang.core.result;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.resume.AwaitResult;
import org.ternlang.core.resume.Resume;
import org.ternlang.core.resume.YieldResult;

public abstract class Result {
   
   public static final Result NORMAL = new NormalResult();
   public static final Result RETURN = new ReturnResult();
   public static final Result BREAK = new BreakResult();
   public static final Result CONTINUE = new ContinueResult();
   public static final Result YIELD = new YieldResult();
   
   public static Result getNormal(Object value) {
      return new NormalResult(value);
   }
   
   public static Result getReturn(Object value) {
      return new ReturnResult(value);
   }

   public static Result getYield(Object value) {
      return new YieldResult(value);
   }
   
   public static Result getYield(Object value, Scope scope, Resume next) {
      return new YieldResult(value, scope, next);
   }

   public static Result getAwait(Object value) {
      return new AwaitResult(value);
   }

   public static Result getAwait(Object value, Scope scope, Resume next) {
      return new AwaitResult(value, scope, next);
   }
   
   public static Result getThrow(Object value) {
      return new ThrowResult(value);
   }
   
   public boolean isReturn() {
      return false;
   }
   
   public boolean isYield() {
      return false;
   }

   public boolean isAwait() {
      return false;
   }
   
   public boolean isNormal() {
      return false;
   }
   
   public boolean isBreak() {
      return false;
   }
   
   public boolean isThrow()  {
      return false;
   }
   
   public boolean isContinue() {
      return false;
   }

   public abstract <T> T getValue();
}