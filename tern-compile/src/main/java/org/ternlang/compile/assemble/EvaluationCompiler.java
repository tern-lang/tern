package org.ternlang.compile.assemble;

import static org.ternlang.core.Reserved.GRAMMAR_FILE;
import static org.ternlang.core.Reserved.GRAMMAR_EXPRESSION;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import org.ternlang.common.Cache;
import org.ternlang.common.LeastRecentlyUsedCache;
import org.ternlang.compile.verify.Verifier;
import org.ternlang.core.Evaluation;
import org.ternlang.core.Reserved;
import org.ternlang.core.module.FilePathConverter;
import org.ternlang.core.module.Path;
import org.ternlang.core.module.PathConverter;
import org.ternlang.core.scope.Scope;
import org.ternlang.parse.SyntaxCompiler;
import org.ternlang.parse.SyntaxNode;
import org.ternlang.parse.SyntaxParser;

public class EvaluationCompiler {

   private final Cache<String, Evaluation> cache;
   private final PathConverter converter;
   private final SyntaxCompiler compiler;
   private final Assembler assembler;
   private final Verifier verifier;
   private final Executor executor;
   private final int limit;
   
   public EvaluationCompiler(Assembler assembler, Verifier verifier, Executor executor){
      this(assembler, verifier, executor, 200);
   }
   
   public EvaluationCompiler(Assembler assembler, Verifier verifier, Executor executor, int limit) {
      this.cache = new LeastRecentlyUsedCache<String, Evaluation>();
      this.compiler = new SyntaxCompiler(GRAMMAR_FILE);
      this.converter = new FilePathConverter();
      this.assembler = assembler;
      this.executor = executor;
      this.verifier = verifier;
      this.limit = limit;
   }
   
   public Evaluation compile(Scope scope, String source, String module) throws Exception{
      Evaluation evaluation = cache.fetch(source);

      if(evaluation == null) {
         Executable executable = new Executable(scope, source, module);
         FutureTask<Evaluation> task = new FutureTask<Evaluation>(executable);
         
         if(executor != null) {
            executor.execute(task); // reduce android stack size using another thread
         } else {
            task.run();
         }
         return task.get();
      }
      return evaluation;
   }
   
   private class Executable implements Callable<Evaluation> {
      
      private final String source;
      private final String module;
      private final Scope scope;
      
      public Executable(Scope scope, String source, String module) {
         this.source = source;
         this.module = module;
         this.scope = scope;
      }
      
      @Override
      public Evaluation call() throws Exception {
         SyntaxParser parser = compiler.compile();
         SyntaxNode node = parser.parse(module, source, GRAMMAR_EXPRESSION);
         Path path = converter.createPath(module);
         Evaluation evaluation = assembler.assemble(node, path);
         int length = source.length();
         
         evaluation.define(scope);
         evaluation.compile(scope, null);
         verifier.verify();
         
         if(length < limit) {
            cache.cache(source, evaluation);
         }                 
         return evaluation;
      }
   }
}