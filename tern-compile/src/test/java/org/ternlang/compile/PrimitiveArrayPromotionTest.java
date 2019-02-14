package org.ternlang.compile;

import junit.framework.TestCase;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.core.Context;
import org.ternlang.core.type.Type;
import org.ternlang.core.convert.ConstraintConverter;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.type.TypeLoader;

public class PrimitiveArrayPromotionTest extends TestCase {

   public void testPrimitiveArrayPromotion() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      TypeLoader loader = context.getLoader();
      Type type1 = loader.loadArrayType("lang", "Byte", 1);
      Type type2 = loader.loadType(byte[].class);

      System.err.println(type1);
      System.err.println(type1.getType());
      System.err.println(type2);
      System.err.println(type2.getType());

      ConstraintMatcher matcher = context.getMatcher();
      ConstraintConverter converter1 = matcher.match(type1);
      ConstraintConverter converter2 = matcher.match(type2);
      
      byte[] primitiveArray = new byte[10];
      Object result1 = converter1.assign(primitiveArray);
      Object result2 = converter2.assign(primitiveArray);
   
      assertEquals(primitiveArray, result1);
      assertEquals(primitiveArray, result2);
   }
   
   public void testPrimitiveMultiDimArrayPromotion() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      TypeLoader loader = context.getLoader();
      Type type1 = loader.loadArrayType("lang", "Byte", 2);
      Type type2 = loader.loadType(byte[][].class);

      System.err.println(type1);
      System.err.println(type1.getType());
      System.err.println(type2);
      System.err.println(type2.getType());

      ConstraintMatcher matcher = context.getMatcher();
      ConstraintConverter converter1 = matcher.match(type1);
      ConstraintConverter converter2 = matcher.match(type2);
      
      byte[][] primitiveArray = new byte[10][10];
      Object result1 = converter1.assign(primitiveArray);
      Object result2 = converter2.assign(primitiveArray);
   
      assertEquals(primitiveArray, result1);
      assertEquals(primitiveArray, result2);
   }
}
