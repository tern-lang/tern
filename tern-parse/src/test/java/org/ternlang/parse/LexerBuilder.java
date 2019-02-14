package org.ternlang.parse;

import java.util.List;

import org.ternlang.parse.SyntaxCompiler;
import org.ternlang.parse.SyntaxNode;
import org.ternlang.parse.SyntaxParser;

public class LexerBuilder {

   public static SyntaxParser create(String file){
      SyntaxCompiler builder = new SyntaxCompiler(file);
      return builder.compile();
   }
  
   public static void print(SyntaxParser analyzer, String source, String name) throws Exception {
      SyntaxNode node=analyzer.parse(null, source, name);
      print(node, 0, 0);             
      System.err.println();
      System.err.println();
   }
   
   public static void print(SyntaxNode token, int parent, int depth) throws Exception{   
      String grammar = token.getGrammar();
      List<SyntaxNode> children = token.getNodes();
      
      for (int i = 0; i < depth; i++) {
         System.err.print(" ");
      }      
      System.err.printf("%s --> (", grammar);
      int count = 0;
      
      for(SyntaxNode next : children) { 
         String child = next.getGrammar();
         
         if(count++ != 0) {
            System.err.print(", ");
         }
         System.err.printf(child);
      }
      System.err.print(")");
      System.err.print(" = <");
      System.err.print(token.getLine().getSource().trim());
      System.err.print("> at line ");
      System.err.print(token.getLine().getNumber());
      System.err.println();
      System.err.flush();
      
      for(SyntaxNode next : children) {   
         String child = next.getGrammar();
         
         
         //if(top.contains(child)) {
         if(child.equals(grammar)) {
            print(next, System.identityHashCode(token), depth); // no depth change with no grammar change
         } else {
            print(next, System.identityHashCode(token), depth+2);
         }
         //}
        // }else {
            //System.err.println(next + " "+child.hasNext() + " "+iterator.ready());
          //  printStructure(child, top, depth); // stay at same depth
         //}
      }
   }  

}
