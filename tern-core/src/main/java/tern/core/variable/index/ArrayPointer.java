package tern.core.variable.index;

import static tern.core.Reserved.PROPERTY_LENGTH;
import static tern.core.Reserved.TYPE_CLASS;
import static tern.core.constraint.Constraint.INTEGER;
import static tern.core.constraint.Constraint.TYPE;

import java.lang.reflect.Array;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.core.variable.bind.VariableFinder;

public class ArrayPointer implements VariablePointer<Object> {
   
   private final TypeInstancePointer pointer;
   private final String name;
   
   public ArrayPointer(VariableFinder finder, String name) {
      this.pointer = new TypeInstancePointer(finder, name);
      this.name = name;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      if(name.equals(PROPERTY_LENGTH)) {
         return INTEGER;
      }
      if(name.equals(TYPE_CLASS)) {
         return TYPE;
      }
      return pointer.getConstraint(scope, left);
   }

   @Override
   public Value getValue(Scope scope, Object left) {
      if(name.equals(PROPERTY_LENGTH)) {
         int length = Array.getLength(left);
         return Value.getConstant(length);
      }
      return pointer.getValue(scope, left);
   }
}