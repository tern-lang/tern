package org.ternlang.core.convert;

public class ScoreMapper {

   private final Class[] types;
   private final Score[] scores;
   
   public ScoreMapper(Class[] types, Score[] scores) {
      this.types = types;
      this.scores = scores;
   }
   
   public Score map(Class type) {
      for(int i = 0; i < types.length; i++) {
         Class next = types[i];
         
         if(type == next) {
            return scores[i];
         }
      }
      return null;
   }
}