package tern.tree.function;

import static tern.core.constraint.Constraint.NONE;
import static tern.core.function.Origin.DEFAULT;

import java.util.ArrayList;
import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.function.FunctionSignature;
import tern.core.function.Parameter;
import tern.core.function.ParameterBuilder;
import tern.core.function.Signature;
import tern.core.module.Module;
import tern.core.scope.Scope;

public class ParameterListCompiler {
   
   private final ParameterMatchChecker checker;
   private final ParameterDeclaration[] list;
   private final ParameterBuilder builder;
   
   public ParameterListCompiler(ParameterDeclaration... list) {
      this.checker = new ParameterMatchChecker(list);
      this.builder = new ParameterBuilder();
      this.list = list;
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