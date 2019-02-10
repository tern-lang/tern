package tern.tree.function;

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

public class FunctionBuilder {

   protected final Statement statement;
   
   public FunctionBuilder(Statement statement) {
      this.statement = statement;
   }

   public FunctionBody create(Signature signature, Module module, Constraint constraint, String name, int modifiers) {
      Type type = new FunctionType(signature, module, null);
      InvocationBuilder builder = new StatementInvocationBuilder(signature, statement, constraint, type, modifiers);
      Invocation invocation = new StatementInvocation(builder);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      return new FunctionBody(builder, null, function);
   }
}