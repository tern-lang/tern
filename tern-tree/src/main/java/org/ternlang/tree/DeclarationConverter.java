package org.ternlang.tree;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.ConstraintConverter;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.Score;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

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