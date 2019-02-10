package tern.tree.closure;

import static tern.core.ModifierType.CLOSURE;
import static tern.core.Reserved.METHOD_CLOSURE;

import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.constraint.StaticConstraint;
import tern.core.function.Function;
import tern.core.function.FunctionBody;
import tern.core.function.FunctionType;
import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.function.InvocationFunction;
import tern.core.function.Signature;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.tree.StatementInvocationBuilder;

public class ClosureBuilder {

   private final Statement statement;
   private final Module module;
   
   public ClosureBuilder(Statement statement, Module module) {
      this.statement = statement;
      this.module = module;
   }
   
   public FunctionBody create(Signature signature, Scope scope, int modifiers) {
      Constraint constraint = new StaticConstraint(null);
      Type type = new FunctionType(signature, module, null);
      InvocationBuilder builder = new StatementInvocationBuilder(signature, statement, constraint, type, modifiers | CLOSURE.mask);
      Invocation invocation = new ClosureInvocation(builder, scope);
      Function function = new InvocationFunction(signature, invocation, type, constraint, METHOD_CLOSURE, modifiers | CLOSURE.mask);
      
      return new ClosureBody(builder, null, function);
   }
}