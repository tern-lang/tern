package org.ternlang.tree.define;

import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Signature;
import org.ternlang.core.type.TypeBody;
import org.ternlang.tree.StaticInvocationBuilder;

public class StaticFunctionBuilder implements MemberFunctionBuilder {
   
   private final Constraint constraint;
   private final Signature signature;
   private final Statement statement;
   private final String name;
   private final int modifiers;

   public StaticFunctionBuilder(Signature signature, Statement statement, Constraint constraint, String name, int modifiers) {
      this.constraint = constraint;
      this.signature = signature;
      this.modifiers = modifiers;
      this.statement = statement;
      this.name = name;
   }
   
   @Override
   public FunctionBody create(TypeBody body, Scope scope, Type type){
      Execution execution = new StaticBody(body, type); 
      InvocationBuilder builder = new StaticInvocationBuilder(signature, execution, statement, constraint, modifiers);
      Invocation invocation = new StaticInvocation(builder, scope);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      return new FunctionBody(builder, null, function);
   }
}