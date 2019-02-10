package tern.tree.operation;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public class AssignmentList {
   
   private final Evaluation[] list;
   
   public AssignmentList(Evaluation... list) {
      this.list = list;
   }
   
   public int define(Scope scope) throws Exception{
      for(int i = 0; i < list.length; i++){
         list[i].define(scope);
      }
      return list.length;
   }
   
   public Constraint[] compile(Scope scope) throws Exception{
      Constraint[] values = new Constraint[list.length];
      
      for(int i = 0; i < list.length; i++){
         values[i] = list[i].compile(scope, null);
      }
      return values;
   }
   
   public Value[] evaluate(Scope scope) throws Exception{
      Value[] values = new Value[list.length];
      
      for(int i = 0; i < list.length; i++){
         values[i] = list[i].evaluate(scope, null);
      }
      return values;
   }
}