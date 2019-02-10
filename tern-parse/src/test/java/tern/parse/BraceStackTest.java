package tern.parse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import tern.common.store.CacheStore;
import tern.common.store.ClassPathStore;
import tern.common.store.Store;

public class BraceStackTest extends TestCase {
   
   public void testBraceStack() throws Exception {
      List<Token> tokens = createTokens("test_source1.tern");
      BraceStack stack = new BraceStack();
      
      for(Token token : tokens) {
         try{
            System.err.println("["+token.getValue()+"]");
            stack.update(token);
         }catch(Exception e){
            throw new IllegalStateException("Unbalanced braces at line "+token.getLine().getNumber()+": " + token.getLine().getSource(), e);
         }
      }
      assertTrue("Braces should be empty", stack.isEmpty());
   }
   
   private List<Token> createTokens(String resource) {
      Store store = new ClassPathStore();
      CacheStore cache = new CacheStore(store);
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
      String text = cache.getString(resource);
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
   
}
