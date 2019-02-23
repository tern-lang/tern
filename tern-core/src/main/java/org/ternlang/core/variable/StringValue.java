package org.ternlang.core.variable;

import static org.ternlang.core.ModifierType.CONSTANT;
import static org.ternlang.core.constraint.Constraint.CHARACTER;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;

public class StringValue extends Value {

   private final Integer index;
   private final String text;

   public StringValue(String text, Integer index) {
      this.text = text;
      this.index = index;
   }

   @Override
   public boolean isConstant() {
      return true;
   }

   @Override
   public int getModifiers(){
      return CONSTANT.mask;
   }

   @Override
   public Constraint getConstraint() {
      return CHARACTER;
   }

   @Override
   public Class getType() {
      return Character.class;
   }

   @Override
   public Object getValue(){
      return text.charAt(index);
   }

   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of string");
   }

   @Override
   public String toString() {
      return text;
   }
}