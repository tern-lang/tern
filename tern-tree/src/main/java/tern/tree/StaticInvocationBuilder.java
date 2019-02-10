package tern.tree;

import java.util.concurrent.atomic.AtomicBoolean;

import tern.core.Context;
import tern.core.Execution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.convert.ConstraintConverter;
import tern.core.convert.ConstraintMatcher;
import tern.core.error.InternalStateException;
import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.function.Signature;
import tern.core.function.SignatureAligner;
import tern.core.module.Module;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.tree.function.ParameterExtractor;
import tern.tree.function.ScopeCalculator;
import tern.tree.resume.AsyncStatement;

public class StaticInvocationBuilder implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private ScopeCalculator calculator;
   private SignatureAligner aligner;
   private Constraint constraint;
   private Invocation invocation;
   private Statement statement;
   private Execution execution;
   private Execution compile;

   public StaticInvocationBuilder(Signature signature, Execution compile, Statement statement, Constraint constraint, int modifiers) {
      this.extractor = new ParameterExtractor(signature, modifiers);
      this.statement = new AsyncStatement(statement, modifiers);
      this.aligner = new SignatureAligner(signature);
      this.calculator = new ScopeCalculator();
      this.constraint = constraint;
      this.compile = compile;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      extractor.define(scope); // count parameters
      statement.define(scope);
      calculator.define(scope);
      constraint.getType(scope);
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      if(execution != null) {
         throw new InternalStateException("Function has already been compiled");
      }
      Scope compound = calculator.compile(scope);

      if(compound == null) {
         throw new InternalStateException("Function scope could not be calculated");
      }
      execution = statement.compile(compound, constraint);
   }
   
   @Override
   public Invocation create(Scope scope) throws Exception {
      if(invocation == null) {
         invocation = build(scope);
      }
      return invocation;
   }
   
   private Invocation build(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();      

      return new ExecutionInvocation(matcher, compile, execution);
   }

   private class ExecutionInvocation implements Invocation<Object> {
      
      private final ConstraintMatcher matcher;
      private final AtomicBoolean execute;
      private final Execution execution;
      private final Execution compile;
      
      public ExecutionInvocation(ConstraintMatcher matcher, Execution compile, Execution execution) {
         this.execute = new AtomicBoolean(false);
         this.execution = execution;
         this.matcher = matcher;
         this.compile = compile;
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Object[] arguments = aligner.align(list);
         Scope inner = extractor.extract(scope, arguments);
         
         if(execute.compareAndSet(false, true)) {
            compile.execute(inner); // could be a static block
         }
         Scope stack = calculator.calculate(inner);
         Result result = execution.execute(stack);
         Object value = result.getValue();
         
         if(value != null) {
            Type type = constraint.getType(scope);
            ConstraintConverter converter = matcher.match(type);

            return converter.assign(value);
         }
         return value;
      }
   }
}