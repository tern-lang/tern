package tern.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import tern.common.store.CacheStore;
import tern.common.store.ClassPathStore;

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
   
   public void stestSemiColonInsertion() throws Exception {
      String semiColonFree = SOURCE.replace(";", "");
      
      //System.err.println(compressText(SOURCE));
      //System.err.println(compressText(semiColonFree));
      
      List<Token> tokens = replaceTokens(createTokens(semiColonFree, "/test.snap"));
      
//      for(Token token : tokens) {
//         System.err.println("["+token.getValue()+"]");
//      }
   }
   
   public void testSemiColonInsertionFromFile() throws Exception {
      String source = new CacheStore(new ClassPathStore()).getString("test_source1.snap");
      String semiColonFree = source.replace(";", "");
      
      //System.err.println(compressText(SOURCE));
      //System.err.println(compressText(semiColonFree));
      
      List<Token> tokens = replaceTokens(createTokens(semiColonFree, "test_source1.snap"));
      
//      for(Token token : tokens) {
//         System.err.println("["+token.getValue()+"]");
//      }
   }
   private String compressText(String text) {
      SourceProcessor sourceProcessor = new SourceProcessor(100);
      SourceCode source = sourceProcessor.process(text);
      return new String(source.getSource());
   }
   
   private List<Token> createTokens(String text, String resource) {
      List<Token> tokens = new ArrayList<Token>();
      GrammarIndexer grammarIndexer = new GrammarIndexer();
      Map<String, Grammar> grammars = new LinkedHashMap<String, Grammar>();      
      GrammarResolver grammarResolver = new GrammarResolver(grammars);
      GrammarCompiler grammarCompiler = new GrammarCompiler(grammarResolver, grammarIndexer);  
      SourceProcessor sourceProcessor = new SourceProcessor(100);
      GrammarReader reader = new GrammarReader("grammar.txt");
      
      for(GrammarDefinition definition : reader){
         String name = definition.getName();
         String value = definition.getDefinition();
         Grammar grammar = grammarCompiler.process(name, value);
         
         grammars.put(name, grammar);
      }
      SourceCode source = sourceProcessor.process(text);
      char[] original = source.getOriginal();
      char[] compress = source.getSource();
      short[] lines = source.getLines();
      short[]types = source.getTypes();
      int count = source.getCount();

      TokenIndexer tokenIndexer = new TokenIndexer(grammarIndexer, resource, original, compress, lines, types, count);
      tokenIndexer.index(tokens);
      return tokens;
   }
   
   public List<Token> replaceTokens(List<Token> tokens) {
      List<Token> done = new ArrayList<Token>();
      BraceStack stack = new BraceStack();
      Token prev = null;
      if(tokens.isEmpty()){
         throw new IllegalStateException("No tokens");
      }
      for(int i = 0; i < tokens.size(); i++) {
         try {
            List<Token> converted = convertTo(tokens, i);
            
            for(Token token: converted) {
               stack.update(token);
               if(isText(token)) {
                  System.err.print("'"+token.getValue()+"'");
               } else {
                  System.err.print(token.getValue());
                  if(token.getValue().equals(';')) {
                     System.err.println();
                  }
               }
            }
            done.addAll(converted);
         }catch(Exception e){
            e.printStackTrace(); 
         }
      }
      return done;
   }
   
   public List<Token> convertTo(List<Token> tokens, int i) {
      Token token = tokens.get(i);
      
      if(token.getValue().equals('\n')) {
         if(!isLiteralBefore(tokens, i) && isLiteralAfter(tokens, i)) {
            return Arrays.<Token>asList(new CharacterToken(';', token.getLine(), token.getType()));
         }
         if(isPreviousOneOf(tokens, i, "return", "continue", "break")) {
            return Arrays.<Token>asList(new CharacterToken(';', token.getLine(), token.getType()), token);
         }
         if((isAlphaBefore(tokens, i) && isAlphaAfter(tokens, i)) && !isLiteralBefore(tokens, i) && !isLiteralAfter(tokens, i)) {
            return Arrays.<Token>asList(new CharacterToken(';', token.getLine(), token.getType()), token);
         }
      }
      if(token.getValue().equals("}")) {
         if(!isPreviousOneOf(tokens, i, "}")) {
            return Arrays.<Token>asList(new CharacterToken(';', token.getLine(), token.getType()), token);
         }
      }
      if(token.getValue().equals(")") || token.getValue().equals("]")) {
         if(isAlphaAfter(tokens, i)) {
            return Arrays.<Token>asList(token, new CharacterToken(';', token.getLine(), token.getType()));
         }
      }
      return Arrays.asList(token);
   }
   
   public boolean isText(Token token) {
      return ((token.getType() & TokenType.TEXT.mask) == TokenType.TEXT.mask) || 
              ((token.getType() & TokenType.TEMPLATE.mask) == TokenType.TEMPLATE.mask);
   }
   
   public boolean isPreviousOneOf(List<Token> tokens, int index, Object... values) {
      if(index > 0){
         Token after = tokens.get(index-1);
         for(Object value : values) {
            if(after.getValue().equals(value)) {
               return true;
            }
         }
      }
      return false;
   }
   
   public boolean isLiteral(Token token){
      return (token.getType() & TokenType.LITERAL.mask) == TokenType.LITERAL.mask;
   }
   
   public boolean isAlphaBefore(List<Token> tokens, int index) {
      if(index > 0){
         Object value = tokens.get(index-1).getValue();
         String text = String.valueOf(value);
         
         return isIdentitfier(text.charAt(text.length()-1));
      }
      return false;
   }
   
   public boolean isAlphaAfter(List<Token> tokens, int index) {
      if(tokens.size() > index+1){
         Object value = tokens.get(index+1).getValue();
         String text = String.valueOf(value);
         
         return isIdentitfier(text.charAt(0));
      }
      return false;
   }
   
   private boolean isIdentitfier(char value) {
      if(value >= 'a' && value <= 'z') {
         return true;
      }
      if(value >= 'A' && value <= 'Z') {
         return true;
      }
      if(value >= '0' && value <= '9') {
         return true;
      }
      return false;
   }
   
   public boolean isLiteralBefore(List<Token> tokens, int index) {
      if(index > 0){
         Token token = tokens.get(index-1);
         if((token.getType() & TokenType.LITERAL.mask) == TokenType.LITERAL.mask){
            if(token.getValue().equals("true") || token.getValue().equals("false") || token.getValue().equals("null")){
               return false;
            }
            return true;
         }
      }
      return false;
   }
   
   public boolean isLiteralAfter(List<Token> tokens, int index) {
      if(tokens.size() > index+1){
         Token token = tokens.get(index+1);
         if((token.getType() & TokenType.LITERAL.mask) == TokenType.LITERAL.mask){
            if(token.getValue().equals("true") || token.getValue().equals("false") || token.getValue().equals("null")){
               return false;
            }
            return true;
         }
      }
      return false;
   }
}
