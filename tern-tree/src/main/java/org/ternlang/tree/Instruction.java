package org.ternlang.tree;

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

   @Override
   public String toString() {
      return String.format("%s = %s", name, type);
   }
}