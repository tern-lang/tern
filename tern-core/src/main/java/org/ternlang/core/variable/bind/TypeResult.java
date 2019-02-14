package org.ternlang.core.variable.bind;

import static org.ternlang.core.ModifierType.CLASS;
import static org.ternlang.core.scope.index.AddressType.TYPE;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.StaticConstraint;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Constant;
import org.ternlang.core.variable.Value;

public class TypeResult implements VariableResult {
   
   private final Constraint constraint;
   private final Value value;
   private final String name;
   
   public TypeResult(Type type, String name) {
      this.constraint = new StaticConstraint(type, CLASS.mask);
      this.value = new Constant(type, constraint);
      this.name = name;
   }
   
   @Override
   public Address getAddress(int offset) {
      return TYPE.getAddress(name, offset);
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
