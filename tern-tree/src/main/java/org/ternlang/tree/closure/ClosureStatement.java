package org.ternlang.tree.closure;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.ModifierType;
import org.ternlang.core.Statement;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.tree.ExpressionStatement;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.resume.AwaitReturnStatement;

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
