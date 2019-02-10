package tern.tree.closure;

import tern.core.Compilation;
import tern.core.Evaluation;
import tern.core.ModifierType;
import tern.core.Statement;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.tree.ExpressionStatement;
import tern.tree.ModifierList;
import tern.tree.resume.AwaitReturnStatement;

public class ClosureStatement implements Compilation {

   private final ExpressionStatement expression;
   private final AwaitReturnStatement await;
   private final ModifierList modifiers;
   private final Statement statement;

   public ClosureStatement(ModifierList modifiers, Statement statement, Evaluation expression){
      this.expression = new ExpressionStatement(expression);
      this.await = new AwaitReturnStatement(expression);
      this.statement = statement;
      this.modifiers = modifiers;
   }

   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      if(statement == null) {
         int mask = modifiers.getModifiers();

         if(ModifierType.isAsync(mask)) {
            return await.compile(module, path, line);
         }
         return expression.compile(module, path, line);
      }
      return statement;
   }
}
