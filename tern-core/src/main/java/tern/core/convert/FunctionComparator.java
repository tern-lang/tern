package tern.core.convert;

import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.INVALID;
import static tern.core.convert.Score.POSSIBLE;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.function.Parameter;
import tern.core.function.Signature;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class FunctionComparator {
   
   private final ConstraintMatcher matcher;
   
   public FunctionComparator(ConstraintMatcher matcher) {
      this.matcher = matcher;
   }

   public Score compare(Scope scope, Function left, Function right) throws Exception {
      Signature leftSignature = left.getSignature();
      Signature rightSignature = right.getSignature();
      List<Parameter> leftParameters = leftSignature.getParameters();
      List<Parameter> rightParameters = rightSignature.getParameters();

      return compare(scope, leftParameters, rightParameters);
   }

   public Score compare(Scope scope, List<Parameter> left, List<Parameter> right) throws Exception{
      int leftSize = left.size();
      int rightSize = right.size();
      
      if(leftSize == rightSize) {
         Score score = score(scope, left, right);
         
         if(score.isValid()) {
            return score;
         }
      }
      boolean leftVariable = leftSize > 0 && left.get(leftSize - 1).isVariable();
      boolean rightVariable = rightSize > 0 && right.get(rightSize - 1).isVariable();

      if(leftVariable && leftSize <= rightSize) {
         Score score = score(scope, left, right); // compare(a...) == compare(a, b)
         
         if(score.isValid()) {
            return score;
         }
      }
      if(rightVariable && rightSize <= leftSize) {
         Score score = score(scope, right, left); // compare(a, b) == compare(a...)
         
         if(score.isValid()) {
            return score;
         }
      }
      return INVALID;
   }
   
   private Score score(Scope scope, List<Parameter> left, List<Parameter> right) throws Exception{
      int leftSize = left.size();
      
      if(leftSize > 0) {
         Score total = INVALID;
         
         for(int i = 0, j = 0; i < leftSize; i++) {
            Parameter leftParameter = left.get(i);
            Parameter rightParameter = right.get(j);
            Score score = score(scope, leftParameter, rightParameter);
            
            if(score.isInvalid()) { // must check for numbers
               return INVALID;
            }
            total = Score.sum(total, score); // sum for better match
            
            if(!leftParameter.isVariable()) { // if variable stick
               j++;
            }
         }
         return total;
      }
      return EXACT;
   }
   
   private Score score(Scope scope, Parameter left, Parameter right) throws Exception{
      Constraint leftConstraint  = left.getConstraint();
      Constraint rightConstraint = right.getConstraint();
      Type leftType  = leftConstraint.getType(scope);
      Type rightType = rightConstraint.getType(scope);
      ConstraintConverter converter = matcher.match(leftType);
      Score score = converter.score(rightType);
      
      if(left.isVariable()) {
         if(score.isInvalid()) {
            return INVALID;
         }
         return POSSIBLE;
      }
      return score;
   }
}