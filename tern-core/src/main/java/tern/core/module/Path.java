package tern.core.module;

public class Path {

   private final String path;
   
   public Path(String path){
      this.path = path;
   }
   
   public String getPath(){
      return path;
   }
   
   @Override
   public boolean equals(Object other) {
      if(other instanceof Path) {
         return equals((Path)other);
      }
      return false;
   }
   
   public boolean equals(Path other) {
      if(other != null) {
         return other.path.equals(path);
      }
      return false;
   }
   
   @Override
   public int hashCode() {
      return path.hashCode();
   }
   
   @Override
   public String toString(){
      return path;
   }
}