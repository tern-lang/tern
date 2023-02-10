package org.ternlang.tree.function;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.ConstraintMapper;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;

public class ParameterList {
   
   private ParameterListCompiler compiler;
   private ConstraintMapper mapper;
   private Signature signature;
   
   public ParameterList(ParameterDeclaration... list) {
      this.compiler = new ParameterListCompiler(list);
      this.mapper = new ConstraintMapper();
   }

   public Signature expose(Scope scope) throws Exception {
      return expose(scope, null);
   }

   public Signature expose(Scope scope, String prefix) throws Exception {
      if(prefix == null) {
         return compiler.expose(scope);
      }
      return compiler.expose(scope, prefix);
   }

   public Signature create(Scope scope, List<Constraint> generics) throws Exception{
      return create(scope, generics, null);
   }

   public Signature create(Scope scope, List<Constraint> generics, String prefix) throws Exception{
      if(signature == null) {
         List<Constraint> constraints = mapper.map(scope, generics);
         
         if(prefix == null) {
            signature = compiler.compile(scope, constraints);
         } else {
            signature = compiler.compile(scope, constraints, prefix);
         }
      }
      return signature;
   }
}