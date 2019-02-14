package org.ternlang.common;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

public class BitSetTest extends TestCase {
   
   private static final int VALUES = 48000;
   
   public void testLargeBitSet(){
      BitSet bitSet = new BitSet(VALUES);
      Set hashSet = new HashSet(VALUES);
      Random random = new SecureRandom();
      int[] positions = new int[VALUES];
      
      for(int i = 0; i < VALUES; i++){
         positions[i] = random.nextInt(VALUES);
      }
      for(int i = 0; i < VALUES; i++) {
         bitSet.set(positions[i]);
         hashSet.add(positions[i]); // do we need this?
      }
      for(int i = 0; i < VALUES; i++){
         assertTrue(bitSet.get(positions[i]));
      }
      for(int i = 0; i < VALUES; i++){
         if(hashSet.contains(i)) {
            System.err.println("SET=["+i+"]");
            assertTrue(bitSet.get(i));
         } else {
            System.err.println("NOT SET=["+i+"]");
            assertFalse(bitSet.get(i));
         }
      }
   }
   
   public void testSimpleBitSet() {
      BitSet set = new BitSet(1);
      set.set(2);
      set.set(7);
      set.set(60);
      set.set(63);
      
      assertTrue(set.get(2));
      assertTrue(set.get(7));
      assertTrue(set.get(60));
      assertTrue(set.get(63));
      
      assertFalse(set.get(1));
      assertFalse(set.get(3));
      assertFalse(set.get(62));
      assertFalse(set.get(8));
   }

}
