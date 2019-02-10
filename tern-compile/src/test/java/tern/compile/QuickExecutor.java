package tern.compile;

public class QuickExecutor {

   public static void execute(String text) {
      try {
         ClassPathCompilerBuilder.createCompiler().compile(text).execute();
      }catch(Exception e){
         e.printStackTrace();
         throw new IllegalStateException("Could not execute: " + text, e);
      }
   }
}
