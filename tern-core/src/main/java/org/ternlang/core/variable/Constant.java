package org.ternlang.core.variable;

import static org.ternlang.core.ModifierType.CONSTANT;
import static org.ternlang.core.constraint.Constraint.NONE;

import java.util.List;

import org.ternlang.core.constraint.ClassConstraint;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.type.Type;

public class Constant extends Value {   
   
   public static final Constraint NUMBER = new ClassConstraint(Number.class, CONSTANT.mask);
   public static final Constraint INTEGER = new ClassConstraint(Integer.class, CONSTANT.mask);
   public static final Constraint LONG = new ClassConstraint(Long.class, CONSTANT.mask);
   public static final Constraint FLOAT = new ClassConstraint(Float.class, CONSTANT.mask);
   public static final Constraint DOUBLE = new ClassConstraint(Double.class, CONSTANT.mask);
   public static final Constraint SHORT = new ClassConstraint(Short.class, CONSTANT.mask);
   public static final Constraint BYTE = new ClassConstraint(Byte.class, CONSTANT.mask);
   public static final Constraint STRING = new ClassConstraint(String.class, CONSTANT.mask);
   public static final Constraint BOOLEAN = new ClassConstraint(Boolean.class, CONSTANT.mask);
   public static final Constraint CHARACTER = new ClassConstraint(Character.class, CONSTANT.mask);
   public static final Constraint LIST = new ClassConstraint(List.class, CONSTANT.mask);
   public static final Constraint TYPE = new ClassConstraint(Type.class, CONSTANT.mask);
   
   private final Constraint constraint;
   private final Object value;
   private final int modifiers;
   
   public Constant(Object value) {
      this(value, NONE);
   }

   public Constant(Object value, Constraint constraint) {
      this(value, constraint, 0);
   }
   
   public Constant(Object value, Constraint constraint, int modifiers) {
      this.modifiers = modifiers |= CONSTANT.mask;
      this.constraint = constraint;
      this.value = value;
   }
   
   @Override
   public boolean isConstant() {
      return true;
   }   
   
   @Override
   public boolean isProperty(){
      return modifiers != -1;
   }
   
   @Override
   public int getModifiers(){
      return modifiers;
   }
   
   @Override
   public Constraint getConstraint() {
      return constraint;
   }
   
   @Override
   public <T> T getValue() {
      return (T)value;
   }
   
   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of constant");
   } 
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}