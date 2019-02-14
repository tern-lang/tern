package org.ternlang.tree.constraint;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

public class GenericParameterList implements GenericList {

   private final GenericParameter[] declarations;
   
   public GenericParameterList(GenericParameter... declarations) {
      this.declarations = declarations;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) throws Exception {
      List<Constraint> generics = new ArrayList<Constraint>();
      
      if(declarations != null) {
         for(GenericParameter declaration : declarations) {
            Constraint constraint = declaration.getGeneric(scope);
            generics.add(constraint);
         }
      }
      return generics;
   }
}
