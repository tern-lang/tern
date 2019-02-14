package org.ternlang.tree;

import static org.ternlang.core.type.Phase.COMPILE;

import org.ternlang.common.Progress;
import org.ternlang.core.Context;
import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.ConstraintConverter;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.function.Signature;
import org.ternlang.core.function.SignatureAligner;
import org.ternlang.core.module.Module;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.function.ParameterExtractor;
import org.ternlang.tree.function.ScopeCalculator;
import org.ternlang.tree.resume.AsyncStatement;

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