package org.ternlang.core.variable;

import static org.ternlang.core.variable.Constant.BYTE;
import static org.ternlang.core.variable.Constant.CHARACTER;
import static org.ternlang.core.variable.Constant.DOUBLE;
import static org.ternlang.core.variable.Constant.FLOAT;
import static org.ternlang.core.variable.Constant.INTEGER;
import static org.ternlang.core.variable.Constant.LONG;
import static org.ternlang.core.variable.Constant.SHORT;

public class ValueCache {

   private static final Value[][] CONSTANTS = new Value[5][2050];
   private static final int HIGH = 1024;
   private static final int LOW = -1024; 

   static {
      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
         CONSTANTS[0][i] = Value.getTransient((byte)j, BYTE);
      }
      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
         CONSTANTS[1][i] = Value.getTransient((short)j, SHORT);
      }
      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
         CONSTANTS[2][i] = Value.getTransient(j, INTEGER);
      }
      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
         CONSTANTS[3][i] = Value.getTransient((long)j, LONG);
      }
      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
         CONSTANTS[4][i] = Value.getTransient((char)j, CHARACTER);
      }
   }
   
   public static Value getByte(int value) {
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[0][value + -LOW];
      }
      return Value.getTransient((byte)value, BYTE);
   }
   
   public static Value getShort(int value) {
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[1][value + -LOW];
      }
      return Value.getTransient((short)value, SHORT);
   }
   
   public static Value getInteger(int value) {
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[2][value + -LOW];
      }
      return Value.getTransient(value, INTEGER);
   }
   
   public static Value getLong(long value) {
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[3][(int)value + -LOW];
      }
      return Value.getTransient(value, LONG);
   }
   
   public static Value getCharacter(char value){
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[4][value + -LOW];
      }
      return Value.getTransient(value, CHARACTER);
   }
   
   public static Value getCharacter(int value){
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[4][value + -LOW];
      }
      return Value.getTransient((char)value, CHARACTER);
   }
   
   public static Value getFloat(float value) {
      return Value.getTransient(value, FLOAT);
   }
   
   public static Value getDouble(double value) {
      return Value.getTransient(value, DOUBLE);
   }
}