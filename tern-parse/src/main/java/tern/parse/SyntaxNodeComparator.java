package tern.parse;

import java.util.Comparator;

public class SyntaxNodeComparator implements Comparator<SyntaxNode> {
   
   public SyntaxNodeComparator() {
      super();
   }

   @Override
   public int compare(SyntaxNode left, SyntaxNode right) {
      int leftMark = left.getStart();
      int rightMark = right.getStart();
      
      if(leftMark < rightMark) {
         return -1;
      }
      if(leftMark == rightMark) {
         return 0;
      }
      return 1; 
   }

}