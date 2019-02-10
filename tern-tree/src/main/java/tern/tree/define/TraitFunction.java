package tern.tree.define;

import tern.core.Evaluation;
import tern.core.ModifierType;
import tern.core.Statement;
import tern.core.scope.Scope;
import tern.core.type.TypeState;
import tern.core.type.Type;
import tern.core.constraint.Constraint;
import tern.core.type.TypeBody;
import tern.tree.ModifierList;
import tern.tree.annotation.AnnotationList;
import tern.tree.constraint.FunctionName;
import tern.tree.function.ParameterList;

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