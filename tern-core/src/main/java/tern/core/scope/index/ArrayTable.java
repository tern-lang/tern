package tern.core.scope.index;

import static tern.core.scope.index.AddressType.LOCAL;

import java.util.Arrays;
import java.util.Iterator;

import tern.common.EmptyIterator;
import tern.core.constraint.Constraint;
import tern.core.variable.Value;

public class ArrayTable implements ScopeTable {

   private Constraint[] constraints;
   private Value[] externals;
   private Value[] locals;

   public ArrayTable() {
      this(0);
   }
   
   public ArrayTable(int count) {
      this.constraints = new Constraint[count];
      this.externals = new Value[count];
      this.locals = new Value[count];
   }

   @Override
   public Iterator<Value> iterator() {
      if(locals.length > 0) {
         return new LocalIterator(locals);
      }
      return new EmptyIterator<Value>();
   }

   @Override
   public Value getValue(Address address) {
      AddressType type = address.getType();
      int index = address.getOffset();
      
      if(type == LOCAL) {
         if(index < locals.length && index >= 0) {
            return locals[index];
         }
      } else {
         if(index < externals.length && index >= 0) {
            return externals[index];
         }
      }
      return null;
   }
   
   @Override
   public void addValue(Address address, Value value) {
      AddressType type = address.getType();
      String name = address.getName();
      int index = address.getOffset();
      
      if(value == null) {
         throw new IllegalStateException("Value for '" + name + "' at index " + index + " is null");
      }      
      if(type == LOCAL) {
         if(index >= locals.length) {
            Value[] copy = new Value[index == 0 ? 2 : index * 2];
            
            for(int i = 0; i < locals.length; i++) {
               copy[i] = locals[i];
            }
            locals = copy;
         }
         locals[index] = value;
      } else {
         if(index >= externals.length) {
            Value[] copy = new Value[index == 0 ? 2 : index * 2];
            
            for(int i = 0; i < externals.length; i++) {
               copy[i] = externals[i];
            }
            externals = copy;
         }
         externals[index] = value;
      }
   }

   @Override
   public Constraint getConstraint(Address address) {
      int index = address.getOffset();
      
      if(index < constraints.length && index >= 0) {
         return constraints[index];
      }
      return null;
   }
   
   @Override
   public void addConstraint(Address address, Constraint constraint) {
      int index = address.getOffset();
      
      if(constraint == null) {
         throw new IllegalStateException("Constraint at index " + index + " is null");
      }
      if(index >= constraints.length) {
         Constraint[] copy = new Constraint[index == 0 ? 2 : index * 2];
         
         for(int i = 0; i < constraints.length; i++) {
            copy[i] = constraints[i];
         }
         constraints = copy;
      }
      constraints[index] = constraint;
   }
   
   @Override
   public String toString() {
      return Arrays.toString(locals);
   }
   
   private static class LocalIterator implements Iterator<Value> {
      
      private Value[] table;
      private Value local;
      private int index;

      public LocalIterator(Value[] table) {
         this.table = table;
      }
      
      @Override
      public boolean hasNext() {
         while(local == null) {
            if(index >= table.length) {
               break;
            }
            local = table[index++];
         }
         return local != null;
      }

      @Override
      public Value next() {
         Value next = null;
         
         if(hasNext()) {
            next = local;
            local = null;
         }
         return next;
      }
   }
}
