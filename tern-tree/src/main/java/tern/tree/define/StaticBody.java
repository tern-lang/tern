package tern.tree.define;

import static tern.core.result.Result.NORMAL;

import java.util.concurrent.atomic.AtomicBoolean;

import tern.core.Execution;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.result.Result;
import tern.core.type.TypeBody;

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