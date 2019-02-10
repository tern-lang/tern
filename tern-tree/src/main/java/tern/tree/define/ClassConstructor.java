package tern.tree.define;

import tern.core.Statement;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeState;
import tern.core.type.TypeBody;
import tern.core.type.TypePart;
import tern.tree.ModifierList;
import tern.tree.annotation.AnnotationList;
import tern.tree.function.ParameterList;

public class ClassConstructor extends MemberConstructor {
   
   public ClassConstructor(AnnotationList annotations, ModifierList modifiers, ParameterList parameters, Statement body){  
      super(annotations, modifiers, parameters, null, body);
   }  
   
   public ClassConstructor(AnnotationList annotations, ModifierList modifiers, ParameterList parameters, TypePart part, Statement body){  
      super(annotations, modifiers, parameters, part, body);
   } 
   
   @Override
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      return assemble(body, type, scope, true);
   }
}