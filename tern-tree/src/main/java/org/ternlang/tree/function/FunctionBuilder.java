package org.ternlang.tree.function;

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

public class FunctionBuilder {

   protected final Statement statement;
   
   public FunctionBuilder(Statement statement) {
      this.statement = statement;
   }

   public FunctionBody create(Signature signature, Module module, Constraint constraint, String name, int modifiers) {
      Type type = new FunctionType(signature, module, null);
      InvocationBuilder builder = new StatementInvocationBuilder(signature, null, statement, constraint, type, modifiers);
      Invocation invocation = new StatementInvocation(builder);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      return new FunctionBody(builder, null, function);
   }
}