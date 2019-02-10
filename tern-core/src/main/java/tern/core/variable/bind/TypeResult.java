package tern.core.variable.bind;

import static tern.core.ModifierType.CLASS;
import static tern.core.scope.index.AddressType.TYPE;

import tern.core.constraint.Constraint;
import tern.core.constraint.StaticConstraint;
import tern.core.scope.index.Address;
import tern.core.type.Type;
import tern.core.variable.Constant;
import tern.core.variable.Value;

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
