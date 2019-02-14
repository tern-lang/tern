package org.ternlang.compile.assemble;

import static org.ternlang.core.Reserved.DEFAULT_PACKAGE;

import java.util.concurrent.Executor;

import org.ternlang.compile.verify.Verifier;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.ExpressionEvaluator;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.extract.EvaluationExtractor;
import org.ternlang.core.scope.extract.ScopePolicyExtractor;
import org.ternlang.core.variable.Value;

public class OperationEvaluator implements ExpressionEvaluator {
   
   private final EvaluationExtractor extractor;
   private final EvaluationCompiler compiler;
   private final ModelScopeBuilder builder;
   private final Assembler assembler;
   
   public OperationEvaluator(Context context, Verifier verifier, Executor executor){
      this(context, verifier, executor, 200);
   }
   
   public OperationEvaluator(Context context, Verifier verifier, Executor executor, int limit) {
      this.assembler = new OperationAssembler(context, executor);
      this.compiler = new EvaluationCompiler(assembler, verifier, executor, limit);
      this.builder = new ModelScopeBuilder(context);
      this.extractor = new EvaluationExtractor();
   }
   
   @Override
   public <T> T evaluate(Model model, String source) throws Exception{
      return evaluate(model, source, DEFAULT_PACKAGE);
   }
   
   @Override
   public <T> T evaluate(Model model, String source, String module) throws Exception{
      Scope scope = builder.create(model, module);
      return evaluate(scope, source, module);
   }
   
   @Override
   public <T> T evaluate(Scope scope, String source) throws Exception{
      return evaluate(scope, source, DEFAULT_PACKAGE);
   }
   
   @Override
   public <T> T evaluate(Scope scope, String source, String module) throws Exception{
      Module parent = scope.getModule();
      Context context = parent.getContext();      
      ErrorHandler handler = context.getHandler();
            
      try {
         Scope capture = extractor.extract(scope);
         Evaluation evaluation = compiler.compile(capture, source, module);
         Value reference = evaluation.evaluate(capture,null);
         
         return (T)reference.getValue();
      } catch(Throwable cause) {
         return (T)handler.failExternalError(scope, cause);
      }
   }
}