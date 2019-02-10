package tern.core.constraint.transform;

import static java.util.Collections.EMPTY_MAP;

import tern.core.attribute.Attribute;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;

public class LocalTransform implements ConstraintTransform {
   
   private final ConstraintSource source;
   private final ConstraintIndex index;
   private final Attribute attribute;
   
   public LocalTransform(Attribute attribute){
      this.source = new EmptySource();
      this.index = new PositionIndex(source, EMPTY_MAP);
      this.attribute = attribute;
   }
   
   @Override
   public ConstraintRule apply(Constraint left){
      ConstraintRule rule = new LocalRule(index, attribute, left);
      return new AttributeRule(rule, attribute);
   }
   
   private static class LocalRule extends ConstraintRule {
      
      private final ConstraintIndex index;
      private final Constraint left;
      
      public LocalRule(ConstraintIndex index, Attribute attribute, Constraint left) {
         this.index = index;
         this.left = left;
      }

      @Override
      public Constraint getResult(Scope scope, Constraint returns) {
         return index.update(scope, left, returns);
      }

      @Override
      public Constraint getSource() {
         return null;
      }      
   }
}
