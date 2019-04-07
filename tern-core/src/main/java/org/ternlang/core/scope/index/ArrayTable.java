package org.ternlang.core.scope.index;

import static org.ternlang.core.scope.index.AddressType.LOCAL;

import java.util.Iterator;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.variable.Value;

public class ArrayTable implements ScopeTable {

   private AddressArray<Constraint> constraints;
   private AddressArray<Value> externals;
   private AddressArray<Value> locals;

   public ArrayTable() {
      this(0);
   }
   
   public ArrayTable(int count) {
      this.constraints = new AddressArray<Constraint>(count);
      this.externals = new AddressArray<Value>(count);
      this.locals = new AddressArray<Value>(count);
   }

   @Override
   public Iterator<Value> iterator() {
      return locals.iterator();
   }

   @Override
   public Value getValue(Address address) {
      AddressType type = address.getType();
      
      if(type == LOCAL) {
         return locals.get(address);
      } 
      return externals.get(address);
   }
   
   @Override
   public void addValue(Address address, Value value) {
      AddressType type = address.getType();
            
      if(type == LOCAL) {
         locals.set(address, value);
      } else {
         externals.set(address, value);
      }
   }

   @Override
   public Constraint getConstraint(Address address) {
      return constraints.get(address);
   }
   
   @Override
   public void addConstraint(Address address, Constraint constraint) {
      constraints.set(address, constraint);
   }
}
