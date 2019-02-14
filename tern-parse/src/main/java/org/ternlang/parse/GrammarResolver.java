package org.ternlang.parse;

import java.util.Map;

public class GrammarResolver {
   
   private final Map<String, Grammar> grammars;
   
   public GrammarResolver(Map<String, Grammar> grammars) {
      this.grammars = grammars;
   }
   
   public Grammar resolve(String name) {
      return grammars.get(name);
   }
}