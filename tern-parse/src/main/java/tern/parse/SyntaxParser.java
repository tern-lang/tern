package tern.parse;

public class SyntaxParser {   
   
   private final SyntaxTreeBuilder builder;
   private final GrammarResolver resolver;
   
   public SyntaxParser(GrammarResolver resolver, GrammarIndexer indexer) {
      this.builder = new SyntaxTreeBuilder(indexer);
      this.resolver = resolver;
   }   

   public SyntaxNode parse(String resource, String expression, String name) {
      GrammarCache cache = new GrammarCache();
      
      if(expression == null) {
         throw new IllegalArgumentException("Expression for '" + resource + "' is null");
      }
      Grammar grammar = resolver.resolve(name);
      
      if(grammar == null) {
         throw new IllegalArgumentException("Grammar '" + name + "' is not defined");
      }               
      SyntaxTree tree = builder.create(resource, expression, name);
      int length = tree.length();
      
      if(length > 0) {
         GrammarMatcher matcher = grammar.create(cache, length);
         SyntaxChecker checker = tree.check();
         
         if(matcher.check(checker, 0)) { // two phase for performance
            SyntaxBuilder builder = tree.build();
               
            if(matcher.build(builder, 0)) {
               builder.commit();
               return tree.commit();
            }
            throw new IllegalArgumentException("Grammar '" + name + "' failed to build");
         }
         checker.validate(); // syntax errors
      }
      return null;
   } 
}