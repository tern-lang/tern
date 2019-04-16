package org.ternlang.tree.closure;

import static org.ternlang.core.ModifierType.CLOSURE;
import static org.ternlang.core.Reserved.METHOD_CLOSURE;

import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.StaticConstraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.function.FunctionType;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.function.StatementInvocationBuilder;

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
      InvocationBuilder builder = new StatementInvocationBuilder(signature, null, statement, constraint, type, modifiers | CLOSURE.mask);
      Invocation invocation = new ClosureInvocation(builder, scope);
      Function function = new InvocationFunction(signature, invocation, type, constraint, METHOD_CLOSURE, modifiers | CLOSURE.mask);
      
      return new ClosureBody(builder, null, function);
   }
}