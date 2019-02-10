package tern.tree.define;

import static tern.core.ModifierType.STATIC;
import static tern.core.Reserved.TYPE_CONSTRUCTOR;

import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.constraint.StaticConstraint;
import tern.core.function.Function;
import tern.core.function.FunctionBody;
import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.function.InvocationFunction;
import tern.core.function.Signature;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.type.TypeState;
import tern.tree.StatementInvocationBuilder;
import tern.tree.function.StatementInvocation;

public class ConstructorBuilder {
   
   private final TypeState delegate;
   private final Statement statement;
   private final Signature signature;

   public ConstructorBuilder(TypeState delegate, Signature signature, Statement statement) {
      this.signature = signature;
      this.statement = statement;
      this.delegate = delegate;
   }
   
   public Function create(TypeBody body, Type type, int modifiers) {
      return create(body, type, modifiers);
   }
   
   public FunctionBody create(TypeBody body, Type type, int modifiers, boolean compile) {
      Constraint none = new StaticConstraint(null);
      InvocationBuilder external = new StatementInvocationBuilder(signature, statement, none, type, modifiers);
      Invocation invocation = new StatementInvocation(external);
      TypeAllocator instance = new ThisAllocator(body, invocation, type);
      InvocationBuilder internal = new TypeInvocationBuilder(delegate, signature, type);
      TypeAllocator base = new TypeDelegateAllocator(instance, internal); 
      Invocation constructor = new NewInvocation(body, base, type, compile);
      Constraint constraint = new StaticConstraint(type);
      Function function = new InvocationFunction(signature, constructor, type, constraint, TYPE_CONSTRUCTOR, modifiers | STATIC.mask, 1);
      
      return new FunctionBody(external, internal, function);
   }
}