package tern.parse;

public class ReferenceGrammar implements Grammar {

   private final ReferenceBuilder resolver; 
   private final int index;
   
   public ReferenceGrammar(GrammarResolver resolver, String name, int index) {
      this.resolver = new ReferenceBuilder(resolver, name, index);
      this.index = index;
   }   
   
   @Override
   public GrammarMatcher create(GrammarCache cache, int length) {
      GrammarMatcher matcher = cache.resolve(index); 
      
      if(matcher == null) {
         matcher = resolver.create(cache, length);
         cache.cache(index, matcher);
      }
      return matcher;
   }  
}