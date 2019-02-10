package tern.core.constraint.transform;

import static tern.core.scope.index.AddressType.LOCAL;

import java.util.List;

import tern.core.attribute.Attribute;
import tern.core.constraint.Constraint;
import tern.core.convert.InstanceOfChecker;
import tern.core.error.InternalStateException;
import tern.core.function.Function;
import tern.core.function.Parameter;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.scope.index.Address;
import tern.core.scope.index.AddressCache;
import tern.core.scope.index.ScopeTable;
import tern.core.type.Type;

public class AttributeRule extends ConstraintRule {
   
   private final InstanceOfChecker checker;
   private final Attribute attribute;
   private final ConstraintRule rule;
   private final Address start;
   
   public AttributeRule(ConstraintRule rule, Attribute attribute) {
      this.start = new Address(LOCAL, null, 0);
      this.checker = new InstanceOfChecker();
      this.attribute = attribute;
      this.rule = rule;
   }

   @Override
   public List<Parameter> getParameters(Scope scope, Function function) {
      Scope updated = getScope(scope);
      return rule.getParameters(updated, function);
   }

   @Override
   public Constraint getResult(Scope scope, Constraint returns) {
      Scope updated = getScope(scope);
      return rule.getResult(updated, returns);
   }

   protected Scope getScope(Scope scope) {
      List<Constraint> defaults = attribute.getGenerics();
      int count = defaults.size();

      if(count > 0) {
         ScopeTable table = scope.getTable();
         ScopeState state = scope.getState();
         Constraint first = table.getConstraint(start);

         for(int i = 0; i < count; i++) {
            Address address = AddressCache.getAddress(i);
            Constraint parameter = table.getConstraint(address);
            Constraint constraint = defaults.get(i);
            String name = constraint.getName(scope);
            Constraint existing = state.getConstraint(name);

            if(parameter != null) {
               Type require = constraint.getType(scope);
               Type actual = parameter.getType(scope);

               if(!checker.isInstanceOf(scope, actual, require)) {
                  throw new InternalStateException("Generic parameter '" + name +"' does not match '" + constraint + "'");
               }
               if(existing != null) {
                  Type current = existing.getType(scope);

                  if(current != actual) {
                     throw new InternalStateException("Generic parameter '" + name +"' has already been declared");
                  }
               } else {
                  state.addConstraint(name, parameter);
               }
            } else {
               if(first != null) {
                  throw new InternalStateException("Generic parameter '" + name +"' not specified");
               }
               if(existing != null) {
                  Type require = constraint.getType(scope);
                  Type current = existing.getType(scope);

                  if (current != require) {
                     throw new InternalStateException("Generic parameter '" + name + "' has already been declared");
                  }
               } else {
                  state.addConstraint(name, constraint);
               }
            }
         }
      }
      return scope;
   }

   @Override
   public Constraint getSource() {
      return rule.getSource();
   }      
}