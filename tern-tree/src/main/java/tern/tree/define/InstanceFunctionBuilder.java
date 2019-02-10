package tern.tree.define;

import tern.core.ModifierType;
import tern.core.Statement;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.Function;
import tern.core.function.FunctionBody;
import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.function.InvocationFunction;
import tern.core.function.Signature;
import tern.core.type.TypeBody;
import tern.tree.StatementInvocationBuilder;

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
      InvocationBuilder builder = new StatementInvocationBuilder(signature, statement, constraint, type, modifiers);
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