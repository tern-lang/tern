package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.STATIC;
import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.StaticConstraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Signature;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypeState;
import org.ternlang.tree.StatementInvocationBuilder;
import org.ternlang.tree.function.StatementInvocation;

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