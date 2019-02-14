package org.ternlang.parse;

import junit.framework.TestCase;

public class ExpressionBuilderTest extends TestCase {
   
   private static final String GRAMMAR_FILE = "grammar.txt";

   public void testParse() throws Exception {
      SyntaxParser tree = LexerBuilder.create(GRAMMAR_FILE);

      
      assertNotNull(tree);      
      analyzeScripts(tree);  
      testParsePerformance(tree);
   }   

   private void analyzeScripts(SyntaxParser analyzer) throws Exception {      
      for(int i = 0; i < 100;i++) {
            String source = ClassPathReader.load("/script"+i+".tern");
            if(source != null) {
               LexerBuilder.print(analyzer, source, "script");
               System.err.println();
               System.err.println();
            }
      }
   }
   
   private void testParsePerformance(SyntaxParser analyzer) throws Exception {
      int iterations = 1000;
      for(int i = 0; i < 100;i++) {
         String source = ClassPathReader.load("/script"+i+".tern");
         if(source != null) {
            long start=System.currentTimeMillis();
            long last=start;
            for(int j=0;j<iterations;j++){
               SyntaxNode list=analyzer.parse("/script"+i+".tern",source, "script");
               assertNotNull(list);
               last=System.currentTimeMillis();
            }
            long finish=System.currentTimeMillis();
            long duration=finish-start;
            long once=finish-last;
            
            System.err.println("Time taken to parse [script"+i+".js] " +iterations+" times was " + duration + " last was "+once);           
         }
      }
   }
}

