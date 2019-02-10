package tern.core.convert;

import tern.core.convert.NumberMatcher;
import tern.core.convert.NumberType;

import junit.framework.TestCase;

public class NumberMatcherTest extends TestCase {

   public void testNumberMatcher() throws Exception {
      assertDouble("0.333", 0.333d);
      assertDouble("1244.3", 1244.3d);
      assertDouble(".3", .3d);
      assertDouble(".002", .002d);
      assertDouble("+.002", +.002);
      assertDouble("-.034e-12", -.034e-12d);
      assertDouble("+.034E+12", +.034E+12d);
      assertDouble("+.034E+12", +.034E+12d);
      assertInteger("133333333333", 133333333333L);
      assertInteger("122", 122);
      assertInteger("0", 0);
      assertInteger("3341", 3341);
      assertHexidecimal("0xffa1", 0xffa1);
      assertHexidecimal("0xFF112", 0xFF112);
      assertHexidecimal("0x1", 0x1);
      assertHexidecimal("-0xaac1", -0xaac1);
   }
   
   private static void assertDouble(String text, double number) {
      NumberMatcher matcher = new NumberMatcher();
      
      assertEquals(matcher.matchNumber(text), NumberType.DECIMAL);
      assertEquals(Double.parseDouble(text), number);
   }
   
   private static void assertInteger(String text, long number) {
      NumberMatcher matcher = new NumberMatcher();
      
      assertEquals(matcher.matchNumber(text), NumberType.DECIMAL);
      assertEquals(Long.parseLong(text), number);
   }
   
   private static void assertHexidecimal(String text, long number) {
      NumberMatcher matcher = new NumberMatcher();
      
      assertEquals(matcher.matchNumber(text), NumberType.HEXIDECIMAL);
      assertEquals(Integer.decode(text), new Integer((int)number));
   }
}
