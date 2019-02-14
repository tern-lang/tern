package org.ternlang.tree.construct;

import java.util.LinkedHashMap;
import java.util.Map;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceEvaluation;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

public class ConstructMap implements Compilation {
   
   private final Evaluation construct;
   
   public ConstructMap(StringToken token) {
      this(null, token);
   }
   
   public ConstructMap(MapEntryData list) {
      this(list, null);
   }
   
   public ConstructMap(MapEntryData list, StringToken token) {
      this.construct = new CompileResult(list);
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }
   
   private static class CompileResult extends Evaluation {

      private final MapEntryData entries;
      
      public CompileResult(MapEntryData entries) {
         this.entries = entries;
      }   
      
      @Override
      public void define(Scope scope) throws Exception {
         if(entries != null) {
            entries.define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         if(entries != null) {
            entries.compile(scope, null);
         }
         return Constraint.MAP;
      }
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception { 
         Map map = new LinkedHashMap();
         
         if(entries != null) {
            return entries.evaluate(scope, left);
         }
         return Value.getTransient(map);
      }
   }
}