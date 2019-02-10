package tern.core.variable.index;

import static tern.core.Reserved.PROPERTY_LENGTH;
import static tern.core.constraint.Constraint.INTEGER;

import java.util.Collection;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.core.variable.bind.VariableFinder;

public class CollectionPointer implements VariablePointer<Collection> {
   
   private final TypeInstancePointer pointer;
   private final String name;
   
   public CollectionPointer(VariableFinder finder, String name) {
      this.pointer = new TypeInstancePointer(finder, name);
      this.name = name;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      if(name.equals(PROPERTY_LENGTH)) {
         return INTEGER;
      }
      return pointer.getConstraint(scope, left);
   }
   
   @Override
   public Value getValue(Scope scope, Collection left) {
      if(name.equals(PROPERTY_LENGTH)) {
         int length = left.size();
         return Value.getConstant(length);
      }
      return pointer.getValue(scope, left);
   }
}