package tern.parse;

public class MergeTableGenerator {
   
   private static String MERGABLE = ">=";

   public static void main(String[] list) throws Exception {
      System.out.println("private static final int[] SCORE = {");
      for(int i = 0; i < 0xff; i++) {
         char next = (char)i;
         if(i %16==0 && i > 0){
            System.out.println();
         }
         System.out.print(MERGABLE.indexOf(next) != -1 ? 1 : 0);
         System.out.print(", ");
      }
   }
}
