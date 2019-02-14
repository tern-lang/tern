package org.ternlang.compile;

import junit.framework.TestCase;

import org.ternlang.core.array.PrimitiveByteList;
import org.ternlang.core.array.PrimitiveDoubleList;
import org.ternlang.core.array.PrimitiveFloatList;
import org.ternlang.core.array.PrimitiveIntegerList;
import org.ternlang.core.array.PrimitiveLongList;
import org.ternlang.core.array.PrimitiveShortList;

public class PrimitiveListTest extends TestCase {

   public void testIntegerList() throws Exception {
      int[] array = new int[1024];
      PrimitiveIntegerList list = new PrimitiveIntegerList(array);
      
      assertEquals(array.length, list.size());
      
      for(int i = 0; i < array.length; i++) {
         assertEquals(list.get(i), 0);
      }
      assertTrue(list.contains("0"));
      Integer[] copy = (Integer[])list.toArray();
      
      assertEquals(copy.length, array.length);
      for(int i = 0; i < array.length; i++) {
         assertEquals(copy[i], (Integer)(0));
      }
      for(int i = 0; i < 100; i++){
         list.set(i, i);
      }
      for(int i = 0; i < 100; i++){
         assertEquals(list.indexOf(String.valueOf(i)), i);
         assertEquals(list.indexOf((double)i), i);
         assertEquals(list.indexOf((short)i), i);
         assertEquals(list.indexOf((long)i), i);
         assertEquals(list.indexOf((byte)i), i);
         assertEquals(list.indexOf(i), i);
         assertEquals(list.indexOf((float)i), i);
      }
   }
   
   public void testDoubleList() throws Exception {
      double[] array = new double[1024];
      PrimitiveDoubleList list = new PrimitiveDoubleList(array);
      
      assertEquals(array.length, list.size());
      
      for(int i = 0; i < array.length; i++) {
         assertEquals(list.get(i), (double)0);
      }
      assertTrue(list.contains("0.0"));
      Double[] copy = (Double[])list.toArray();
      
      assertEquals(copy.length, array.length);
      for(int i = 0; i < array.length; i++) {
         assertEquals(copy[i], ((double)0));
      }
      for(int i = 0; i < 100; i++){
         list.set(i, i);
      }
      for(int i = 0; i < 100; i++){
         assertEquals(list.indexOf(String.valueOf((double)i)), i);
         assertEquals(list.indexOf((double)i), i);
         assertEquals(list.indexOf((short)i), i);
         assertEquals(list.indexOf((long)i), i);
         assertEquals(list.indexOf((byte)i), i);
         assertEquals(list.indexOf(i), i);
         assertEquals(list.indexOf((float)i), i);
      }
   }
   
   public void testLongList() throws Exception {
      long[] array = new long[1024];
      PrimitiveLongList list = new PrimitiveLongList(array);
      
      assertEquals(array.length, list.size());
      
      for(int i = 0; i < array.length; i++) {
         assertEquals(list.get(i), (long)0);
      }
      assertTrue(list.contains("0"));
      Long[] copy = (Long[])list.toArray();
      
      assertEquals(copy.length, array.length);
      for(int i = 0; i < array.length; i++) {
         assertEquals(copy[i], (Long)((long)0));
      }
      for(int i = 0; i < 100; i++){
         list.set(i, i);
      }
      for(int i = 0; i < 100; i++){
         assertEquals(list.indexOf(String.valueOf((long)i)), i);
         assertEquals(list.indexOf((double)i), i);
         assertEquals(list.indexOf((short)i), i);
         assertEquals(list.indexOf((long)i), i);
         assertEquals(list.indexOf((byte)i), i);
         assertEquals(list.indexOf(i), i);
         assertEquals(list.indexOf((float)i), i);
      }
   }
   
   public void testShortList() throws Exception {
      short[] array = new short[1024];
      PrimitiveShortList list = new PrimitiveShortList(array);
      
      assertEquals(array.length, list.size());
      
      for(int i = 0; i < array.length; i++) {
         assertEquals(list.get(i), (short)0);
      }
      assertTrue(list.contains("0"));
      Short[] copy = (Short[])list.toArray();
      
      assertEquals(copy.length, array.length);
      for(int i = 0; i < array.length; i++) {
         assertEquals(copy[i], (Short)((short)0));
      }
      for(int i = 0; i < 100; i++){
         list.set(i, i);
      }
      for(int i = 0; i < 100; i++){
         assertEquals(list.indexOf(String.valueOf((short)i)), i);
         assertEquals(list.indexOf((double)i), i);
         assertEquals(list.indexOf((short)i), i);
         assertEquals(list.indexOf((long)i), i);
         assertEquals(list.indexOf((byte)i), i);
         assertEquals(list.indexOf(i), i);
         assertEquals(list.indexOf((float)i), i);
      }
   }
   
   public void testByteList() throws Exception {
      byte[] array = new byte[1024];
      PrimitiveByteList list = new PrimitiveByteList(array);
      
      assertEquals(array.length, list.size());
      
      for(int i = 0; i < array.length; i++) {
         assertEquals(list.get(i), (byte)0);
      }
      assertTrue(list.contains("0"));
      Byte[] copy = (Byte[])list.toArray();
      
      assertEquals(copy.length, array.length);
      for(int i = 0; i < array.length; i++) {
         assertEquals(copy[i], (Byte)((byte)0));
      }
      for(int i = 0; i < 100; i++){
         list.set(i, i);
      }
      for(int i = 0; i < 100; i++){
         assertEquals(list.indexOf(String.valueOf((byte)i)), i);
         assertEquals(list.indexOf((double)i), i);
         assertEquals(list.indexOf((short)i), i);
         assertEquals(list.indexOf((long)i), i);
         assertEquals(list.indexOf((byte)i), i);
         assertEquals(list.indexOf(i), i);
         assertEquals(list.indexOf((float)i), i);
      }
   }
   
   public void testFloatList() throws Exception {
      float[] array = new float[1024];
      PrimitiveFloatList list = new PrimitiveFloatList(array);
      
      assertEquals(array.length, list.size());
      
      for(int i = 0; i < array.length; i++) {
         assertEquals(list.get(i), (float)0);
      }
      assertTrue(list.contains("0.0"));
      Float[] copy = (Float[])list.toArray();
      
      assertEquals(copy.length, array.length);
      for(int i = 0; i < array.length; i++) {
         assertEquals(copy[i], ((float)0));
      }
      for(int i = 0; i < 100; i++){
         list.set(i, i);
      }
      for(int i = 0; i < 100; i++){
         assertEquals(list.indexOf(String.valueOf((float)i)), i);
         assertEquals(list.indexOf((double)i), i);
         assertEquals(list.indexOf((short)i), i);
         assertEquals(list.indexOf((long)i), i);
         assertEquals(list.indexOf((byte)i), i);
         assertEquals(list.indexOf(i), i);
         assertEquals(list.indexOf((float)i), i);
      }
   }
}
