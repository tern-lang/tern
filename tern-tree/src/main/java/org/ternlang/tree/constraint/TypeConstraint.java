package org.ternlang.tree.constraint;

import java.util.List;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class TypeConstraint extends Constraint {

   private Evaluation evaluation;
   private Constraint constraint;
   private Constraint left;
   
   public TypeConstraint(Evaluation evaluation) {
      this(evaluation, null);
   }
   
   public TypeConstraint(Evaluation evaluation, Constraint left) {
      this.evaluation = evaluation;
      this.left = left;
   }
   
   @Override
   public List<String> getImports(Scope scope) {
      if(constraint == null) {
         try {
            constraint = evaluation.compile(scope, left);
         } catch (Exception e) {
            throw new InternalStateException("Import not found", e);
         }
      }
      return constraint.getImports(scope);
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      if(constraint == null) {
         try {
            constraint = evaluation.compile(scope, left);
         } catch (Exception e) {
            throw new InternalStateException("Import not found", e);
         }
      }
      return constraint.getGenerics(scope);
   }
   
   @Override
   public Type getType(Scope scope) {
      if(constraint == null) {
         try {
            constraint = evaluation.compile(scope, left);
         } catch (Exception e) {
            throw new InternalStateException("Import not found", e);
         }
      }
      return constraint.getType(scope);
   }
   
   @Override
   public String getName(Scope scope) {
      if(constraint == null) {
         try {
            constraint = evaluation.compile(scope, left);
         } catch (Exception e) {
            throw new InternalStateException("Import not found", e);
         }
      }
      return constraint.getName(scope);
   }
   
   @Override
   public String toString() {
      return String.valueOf(constraint);
   }
}
