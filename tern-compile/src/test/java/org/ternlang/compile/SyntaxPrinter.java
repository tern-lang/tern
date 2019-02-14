package org.ternlang.compile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.ternlang.parse.SyntaxNode;
import org.ternlang.parse.SyntaxParser;
import org.ternlang.parse.Token;

public class SyntaxPrinter {
  
   public static String print(SyntaxParser analyzer, String source, String name) throws Exception {
      SyntaxNode node=analyzer.parse(null, source, name);
      StringWriter writer = new StringWriter();
      PrintWriter printer = new PrintWriter(writer);
      print(printer, node, 0, 0);
      printer.close();
      return writer.toString();
   }
   
   public static String print(SyntaxNode token) throws Exception {
      StringWriter writer = new StringWriter();
      PrintWriter printer = new PrintWriter(writer);
      print(printer, token, 0, 0);
      printer.close();
      return writer.toString();
   }
   
   private static void print(PrintWriter builder, SyntaxNode token, int parent, int depth) throws Exception{ 
      String grammar = token.getGrammar();
      List<SyntaxNode> children = token.getNodes();
      Token t = token.getToken();
      
      for (int i = 0; i < depth; i++) {
         builder.print(" ");
      }      
      builder.printf("%s --> (", grammar);
      int count = 0;
      
      for(SyntaxNode next : children) { 
         String child = next.getGrammar();
         
         if(count++ != 0) {
            builder.print(", ");
         }
         builder.printf(child);
      }
      builder.print(")");
      builder.print(" = <");
      builder.print(token.getLine().getSource().trim());
      builder.print("> at line ");
      builder.print(token.getLine().getNumber()); 
      builder.println();
      builder.flush();
      
      for(SyntaxNode next : children) {   
         String child = next.getGrammar();
         
         
         //if(top.contains(child)) {
         if(child.equals(grammar)) {
            print(builder, next, System.identityHashCode(token), depth); // no depth change with no grammar change
         } else {
            print(builder, next, System.identityHashCode(token), depth+2);
         }
         //}
        // }else {
            //builder.println(next + " "+child.hasNext() + " "+iterator.ready());
          //  printStructure(child, top, depth); // stay at same depth
         //}
      }
   }  

}
