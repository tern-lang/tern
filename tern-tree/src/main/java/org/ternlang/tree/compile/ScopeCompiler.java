package org.ternlang.tree.compile;

import java.util.List;
import java.util.Set;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.Module;
import org.ternlang.core.property.Property;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.Local;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.type.TypeLoader;
import org.ternlang.core.variable.Value;

public abstract class ScopeCompiler {

   protected ScopeCompiler() {
      super();
   }

   protected void compileProperties(Scope scope, Type type) {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Property> properties = extractor.getProperties(type);
      ScopeState state = scope.getState();

      for(Property property : properties) {
         String alias = property.getAlias();
         Value field = compileProperty(scope, property);
         Value current = state.getValue(alias);

         if(current == null) {
            state.addValue(alias, field);
         }
      }
   }

   protected Value compileProperty(Scope scope, Property property) {
      Constraint constraint = property.getConstraint();
      Type result = constraint.getType(scope);

      if(constraint.isConstant()) {
         return Value.getConstant(property, constraint);
      }
      return Local.getReference(property, constraint);
   }

   protected void compileParameters(Scope scope, Function function) {
      Signature signature = function.getSignature();
      ScopeTable table = scope.getTable();
      List<Parameter> parameters = signature.getParameters();
      int count = parameters.size();

      for(int i = 0; i < count; i++) {
         Parameter parameter = parameters.get(i);
         Address address = parameter.getAddress();
         Local local = compileParameter(scope, parameter);

         table.addValue(address, local);
      }
   }

   protected Local compileParameter(Scope scope, Parameter parameter) {
      String name = parameter.getName();
      Constraint constraint = parameter.getConstraint();

      if(parameter.isVariable()) {
         constraint = compileArray(scope, constraint);
      }
      if(parameter.isConstant()) {
         return Local.getConstant(parameter, name, constraint);
      }
      return Local.getReference(parameter, name, constraint);
   }

   protected Constraint compileArray(Scope scope, Constraint constraint) {
      Type type = constraint.getType(scope);

      if(type != null) {
         Module module = type.getModule();
         Context context = module.getContext();
         TypeLoader loader = context.getLoader();
         String prefix = module.getName();
         String name = type.getName();
         Type array = loader.loadArrayType(prefix, name, 1);

         return Constraint.getConstraint(array);
      }
      return constraint;
   }
   
   public abstract Scope define(Scope local, Type type) throws Exception;
   public abstract Scope compile(Scope local, Type type, Function function) throws Exception;
}
