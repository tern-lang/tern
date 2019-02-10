package tern.compile.assemble;

import static tern.core.Reserved.GRAMMAR_FILE;
import static tern.core.Reserved.GRAMMAR_EXPRESSION;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import tern.common.Cache;
import tern.common.LeastRecentlyUsedCache;
import tern.compile.verify.Verifier;
import tern.core.Evaluation;
import tern.core.Reserved;
import tern.core.module.FilePathConverter;
import tern.core.module.Path;
import tern.core.module.PathConverter;
import tern.core.scope.Scope;
import tern.parse.SyntaxCompiler;
import tern.parse.SyntaxNode;
import tern.parse.SyntaxParser;

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