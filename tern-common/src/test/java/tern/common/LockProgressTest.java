package tern.common;

import junit.framework.TestCase;

public class LockProgressTest extends TestCase {
   
   public static enum Stage{
      FIRST,
      SECOND,
      THIRD,
      FOURTH
   }
   
   public void testProgress() {
      final Progress<Stage> progress = new LockProgress<Stage>();
      new Thread(new Runnable() {

         @Override
         public void run() {
            try {
               Thread.sleep(1000);
               progress.done(Stage.FIRST);
               System.err.println("Done "+Stage.FIRST);
               Thread.sleep(1000);
               progress.done(Stage.SECOND);
               System.err.println("Done "+Stage.SECOND);
               Thread.sleep(1000);
               progress.done(Stage.FOURTH);
               System.err.println("Done "+Stage.FOURTH);
            }catch(Exception e){
               e.printStackTrace();
            }
         }
         
      }).start();
      System.err.println("Waiting for "+Stage.THIRD);
      progress.wait(Stage.THIRD);
      System.err.println("Finished");
      
   }
   
   public void testProgressWithTimeout() {
      final Progress<Stage> progress = new LockProgress<Stage>();
      new Thread(new Runnable() {

         @Override
         public void run() {
            try {
               Thread.sleep(1000);
               System.err.println("done="+progress.done(Stage.FIRST));
               System.err.println("Done "+Stage.FIRST);
               Thread.sleep(1000);
               System.err.println("done="+progress.done(Stage.SECOND));
               System.err.println("Done "+Stage.SECOND);
               Thread.sleep(1000);
               System.err.println("done="+progress.done(Stage.THIRD));
               System.err.println("Done "+Stage.THIRD);
            }catch(Exception e){
               e.printStackTrace();
            }
         }
         
      }).start();
      System.err.println("Waiting for "+Stage.FOURTH);
      System.err.println("wait="+progress.wait(Stage.FOURTH, 5000));
      System.err.println("Finished waiting 5 secs for "+Stage.FOURTH);
      System.err.println("wait="+progress.wait(Stage.THIRD, 5000));
      System.err.println("Finished waiting 5 secs for "+Stage.THIRD);
      System.err.println("wait="+progress.wait(Stage.FOURTH, 5000));
      System.err.println("Finished waiting 5 secs for "+Stage.FOURTH);
      
   }

}
