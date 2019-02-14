package org.ternlang.core.type.index;

import static java.util.Collections.EMPTY_LIST;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.constraint.Constraint;

public class GenericIndexer {
   
   private final GenericConstraintResolver resolver;
   
   public GenericIndexer(TypeIndexer indexer){
      this.resolver = new GenericConstraintResolver();
   }
   
   public List<Constraint> index(Class type) {
      TypeVariable[] variables = type.getTypeParameters();
      
      if(variables.length > 0) {
         List<Constraint> constraints = new ArrayList<Constraint>();
         
         for(int i = 0; i < variables.length; i++) {
            TypeVariable variable = variables[i];            
            Constraint constraint = resolver.resolve(variable);
            
            constraints.add(constraint);
         }
         return constraints;
      }
      return EMPTY_LIST;
   }
}
