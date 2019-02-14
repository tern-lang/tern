package org.ternlang.tree.define;

import static org.ternlang.core.result.Result.NORMAL;

import java.util.concurrent.atomic.AtomicBoolean;

import org.ternlang.core.Execution;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.result.Result;
import org.ternlang.core.type.TypeBody;

public class StaticBody extends Execution {
   
   private final AtomicBoolean execute;
   private final TypeBody body;
   private final Type type;
   
   public StaticBody(TypeBody body, Type type) {
      this.execute = new AtomicBoolean(false);
      this.body = body;
      this.type = type;
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      if(execute.compareAndSet(false, true)) {
         Scope outer = type.getScope();
         body.allocate(outer, type);
      }
      return NORMAL;
   } 
}