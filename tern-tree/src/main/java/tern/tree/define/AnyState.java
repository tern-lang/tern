package tern.tree.define;

import static tern.core.type.Category.OTHER;

import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.type.Category;
import tern.core.type.Type;
import tern.core.type.TypeState;
import tern.core.type.index.PrimitiveInstanceBuilder;

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