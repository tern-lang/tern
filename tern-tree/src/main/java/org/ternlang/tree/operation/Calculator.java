package org.ternlang.tree.operation;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.math.NumberOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Calculator {
   
   private final CalculatorStack<CalculationPart> tokens;
   private final CalculatorStack<Evaluation> variables;
   private final List<CalculationPart> order;
   private final AtomicBoolean expansion;
   
   public Calculator() {
      this.tokens = new CalculatorStack<CalculationPart>();      
      this.variables = new CalculatorStack<Evaluation>();
      this.order = new ArrayList<CalculationPart>();
      this.expansion = new AtomicBoolean();
   }

   public Calculation create(Scope scope) throws Exception {
      Evaluation evaluation = assemble(scope);
      boolean expand = expansion.get();

      return new Calculation(evaluation, expand);
   }
   
   private Evaluation assemble(Scope scope) throws Exception {
      while(!tokens.isEmpty()) {
         CalculationPart top = tokens.pop();
         
         if(top != null) {
            order.add(top);
         }
      }      
      for(CalculationPart part : order) {
         NumberOperator operator = part.getOperator();
   
         if(operator != null) {
            Evaluation right = variables.pop();
            Evaluation left = variables.pop();
            
            if(left != null && right != null) {             
               Evaluation evaluation = part.getEvaluation(left, right);
               boolean expand = left.expansion(scope) || right.expansion(scope);

               expansion.compareAndSet(false, expand);
               variables.push(evaluation);
            }                          
         } else {
            Evaluation evaluation = part.getEvaluation(null, null);
            
            if(evaluation != null) {
               boolean expand = evaluation.expansion(scope);

               expansion.compareAndSet(false, expand);
               variables.push(evaluation);
            }
         }               
      }
      return variables.pop();
   }
   
   public void update(CalculationPart part) {
      NumberOperator operator = part.getOperator();
      
      if(operator != null) {
         while(!tokens.isEmpty()) {
            CalculationPart top = tokens.pop();
            NumberOperator other = top.getOperator();
                                 
            if(other.priority < operator.priority) {
               tokens.push(top);
               break;
            } else {
               order.add(top);
            }            
         }
         tokens.push(part);
      } else {
         order.add(part);
      }
   }

   private static class Calculation extends Evaluation {

      private final Evaluation evaluation;
      private final boolean expand;

      public Calculation(Evaluation evaluation, boolean expand) {
         this.evaluation = evaluation;
         this.expand = expand;
      }

      @Override
      public boolean expansion(Scope scope) throws Exception {
         return expand;
      }

      @Override
      public void define(Scope scope) throws Exception {
         evaluation.define(scope);
      }

      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         return evaluation.compile(scope, left);
      }

      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         return evaluation.evaluate(scope, left);
      }
   }
}