package org.ternlang.tree.define;

import org.ternlang.core.Evaluation;
import org.ternlang.core.ModifierType;
import org.ternlang.core.Statement;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.type.Type;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.type.TypeBody;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.constraint.FunctionName;
import org.ternlang.tree.function.ParameterList;

public class TraitFunction extends MemberFunction {
 
   public TraitFunction(AnnotationList annotations, ModifierList list, FunctionName identifier, ParameterList parameters){
      super(annotations, list, identifier, parameters);
   }
   
   public TraitFunction(AnnotationList annotations, ModifierList list, FunctionName identifier, ParameterList parameters, Constraint constraint){
      super(annotations, list, identifier, parameters, constraint);
   }
   
   public TraitFunction(AnnotationList annotations, ModifierList list, FunctionName identifier, ParameterList parameters, Statement body){
      super(annotations, list, identifier, parameters, body);
   }
   
   public TraitFunction(AnnotationList annotations, ModifierList list, FunctionName identifier, ParameterList parameters, Constraint constraint, Statement body){
      super(annotations, list, identifier, parameters, constraint, body);
   } 
   
   @Override
   protected TypeState assemble(TypeBody body, Type type, Scope scope, int mask) throws Exception {
      if(statement == null) {
         return super.assemble(body, type, scope, ModifierType.ABSTRACT.mask);
      }  
      return super.assemble(body, type, scope, 0);
   }
}