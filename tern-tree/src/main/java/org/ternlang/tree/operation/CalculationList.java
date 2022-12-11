package org.ternlang.tree.operation;

import static org.ternlang.core.Reserved.PLACE_HOLDER;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.Expansion;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.PlaceHolder;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.closure.Closure;
import org.ternlang.tree.closure.ClosureParameterList;
import org.ternlang.tree.constraint.GenericList;
import org.ternlang.tree.function.ParameterDeclaration;
import org.ternlang.tree.reference.GenericArgumentList;

public class CalculationList implements Compilation { 
   
   private CalculationPart[] parts; 

   public CalculationList(CalculationPart... parts) {
      this.parts = parts;
   }

   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Evaluation evaluation = create(module, path, line);
      Expansion expansion = evaluation.expansion(scope);

      if(expansion.isClosure()) {
         return closure(evaluation, module, path, line);
      }
      return evaluation;
   }

   private Evaluation create(Module module, Path path, int line) throws Exception {
      Calculator calculator = new Calculator();
      
      for(CalculationPart part : parts) {
         calculator.update(part);
      }
      return calculator.create();
   }

   private Evaluation closure(Evaluation evaluation, Module module, Path path, int line) throws Exception {
      StringToken token = new StringToken(PLACE_HOLDER);
      PlaceHolder holder = new PlaceHolder(token);
      ModifierList modifiers = new ModifierList();
      GenericList generics = new GenericArgumentList();
      AnnotationList annotations = new AnnotationList();
      ParameterDeclaration declaration = new ParameterDeclaration(annotations, modifiers, holder);
      ClosureParameterList parameters = new ClosureParameterList(declaration);
      Closure closure = new Closure(modifiers, generics, parameters, evaluation);

      return closure.compile(module, path, line);
   }
}