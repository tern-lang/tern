package org.ternlang.parse.insertion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.ternlang.common.store.CacheStore;
import org.ternlang.common.store.ClassPathStore;
import org.ternlang.parse.CharacterToken;
import org.ternlang.parse.Grammar;
import org.ternlang.parse.GrammarCompiler;
import org.ternlang.parse.GrammarDefinition;
import org.ternlang.parse.GrammarIndexer;
import org.ternlang.parse.GrammarReader;
import org.ternlang.parse.GrammarResolver;
import org.ternlang.parse.SourceCode;
import org.ternlang.parse.SourceProcessor;
import org.ternlang.parse.Token;
import org.ternlang.parse.TokenIndexer;
import org.ternlang.parse.TokenType;

/*
http://www.ecma-international.org/ecma-262/6.0/index.html#sec-automatic-semicolon-insertion
http://www.bradoncode.com/blog/2015/08/26/javascript-semi-colon-insertion/
 */
public class SemiColonInsertionTest extends TestCase {
   
   private static final String SOURCE =
   "class  \n  Point{\n"+
   "   const x;\n"+
   "   const y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   draw(){\n"+
   "      println(x+','+y);\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return '('+x+','+y+')';\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class Line {\n"+
   "   const a;\n"+
   "   const b;\n"+
   "   new(a: Point, b: Point){\n"+
   "      this.a=a;\n"+
   "      this.b=b;\n"+
   "   }\n"+
   "   draw(){\n"+
   "      println(a+'->'+b);\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return a+'->'+b;\n"+
   "   }\n"+
   "}\n";

   public void testInsertion() throws Exception {
      String semiColonFree = SOURCE.replace(";", "");

      new SegmentProcessor(semiColonFree.toCharArray()).process().forEachRemaining(token -> {
         System.out.print("[" + token.source() + "]");
      });

      new SegmentProcessor(semiColonFree.toCharArray()).process().forEachRemaining(token -> {
         System.out.print(token.source());
      });

      String source = new CacheStore(new ClassPathStore()).getString("test_source1.tern");

      new SegmentProcessor(source.toCharArray()).process().forEachRemaining(token -> {
         System.out.print(token.type()+"["+token.source()+"]");
      });
   }
   
//   public void stestSemiColonInsertion() throws Exception {
//      String semiColonFree = SOURCE.replace(";", "");
//
//      //System.err.println(compressText(SOURCE));
//      //System.err.println(compressText(semiColonFree));
//
//      List<Token> tokens = replaceTokens(createTokens(semiColonFree, "/test.tern"));
//
////      for(Token token : tokens) {
////         System.err.println("["+token.getValue()+"]");
////      }
//   }
   
//   public void testSemiColonInsertionFromFile() throws Exception {
//      String source = new CacheStore(new ClassPathStore()).getString("test_source1.tern");
//      String semiColonFree = source.replace(";", "");
//
//      //System.err.println(compressText(SOURCE));
//      //System.err.println(compressText(semiColonFree));
//
//      List<Token> tokens = replaceTokens(createTokens(semiColonFree, "test_source1.tern"));
//
////      for(Token token : tokens) {
////         System.err.println("["+token.getValue()+"]");
////      }
//   }
}
