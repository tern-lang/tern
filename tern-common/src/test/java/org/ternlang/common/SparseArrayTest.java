package org.ternlang.common;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;

public class SparseArrayTest extends TestCase {

   private static final int VALUES = 48000;
   
   public void testSparseArray(){
      for(int i = 1; i < 60; i++) {
         compareWithBlock(i);
      }
   }
   
   public void testSparseArrayGrowth() {
      SparseArray sparseArray = new SparseArray(VALUES);
      for(int i = 0; i <100; i++) {
         int step = i*100;
         sparseArray.set(VALUES +step, String.valueOf(i));
      }
      for(int i = 0; i <100; i++) {
         int step = i*100;
         assertEquals(sparseArray.get(VALUES +step), String.valueOf(i));
      }
   }
   
   private static void compareWithBlock(int block){
      SparseArray sparseArray = new SparseArray(VALUES, block);
      Map hashMap = new HashMap(VALUES);
      Random random = new SecureRandom();
      int[] positions = new int[VALUES];
      
      for(int i = 0; i < VALUES; i++){
         positions[i] = random.nextInt(VALUES);
      }
      for(int i = 0; i < VALUES; i++) {
         sparseArray.set(positions[i], "value-"+positions[i]);
         hashMap.put(positions[i], "value-"+positions[i]); // do we need this?
      }
      for(int i = 0; i < VALUES; i++){
         assertEquals(sparseArray.get(positions[i]), "value-"+positions[i]);
      }
      for(int i = 0; i < VALUES; i++){
         Object required = hashMap.get(i);
         Object actual = sparseArray.get(i);
         
         if(required != null){
            assertEquals(required, actual);
         } else {
            assertNull(actual);
         }
      }
   }
}
