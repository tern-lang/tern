package tern.core.variable;

import static tern.core.constraint.Constraint.NONE;

import tern.core.constraint.Constraint;

public abstract class Value {
   
   public static final Value NULL = new Null();
   
   public static Value getNull() {
      return new Null();
   }
   
   public static Value getConstant(Object value) {
      return new Constant(value);
   }
   
   public static Value getConstant(Object value, Constraint type) {
      return new Constant(value, type);
   }
   
   public static Value getConstant(Object value, Constraint type, int modifiers) {
      return new Constant(value, type, modifiers);
   }
   
   public static Value getReference(Object value) {
      return new Reference(value);
   }
   
   public static Value getReference(Object value, Constraint type) {
      return new Reference(value, type);
   }
   
   public static Value getProperty(Object value, Constraint type, int modifiers) {
      return new Reference(value, type, modifiers);
   }
   
   public static Value getBlank(Object value, Constraint type, int modifiers) {
      return new Blank(value, type, modifiers);
   }
   
   public static Value getTransient(Object value) {
      return new Transient(value);
   }
   
   public static Value getTransient(Object value, Constraint type) {
      return new Transient(value, type);
   }

   public double getDouble() {
      Number number = getNumber();

      if (number != null) {
         return number.doubleValue();
      }
      return 0;
   }

   public long getLong() {
      Number number = getNumber();

      if (number != null) {
         return number.longValue();
      }
      return 0;
   }

   public int getInteger() {
      Number number = getNumber();

      if (number != null) {
         return number.intValue();
      }
      return 0;
   }

   public Number getNumber() {
      Object value = getValue();

      if (value != null) {
         return ValueMapper.toNumber(value); 
      }
      return null;
   }
   
   public char getCharacter() {
      Object value = getValue();

      if (value != null) {
         return ValueMapper.toCharacter(value); 
      }
      return 0;
   }
   
   public String getString() {
      Object value = getValue();

      if (value != null) {
         return value.toString();
      }
      return null;
   }   
   
   public Class getType() {
      Object value = getValue();
      
      if(value != null) {
         return value.getClass();         
      }
      return null;
   }     
   
   public Constraint getConstraint(){
      return NONE; 
   }   
   
   public boolean isProperty() {
      return false;
   }
   
   public boolean isConstant() {
      return false;
   }
   
   public int getModifiers(){
      return -1;
   }
   
   public String getName() {
      return null;
   }
   
   public abstract <T> T getValue();
   public abstract void setValue(Object value);
   
   
}