package org.ternlang.tree.script;

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
import org.ternlang.tree.StatementInvocationBuilder;
import org.ternlang.tree.function.FunctionBuilder;

public class ScriptFunctionBuilder extends FunctionBuilder {
   
   public ScriptFunctionBuilder(Statement statement) {
      super(statement);
   }

   @Override
   public FunctionBody create(Signature signature, Module module, Constraint constraint, String name, int modifiers) {
      Type type = new FunctionType(signature, module, null);
      InvocationBuilder builder = new StatementInvocationBuilder(signature, statement, constraint, type, modifiers);
      Invocation invocation = new ScriptInvocation(builder, signature);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      return new FunctionBody(builder, null, function);
   }
}