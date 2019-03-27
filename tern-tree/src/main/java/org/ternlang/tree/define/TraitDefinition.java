package org.ternlang.tree.define;

import static org.ternlang.core.result.Result.NORMAL;
import static org.ternlang.core.type.Phase.COMPILE;
import static org.ternlang.core.type.Phase.CREATE;
import static org.ternlang.core.type.Phase.DEFINE;

import java.util.concurrent.atomic.AtomicBoolean;

import org.ternlang.common.Progress;
import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Execution;
import org.ternlang.core.NoExecution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceStatement;
import org.ternlang.core.type.Phase;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.constraint.TraitName;

public class TraitDefinition implements Compilation {
   
   private final Statement definition;
   
   public TraitDefinition(AnnotationList annotations, TraitName name, TypeHierarchy hierarchy, TypePart... parts) {
      this.definition = new CompileResult(annotations, name, hierarchy, parts);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getDefine(module, path, line);
      
      return new TraceStatement(interceptor, handler, definition, trace);
   }
   
   private static class CompileResult extends Statement {  
   
      private final FunctionPropertyGenerator generator;
      private final TypeStateCollector collector;
      private final TypeState constants;
      private final AtomicBoolean compile;
      private final AtomicBoolean define;
      private final AtomicBoolean create;
      private final ClassBuilder builder;
      private final Execution execution;
      private final TypePart[] parts;
      
      public CompileResult(AnnotationList annotations, TraitName name, TypeHierarchy hierarchy, TypePart[] parts) {
         this.builder = new ClassBuilder(annotations, name, hierarchy);
         this.generator = new FunctionPropertyGenerator(); 
         this.collector = new TypeStateCollector();
         this.constants = new StaticState(collector);
         this.execution = new NoExecution(NORMAL);
         this.compile = new AtomicBoolean(true);
         this.define = new AtomicBoolean(true);
         this.create = new AtomicBoolean(true);
         this.parts = parts;
      }
      
      @Override
      public void create(Scope outer) throws Exception {
         if(!create.compareAndSet(false, true)) {
            Type type = builder.create(collector, outer);
            Progress<Phase> progress = type.getProgress();
            Scope scope = type.getScope();
            
            try {
               for(TypePart part : parts) {
                  part.create(collector, type, scope);               
               } 
            } finally {
               progress.done(CREATE);
            }
         }
      }
   
      @Override
      public boolean define(Scope outer) throws Exception {
         if(!define.compareAndSet(false, true)) {
            Type type = builder.define(collector, outer);
            Progress<Phase> progress = type.getProgress();
            Scope scope = type.getScope();
            
            try {
               collector.update(constants); // collect static constants first
               
               for(TypePart part : parts) {
                  TypeState state = part.define(collector, type, scope);
                  collector.update(state);
               } 
               collector.define(scope, type);
               generator.generate(type);
            } finally {
               progress.done(DEFINE); 
            }
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope outer, Constraint returns) throws Exception {
         if(!compile.compareAndSet(false, true)) {
            Type type = builder.compile(collector, outer);
            Progress<Phase> progress = type.getProgress();
            Scope scope = type.getScope();
            Scope local = scope.getStack(); // make it temporary
            
            try {
               collector.compile(local, type);
            } finally {
               progress.done(COMPILE); 
            }
         }
         return execution;
      }
   }
}