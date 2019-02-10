package tern.core.convert;

import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.INVALID;
import static tern.core.convert.Score.POSSIBLE;

import tern.core.error.InternalArgumentException;
import tern.core.type.Type;

public class ClassConverter extends ConstraintConverter {
   
   private final Type type;
   
   public ClassConverter(Type type) {
      this.type = type;
   }

   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class real = actual.getType();
         
         if(real == Class.class) {
            return EXACT;
         }
         if(Type.class.isAssignableFrom(real)) {
            return EXACT;
         }
         return INVALID;
      }
      return POSSIBLE;
   }

   @Override
   public Score score(Object value) throws Exception {
      if(value != null) {
         Class real = value.getClass();
         
         if(real == Class.class) {
            return EXACT;
         }
         if(Type.class.isAssignableFrom(real)) {
            return EXACT;
         }
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Object convert(Object value) throws Exception {
      if(value != null) {
         Class real = value.getClass();
         
         if(real == Class.class) {
            return value;
         }
         if(Type.class.isInstance(value)) {
            Type type = (Type)value;
            return type.getType();
         }
         throw new InternalArgumentException("Conversion from " + type + " to class is not possible");
      }
      return null;
   }

}