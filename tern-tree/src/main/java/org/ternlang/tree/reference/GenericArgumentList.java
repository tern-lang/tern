package org.ternlang.tree.reference;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.constraint.GenericList;

public class GenericArgumentList implements GenericList {

   private final GenericArgument[] arguments;

   public GenericArgumentList(GenericArgument... arguments) {
      this.arguments = arguments;
   }
   
   public List<String> getImports(Scope scope) throws Exception {
      List<String> result = new ArrayList<String>();
      
      for(GenericArgument argument : arguments) {
         Constraint constraint = argument.getConstraint();
         List<String> imports = constraint.getImports(scope);
         
         result.addAll(imports);
      }
      return result;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) throws Exception {
      List<Constraint> result = new ArrayList<Constraint>();
      
      for(GenericArgument argument : arguments) {
         Constraint constraint = argument.getConstraint();
         Type type = constraint.getType(scope);
         
         if(type == null) {
            throw new InternalStateException("Could not find constraint");
         }
         result.add(constraint);
      }
      return result;
   }
}
