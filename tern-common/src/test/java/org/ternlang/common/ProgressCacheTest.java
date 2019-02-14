package org.ternlang.common;

import junit.framework.TestCase;

public class ProgressCacheTest extends TestCase {

   private static enum Stage{
      FIRST,
      SECOND,
      THIRD,
      FOURTH
   }
   
   private static class StageEntry{
      
      private final Progress<Stage> progress;
      
      public StageEntry(){
         this.progress = new LockProgress();
      }
      
      public Progress<Stage> getProgress(){
         return progress;
      }
   }
   
   private static class StageCache extends ProgressCache<String, StageEntry, Stage> {
      
      public StageCache(Stage stage) {
         super(stage);
      }
      
      @Override
      protected Progress<Stage> progress(StageEntry entry){
         return entry.getProgress();
      }
   }
   
   public void testProgressCache() throws Exception {
      final StageEntry entry1 = new StageEntry();
      final StageEntry entry2 = new StageEntry();
      final StageCache cache = new StageCache(Stage.THIRD);
      
      cache.cache("entry1", entry1);
      cache.cache("entry2", entry2);
      
      entry2.getProgress().done(Stage.FOURTH);
      
      assertEquals(cache.fetch("entry2"), entry2);
      
      new Thread(new Runnable() {

         @Override
         public void run() {
            try {
               Thread.sleep(1000);
               entry1.getProgress().done(Stage.FIRST);
               System.err.println("Done "+Stage.FIRST);
               Thread.sleep(1000);
               entry1.getProgress().done(Stage.SECOND);
               System.err.println("Done "+Stage.SECOND);
               Thread.sleep(1000);
               entry1.getProgress().done(Stage.THIRD);
               System.err.println("Done "+Stage.THIRD);
            }catch(Exception e){
               e.printStackTrace();
            }
         }
         
      }).start();
      System.err.println("Waiting for "+Stage.THIRD);
      cache.fetch("entry1");
      System.err.println("Fetched entry1");
      cache.fetch("entry1");
      System.err.println("Fetched entry1");
      cache.fetch("entry1");
      System.err.println("Fetched entry1");
   }
}
