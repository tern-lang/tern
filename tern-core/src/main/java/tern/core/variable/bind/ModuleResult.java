package tern.core.variable.bind;

import static tern.core.scope.index.AddressType.MODULE;

import tern.core.constraint.Constraint;
import tern.core.constraint.ModuleConstraint;
import tern.core.module.Module;
import tern.core.scope.index.Address;
import tern.core.variable.Constant;
import tern.core.variable.Value;

public class ModuleResult implements VariableResult {
   
   private final Constraint constraint;
   private final Value value;
   private final String name;
   
   public ModuleResult(Module module, String name) {
      this.constraint = new ModuleConstraint(module);
      this.value = new Constant(module, constraint);
      this.name = name;
   }
   
   @Override
   public Address getAddress(int offset) {
      return MODULE.getAddress(name, offset);
   }

   @Override
   public Constraint getConstraint(Constraint left) {
      return constraint;
   }

   @Override
   public Value getValue(Object left) {
      return value;
   }

}
