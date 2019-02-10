package tern.tree.script;

import tern.core.Statement;
import tern.core.module.Module;
import tern.core.type.Type;
import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.function.FunctionBody;
import tern.core.function.FunctionType;
import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.function.InvocationFunction;
import tern.core.function.Signature;
import tern.tree.StatementInvocationBuilder;
import tern.tree.function.FunctionBuilder;

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