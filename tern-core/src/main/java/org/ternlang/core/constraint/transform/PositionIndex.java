package org.ternlang.core.constraint.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.TypeConstraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class PositionIndex implements ConstraintIndex {

   private final PositionMapper mapper;

   public PositionIndex(ConstraintSource source, Map<String, Integer> positions) {
      this.mapper = new PositionMapper(source, positions);
   }
   
   @Override
   public Constraint update(Scope scope, Constraint source, Constraint change) {
      String name = change.getName(scope);

      if(name == null) {
         List<Constraint> generics = change.getGenerics(scope);
         Type type = change.getType(scope);
         int count = generics.size();

         if(count > 0) {
            List<Constraint> updated = new ArrayList<Constraint>();
            AtomicBoolean touch = new AtomicBoolean();

            for(Constraint generic : generics) {
               Constraint update = update(scope, source, generic);

               touch.compareAndSet(false, update!= generic); // has anything at all changed
               updated.add(update);
            }
            if(touch.get()) {
               return new TypeConstraint(type, updated);
            }
         }
         return change;
      }
      return mapper.resolve(scope, source, name);
   }
}
