package tern.tree;

public class Instruction {

   public final String type;
   public final String name;
   
   public Instruction(String type, String name){
      this.type = type;
      this.name = name;
   }

   public String getName(){
      return name;
   }
   
   public String getType(){
      return type;
   }
}