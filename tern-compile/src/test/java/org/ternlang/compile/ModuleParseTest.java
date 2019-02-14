package org.ternlang.compile;

import static org.ternlang.core.Reserved.GRAMMAR_FILE;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.ternlang.parse.SyntaxCompiler;
import org.ternlang.parse.SyntaxNode;
import org.ternlang.parse.SyntaxParser;

public class ModuleParseTest extends TestCase {

   public void testParse() throws Exception {
      SyntaxCompiler bb = new SyntaxCompiler(GRAMMAR_FILE);
      SyntaxParser tree =  bb.compile();

      assertNotNull(tree);

      analyze(tree, "module x{}", "script");      

          
   }

   private void analyze(SyntaxParser analyzer, String source, String grammar) throws Exception {
      analyze(analyzer, source, grammar, true);
   }

   private void analyze(SyntaxParser analyzer, String source, String grammar, boolean success) throws Exception {
      Set<String> keep=new HashSet<String>();
      
      keep.add("expression");
      keep.add("reference");
      keep.add("method");
      keep.add("variable");
      keep.add("literal");
      keep.add("construct");       
      keep.add("calculation-expression");
      keep.add("calculation-operator");
      keep.add("arithmetic-expression");
      
      SyntaxNode list = analyzer.parse(null,source, grammar);

      if (list != null) {
         SyntaxPrinter.print(analyzer, source, grammar);
      }else {
         if(success) {
            assertTrue(false);
         } 
      }
   }

}

