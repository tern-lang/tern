package org.ternlang.tree.function;

import static org.ternlang.core.ModifierType.CONSTANT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.TypeInspector;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.AddressCache;
import org.ternlang.core.scope.index.Local;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.type.Type;

public class ParameterCollector {

   private List<ParameterAppender> appenders;
   private Signature signature;
   private int modifiers;

   public ParameterCollector(Signature signature, int modifiers) {
      this.signature = signature;
      this.modifiers = modifiers;
   }

   public List<ParameterAppender> collect() {
      if(appenders == null) {
         appenders = create();
      }
      return appenders;
   }

   private List<ParameterAppender> create() {
      List<Parameter> parameters = signature.getParameters();
      List<Constraint> generics = signature.getGenerics();
      int required = parameters.size();
      int optional = generics.size();

      if(required + optional > 0) {
         List<ParameterAppender> appenders = new ArrayList<ParameterAppender>();

         for(int i = optional - 1; i >= 0; i--) {
            ParameterAppender appender = createGeneric(i);

            if(appender != null) {
               appenders.add(appender);
            }
         }
         for(int i = required - 1; i >= 0; i--) {
            ParameterAppender appender = createArgument(i);

            if(appender != null) {
               appenders.add(appender);
            }
         }
         return Collections.unmodifiableList(appenders);
      }
      return Collections.emptyList();
   }

   private ParameterAppender createGeneric(int index) {
      List<Constraint> generics = signature.getGenerics();
      Constraint constraint = generics.get(index);
      Address address = AddressCache.getAddress(index);

      return new GenericParameterAppender(constraint, address);
   }

   private ParameterAppender createArgument(int index) {
      List<Parameter> parameters = signature.getParameters();
      Parameter parameter = parameters.get(index);

      if(parameter.isConstant()) {
         return createArgument(signature, index, modifiers | CONSTANT.mask);
      }
      return createArgument(signature, index, modifiers);
   }

   private ParameterAppender createArgument(Signature signature, int index, int modifiers) {
      List<Parameter> parameters = signature.getParameters();
      Parameter parameter = parameters.get(index);
      Constraint constraint = parameter.getConstraint();
      Address address = parameter.getAddress();
      String name = parameter.getName();
      int length = parameters.size();

      if(index < length -1) {
         return new FixedParameterAppender(constraint, name, address, modifiers);
      }
      if (signature.isVariable()) {
         return new VariableParameterAppender(constraint, name, address, modifiers);
      }
      return new FixedParameterAppender(constraint, name, address, modifiers);
   }

   private static class GenericParameterAppender implements ParameterAppender {

      private final Constraint constraint;
      private final Address address;

      public GenericParameterAppender(Constraint constraint, Address address) {
         this.constraint = constraint;
         this.address = address;
      }

      @Override
      public void append(Scope scope, Object[] arguments) throws Exception {
         ScopeTable table = scope.getTable();
         table.addConstraint(address, constraint);
      }
   }

   private static class FixedParameterAppender implements ParameterAppender {

      private final Constraint constraint;
      private final Address address;
      private final String name;
      private final int modifiers;

      public FixedParameterAppender(Constraint constraint, String name, Address address, int modifiers) {
         this.constraint = constraint;
         this.modifiers = modifiers;
         this.address = address;
         this.name = name;
      }

      @Override
      public void append(Scope scope, Object[] arguments) throws Exception {
         Local local = create(scope, arguments);

         if(local != null) {
            ScopeTable table = scope.getTable();
            ScopeState state = scope.getState();

            if (ModifierType.isClosure(modifiers)) {
               state.addValue(name, local);
            }
            table.addValue(address, local);
         }
      }

      private Local create(Scope scope, Object[] arguments) throws Exception {
         int index = address.getOffset();
         Object value = arguments[index];

         if (ModifierType.isConstant(modifiers)) {
            return Local.getConstant(value, name, constraint);
         }
         return Local.getReference(value, name, constraint);
      }
   }

   private static class VariableParameterAppender implements ParameterAppender {

      private final TypeInspector inspector;
      private final Constraint constraint;
      private final Address address;
      private final String name;
      private final int modifiers;

      public VariableParameterAppender(Constraint constraint, String name, Address address, int modifiers) {
         this.inspector = new TypeInspector();
         this.constraint = constraint;
         this.modifiers = modifiers;
         this.address = address;
         this.name = name;
      }

      @Override
      public void append(Scope scope, Object[] arguments) throws Exception {
         Local local = create(scope, arguments);

         if(local != null) {
            ScopeTable table = scope.getTable();
            ScopeState state = scope.getState();

            if (ModifierType.isClosure(modifiers)) {
               state.addValue(name, local);
            }
            table.addValue(address, local);
         }
      }

      private Local create(Scope scope, Object[] arguments) throws Exception {
         int index = address.getOffset();
         Object value = arguments[index];
         Object[] list = (Object[]) value;

         if(list.length > 0) {
            Type type = constraint.getType(scope);

            for (int i = 0; i < list.length; i++) {
               Object entry = list[i];

               if (!inspector.isCompatible(type, entry)) {
                  throw new InternalStateException("Parameter '" + name + "...' does not match constraint '" + type + "'");
               }
            }
         }
         if (ModifierType.isConstant(modifiers)) {
            return Local.getConstant(value, name, constraint);
         }
         return Local.getReference(value, name, constraint);
      }
   }
}
