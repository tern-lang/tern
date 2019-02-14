package org.ternlang.core.type.index;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.constraint.AnyConstraint;
import org.ternlang.core.constraint.Constraint;

public class ClassHierarchyIndexer {

   private final GenericConstraintResolver resolver;
   private final Constraint any;
   
   public ClassHierarchyIndexer() {
      this.resolver = new GenericConstraintResolver();
      this.any = new AnyConstraint();
   }
   
   public List<Constraint> index(Class source) throws Exception {
      List<Constraint> hierarchy = new ArrayList<Constraint>();
      
      if(source == Object.class) {
         hierarchy.add(any);
      } else {
         Type[] interfaces = source.getGenericInterfaces();
         Type base = source.getGenericSuperclass(); // the super class
         
         if(base != null) {
            Constraint constraint = resolver.resolve(base);            
            hierarchy.add(constraint);
         }
         for (Type entry : interfaces) {
            Constraint constraint = resolver.resolve(entry);    
            hierarchy.add(constraint);
         }
      }
      return hierarchy;
   }
}