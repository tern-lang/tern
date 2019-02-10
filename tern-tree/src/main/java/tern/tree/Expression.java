package tern.tree;

import tern.core.Compilation;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public class Expression implements Compilation {

   private final Evaluation[] list;
   
   public Expression(Evaluation... list) {
      this.list = list;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      if(list.length > 1) {
         return new CompileResult(list);
      }
      return list[0];
   }

   private static class CompileResult extends Evaluation {
   
      private final Evaluation[] list;
      
      public CompileResult(Evaluation... list) {
         this.list = list;
      }
   
      @Override
      public void define(Scope scope) throws Exception {
         for(int i = 0; i < list.length; i++){
            list[i].define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         return list[list.length -1].compile(scope, left);
      }
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         Value value = list[0].evaluate(scope, left);
         
         for(int i = 1; i < list.length; i++){
            value = list[i].evaluate(scope, left);
         }
         return value;
      }
   }
}