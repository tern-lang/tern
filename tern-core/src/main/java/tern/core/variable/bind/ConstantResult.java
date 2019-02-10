package tern.core.variable.bind;

import static tern.core.scope.index.AddressType.STATIC;

import tern.core.constraint.Constraint;
import tern.core.scope.index.Address;
import tern.core.variable.Constant;
import tern.core.variable.Value;

public class ConstantResult implements VariableResult {
   
   private final Constraint constraint;
   private final Value value;
   
   public ConstantResult(Object value, Constraint constraint) {
      this.value = new Constant(value);
      this.constraint = constraint;
   }
   
   @Override
   public Address getAddress(int offset) {
      return STATIC.getAddress(null, offset);
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
