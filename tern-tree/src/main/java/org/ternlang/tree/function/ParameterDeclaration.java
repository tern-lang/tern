package org.ternlang.tree.function;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.DeclarationConstraint;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.scope.Scope;
import org.ternlang.tree.Modifier;
import org.ternlang.tree.ModifierChecker;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.NameReference;
import org.ternlang.tree.annotation.AnnotationList;

public class ParameterDeclaration {
   
   private DeclarationConstraint constraint;
   private AnnotationList annotations;
   private ModifierChecker checker;
   private NameReference reference;
   private Parameter parameter;
   private Modifier modifier;
   
   public ParameterDeclaration(AnnotationList annotations, ModifierList modifiers, Evaluation identifier){
      this(annotations, modifiers, identifier, null, null);
   }
   
   public ParameterDeclaration(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, Constraint constraint){
      this(annotations, modifiers, identifier, null, constraint);
   }
   
   public ParameterDeclaration(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, Modifier modifier){
      this(annotations, modifiers, identifier, modifier, null);
   }
   
   public ParameterDeclaration(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, Modifier modifier, Constraint constraint){
      this.constraint = new DeclarationConstraint(constraint);
      this.reference = new NameReference(identifier);
      this.checker = new ModifierChecker(modifiers);
      this.annotations = annotations;
      this.modifier = modifier;
   }

   public Parameter get(Scope scope, int index) throws Exception {
      if(parameter == null) {
         parameter = create(scope, index);
         
         if(parameter != null) {
            annotations.apply(scope, parameter);
         }        
      }
      return parameter.getParameter(index);
   }

   private Parameter create(Scope scope, int index) throws Exception {
      boolean constant = checker.isConstant();
      int modifiers = checker.getModifiers();
      Constraint declare = constraint.getConstraint(scope, modifiers);
      String name = reference.getName(scope);

      return new Parameter(name, declare, index, constant, modifier != null);
   }
}