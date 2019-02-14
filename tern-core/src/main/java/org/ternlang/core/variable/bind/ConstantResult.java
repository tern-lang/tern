package org.ternlang.core.variable.bind;

import static org.ternlang.core.scope.index.AddressType.STATIC;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.variable.Constant;
import org.ternlang.core.variable.Value;

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
