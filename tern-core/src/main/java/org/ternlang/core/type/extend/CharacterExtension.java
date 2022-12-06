package org.ternlang.core.type.extend;

public class CharacterExtension {

   public CharacterExtension() {
      super();
   }

   public Integer plus(Character left, Character right) {
      return left.charValue() + right.charValue();
   }

   public Integer minus(Character left, Character right) {
      return left.charValue() - right.charValue();
   }

   public Integer plus(Character left, Number right) {
      return left.charValue() + right.intValue();
   }

   public Integer minus(Character left, Number right) {
      return left.charValue() - right.intValue();
   }

   public Boolean isDigit(Character value) {
      return Character.isDigit(value);
   }

   public Boolean isLetter(Character value) {
      return Character.isLetter(value);
   }

   public Boolean isLetterOrDigit(Character value) {
      return Character.isLetterOrDigit(value);
   }

   public Boolean isLowerCase(Character value) {
      return Character.isLowerCase(value);
   }

   public Boolean isUpperCase(Character value) {
      return Character.isUpperCase(value);
   }

   public Character toLowerCase(Character value) {
      return Character.toLowerCase(value);
   }

   public Character toUpperCase(Character value) {
      return Character.toUpperCase(value);
   }

   public Integer toInteger(Character value) {
      return (int)value.charValue();
   }

   public Long toLong(Character value) {
      return (long)value.charValue();
   }

   public Byte toByte(Character value) {
      return (byte)value.charValue();
   }

   public Short toShort(Character value) {
      return (short)value.charValue();
   }
}
