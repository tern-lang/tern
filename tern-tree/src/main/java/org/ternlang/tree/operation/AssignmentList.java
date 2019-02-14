package org.ternlang.tree.operation;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

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