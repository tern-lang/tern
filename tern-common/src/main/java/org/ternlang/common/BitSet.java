package org.ternlang.common;

public class BitSet {

   private final long[] bits;
   
   public BitSet(int length){
      this.bits = new long[length / 64 + 1];
   }
   
   public void set(int index){
      int bit = index % 64;
      long mask = 1L << bit;
      
      bits[index / 64] |= mask;
      
   }
   
   public boolean get(int index){
      int bit = index % 64;
      long mask = 1L << bit;
      long word = bits[index / 64];
      
      return (word & mask) != 0;
   }
}