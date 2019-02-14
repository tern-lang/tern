package org.ternlang.core.constraint.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ternlang.core.EntityCache;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class ConstraintIndexBuilder {

   private final EntityCache<ConstraintIndex> indexes;

   public ConstraintIndexBuilder(){
      this.indexes = new EntityCache<ConstraintIndex>();
   }

   public ConstraintIndex index(Type type){ // give me the named parameters
      ConstraintIndex index = indexes.fetch(type);

      if(index == null) {
         index = create(type);
         indexes.cache(type, index);
      }
      return index;
   }

   private ConstraintIndex create(Type type) {
      Scope scope = type.getScope();
      List<Constraint> generics = type.getGenerics();
      int count = generics.size();

      if(count > 0) {
         Map<String, Integer> positions = new HashMap<String,Integer>();
         ConstraintSource source = new TypeSource(type);
         
         for(int i = 0; i < count; i++){
            Constraint constraint = generics.get(i);
            String name = constraint.getName(scope);

            if(name != null) {
               positions.put(name, i);
            }
         }
         return new PositionIndex(source, positions);
      }
      return new EmptyIndex();
   }
}
