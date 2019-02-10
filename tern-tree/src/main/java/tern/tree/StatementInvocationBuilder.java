package tern.tree;

import static tern.core.type.Phase.COMPILE;

import tern.common.Progress;
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

public class StatementInvocationBuilder implements InvocationBuilder {

   private ParameterExtractor extractor;
   private ScopeCalculator calculator;
   private SignatureAligner aligner;
   private Constraint constraint;
   private Invocation invocation;
   private Statement statement;
   private Execution execution;
   private Type type;

   public StatementInvocationBuilder(Signature signature, Statement statement, Constraint constraint, Type type, int modifiers) {
      this.extractor = new ParameterExtractor(signature, modifiers);
      this.statement = new AsyncStatement(statement, modifiers);
      this.aligner = new SignatureAligner(signature);
      this.calculator = new ScopeCalculator();
      this.constraint = constraint;
      this.type = type;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      extractor.define(scope); // count parameters
      statement.define(scope); // start counting from here
      calculator.define(scope);
      constraint.getType(scope);
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      if(execution != null) {
         throw new InternalStateException("Function has already been compiled");
      }
      if(execution == null) {
         Scope compound = calculator.compile(scope);

         if(compound == null) {
            throw new InternalStateException("Function scope could not be calculated");
         }
         execution = statement.compile(compound, constraint);
      }
   }
   
   @Override
   public Invocation create(Scope scope) throws Exception {
      if(invocation == null) {
         Progress progress = type.getProgress();

         if (progress.wait(COMPILE)) {
            if (execution == null) {
               throw new InternalStateException("Function has not been compiled");
            }
            invocation = build(scope);
         }
      }
      return invocation;
   }
   
   private Invocation build(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();      

      return new ExecutionInvocation(matcher, execution);
   }

   private class ExecutionInvocation implements Invocation<Object> {
      
      private final ConstraintMatcher matcher;
      private final Execution execution;
      
      public ExecutionInvocation(ConstraintMatcher matcher, Execution execution) {
         this.execution = execution;
         this.matcher = matcher;
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Object[] arguments = aligner.align(list);
         Scope inner = extractor.extract(scope, arguments);
         Scope stack = calculator.calculate(inner);
         Type type = constraint.getType(scope);
         ConstraintConverter converter = matcher.match(type);
         Result result = execution.execute(stack);
         Object value = result.getValue();
         
         if(value != null) {
            value = converter.assign(value);
         }
         return value;
      }
   }
}