package org.ternlang.tree.define;

import org.ternlang.core.Statement;
import org.ternlang.core.module.Module;
import org.ternlang.core.type.Type;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.function.FunctionType;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Signature;
import org.ternlang.tree.StatementBlock;
import org.ternlang.tree.StatementInvocationBuilder;
import org.ternlang.tree.function.StatementInvocation;

public class ModuleFunctionBuilder {

   private final Statement statement;
   
   public ModuleFunctionBuilder(ModuleBody body, Statement statement) {
      this.statement = new StatementBlock(body, statement);
   }

   public FunctionBody create(Signature signature, Module module, Constraint constraint, String name, int modifiers) {
      Type type = new FunctionType(signature, module, null);
      InvocationBuilder builder = new StatementInvocationBuilder(signature, statement, constraint, type, modifiers);
      Invocation invocation = new StatementInvocation(builder);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      return new FunctionBody(builder, null, function);
   }
}
