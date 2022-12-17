package org.ternlang.tree.function;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.function.Origin.DEFAULT;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.FunctionSignature;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.ParameterBuilder;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class ParameterListCompiler {
   
   private final ParameterMatchChecker checker;
   private final ParameterDeclaration[] list;
   private final ParameterBuilder builder;
   
   public ParameterListCompiler(ParameterDeclaration... list) {
      this.checker = new ParameterMatchChecker(list);
      this.builder = new ParameterBuilder();
      this.list = list;
   }

   public Signature expose(Scope scope) throws Exception{
      return expose(scope, null);
   }

   public Signature expose(Scope scope, String prefix) throws Exception{ // no generics
      List<Parameter> parameters = new ArrayList<Parameter>();

      if(prefix != null) {
         Parameter parameter = builder.create(NONE, prefix, 0); // this is constrained by type
         parameters.add(parameter);
      }
      Module module = scope.getModule();
      boolean variable = checker.isVariable(scope);
      int start = parameters.size();

      for(int i = 0; i < list.length; i++) {
         ParameterDeclaration declaration = list[i];

         if(declaration != null) {
            Parameter parameter = declaration.get(scope, start + i);
            parameters.add(parameter);
         }
      }
      return new FunctionSignature(parameters, EMPTY_LIST, module, null, DEFAULT, false, variable);
   }

   public Signature compile(Scope scope, List<Constraint> generics) throws Exception{
      return compile(scope, generics, null);
   }

   public Signature compile(Scope scope, List<Constraint> generics, String prefix) throws Exception{
      List<Parameter> parameters = new ArrayList<Parameter>();
      
      if(prefix != null) {
         Parameter parameter = builder.create(NONE, prefix, 0); // this is constrained by type
         parameters.add(parameter);
      }
      Module module = scope.getModule();
      boolean variable = checker.isVariable(scope);
      boolean absolute = checker.isAbsolute(scope);
      int start = parameters.size();
      
      for(int i = 0; i < list.length; i++) {
         ParameterDeclaration declaration = list[i];
         
         if(declaration != null) {
            Parameter parameter = declaration.get(scope, start + i);
            Constraint constraint = parameter.getConstraint();

            constraint.getType(scope);
            parameters.add(parameter);
         }
      }
      return new FunctionSignature(parameters, generics, module, null, DEFAULT, absolute, variable);
   }
}