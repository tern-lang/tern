package org.ternlang.tree.define;

import static org.ternlang.core.type.Category.OTHER;

import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Category;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.type.index.PrimitiveInstanceBuilder;

public class AnyState extends TypeState {
   
   private final PrimitiveInstanceBuilder builder;
   
   public AnyState() {
      this.builder = new PrimitiveInstanceBuilder();
   }
   
   @Override
   public Category define(Scope instance, Type real) throws Exception {
      return OTHER;
   }

   @Override
   public Result execute(Scope instance, Type real) throws Exception {
      Scope base = builder.create(instance, real);      
      return Result.getNormal(base);
   }
}