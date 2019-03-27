package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;

public class StaticState extends StaticBlock {
   
   private final StaticConstantCollector collector;
   private final TypeBody body;
   
   public StaticState(TypeBody body) {
      this.collector = new StaticConstantCollector();
      this.body = body;
   }

   @Override
   protected void compile(Scope scope) throws Exception { 
      Type type = scope.getType();
      collector.compile(type);
   }
   
   @Override
   protected void allocate(Scope scope) throws Exception{
      Type type = scope.getType();
      body.allocate(scope, type);
   }
}