package tern.tree.define;

import tern.core.Execution;
import tern.core.Statement;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.function.FunctionBody;
import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.function.InvocationFunction;
import tern.core.function.Signature;
import tern.core.type.TypeBody;
import tern.tree.StaticInvocationBuilder;

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