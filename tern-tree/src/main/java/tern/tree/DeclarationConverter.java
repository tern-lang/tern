package tern.tree;

import tern.core.Context;
import tern.core.constraint.Constraint;
import tern.core.convert.ConstraintConverter;
import tern.core.convert.ConstraintMatcher;
import tern.core.convert.Score;
import tern.core.error.InternalStateException;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class DeclarationConverter {

   public DeclarationConverter() {
      super();
   }

   public Constraint compile(Scope scope, Type value, Constraint constraint, String name) throws Exception {
      if(value != null) {
         Type type = constraint.getType(scope);
         Module module = scope.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(value);
         
         if(score.isInvalid()) {
            throw new InternalStateException("Variable '" + name + "' does not match constraint '" + type + "'");
         }
         return constraint;
      }
      return null;
   }
   
   public Object convert(Scope scope, Object value, Constraint constraint, String name) throws Exception {
      if(value != null) {
         Type type = constraint.getType(scope);
         Module module = scope.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(value);
         
         if(score.isInvalid()) {
            throw new InternalStateException("Variable '" + name + "' does not match constraint '" + type + "'");
         }
         return converter.assign(value);
      }
      return null;
   }
}