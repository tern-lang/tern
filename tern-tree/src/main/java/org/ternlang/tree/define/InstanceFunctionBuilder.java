package org.ternlang.tree.define;

import org.ternlang.core.ModifierType;
import org.ternlang.core.Statement;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Signature;
import org.ternlang.core.type.TypeBody;
import org.ternlang.tree.function.StatementInvocationBuilder;

public class InstanceFunctionBuilder implements MemberFunctionBuilder {
      
   private final Constraint constraint;
   private final Signature signature;
   private final Statement statement;
   private final String name;
   private final int modifiers;

   public InstanceFunctionBuilder(Signature signature, Statement statement, Constraint constraint, String name, int modifiers) {
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.signature = signature;
      this.statement = statement;
      this.name = name;
   }
   
   @Override
   public FunctionBody create(TypeBody body, Scope scope, Type type){
      InvocationBuilder builder = new StatementInvocationBuilder(signature, null, statement, constraint, type, modifiers);
      Invocation invocation = new InstanceInvocation(builder, name, statement == null);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      if(!ModifierType.isAbstract(modifiers)) {
         if(statement == null) {
            throw new InternalStateException("Function '" + function + "' is not abstract");
         }
      }
      return new FunctionBody(builder, null, function);
   }
}