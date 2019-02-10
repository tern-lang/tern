package tern.parse;

import java.util.LinkedHashMap;
import java.util.Map;

public class SyntaxCompiler {
   
   private final Iterable<GrammarDefinition> definitions;
   private final Map<String, Grammar> grammars;
   private final GrammarCompiler compiler;
   private final GrammarResolver resolver;
   private final GrammarIndexer indexer;
   private final SyntaxParser parser;

   public SyntaxCompiler(String file) {
      this.grammars = new LinkedHashMap<String, Grammar>();      
      this.resolver = new GrammarResolver(grammars);
      this.indexer = new GrammarIndexer();
      this.parser = new SyntaxParser(resolver, indexer);      
      this.compiler = new GrammarCompiler(resolver, indexer);
      this.definitions = new GrammarReader(file);
   } 

   public synchronized SyntaxParser compile() {
      if(grammars.isEmpty()) { 
         for(GrammarDefinition definition : definitions) {
            String name = definition.getName();
            String value = definition.getDefinition();
            Grammar grammar = compiler.process(name, value);
            
            grammars.put(name, grammar);
         }
      }
      return parser;
   }
}