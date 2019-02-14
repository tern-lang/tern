package org.ternlang.parse;

public class SyntaxTreeBuilder {

   private final SourceProcessor processor;
   private final GrammarIndexer indexer;
   
   public SyntaxTreeBuilder(GrammarIndexer indexer) {
      this.processor = new SourceProcessor(100);
      this.indexer = indexer;
   }

   public SyntaxTree create(String resource, String text, String grammar) {
      char[] array = text.toCharArray();
      
      if(array.length == 0) {
         throw new ParseException("Source text is empty for '" + resource + "'");
      }
      SourceCode source = processor.process(text);
      char[] original = source.getOriginal();
      char[] compress = source.getSource();
      short[] lines = source.getLines();
      short[]types = source.getTypes();
      int count = source.getCount();

      return new SyntaxTree(indexer, resource, grammar, original, compress, lines, types, count);
   }       
}