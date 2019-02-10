package tern.parse;

import java.util.Map;

import tern.common.LeastRecentlyUsedMap;

public class SourceProcessor {
   
   private final Map<String, SourceCode> cache;
   private final int limit;
   
   public SourceProcessor(int limit) {
      this.cache = new LeastRecentlyUsedMap<String, SourceCode>();
      this.limit = limit;
   }
   
   public SourceCode process(String source) {
      char[] text = source.toCharArray();
      
      if(text.length == 0) {
         throw new SourceException("Source text is empty");
      }
      SourceCompressor compressor = new SourceCompressor(text);
      
      if(text.length < limit) {
         SourceCode code = cache.get(source);
         
         if(code == null) {
            code = compressor.compress();
            cache.put(source, code); // cache small sources
         }
         return code;
      }
      return compressor.compress();
   }
}