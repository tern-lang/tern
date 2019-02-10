package tern.tree.define;

import static tern.core.result.Result.NORMAL;
import static tern.core.type.Phase.COMPILE;
import static tern.core.type.Phase.CREATE;
import static tern.core.type.Phase.DEFINE;

import java.util.concurrent.atomic.AtomicBoolean;

import tern.common.Progress;
import tern.core.Compilation;
import tern.core.Context;
import tern.core.Execution;
import tern.core.NoExecution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceStatement;
import tern.core.type.Phase;
import tern.core.type.Type;
import tern.core.type.TypePart;
import tern.core.type.TypeState;
import tern.tree.annotation.AnnotationList;
import tern.tree.constraint.EnumName;

public class EnumDefinition implements Compilation {
   
   private final Statement definition;
   
   public EnumDefinition(AnnotationList annotations, EnumName name, TypeHierarchy hierarchy, EnumList list, TypePart... parts) {
      this.definition = new CompileResult(annotations, name, hierarchy, list, parts);
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

      private final DefaultConstructor constructor;
      private final TypeStateCollector collector;
      private final AtomicBoolean compile;
      private final AtomicBoolean define;
      private final AtomicBoolean create;
      private final EnumBuilder builder;
      private final Execution execution;
      private final EnumList list;
      private final TypePart[] parts;
      
      public CompileResult(AnnotationList annotations, EnumName name, TypeHierarchy hierarchy, EnumList list, TypePart[] parts) {
         this.builder = new EnumBuilder(name, hierarchy);
         this.collector = new TypeStateCollector();
         this.constructor = new DefaultConstructor(collector, true);
         this.execution = new NoExecution(NORMAL);
         this.compile = new AtomicBoolean(true);
         this.define = new AtomicBoolean(true);
         this.create = new AtomicBoolean(true);
         this.parts = parts;
         this.list = list;
      }
      
      @Override
      public void create(Scope outer) throws Exception {
         if(!create.compareAndSet(false, true)) {
            Type type = builder.create(collector, outer);
            Progress<Phase> progress = type.getProgress();
         
            progress.done(CREATE);
         }
      }
   
      @Override
      public boolean define(Scope outer) throws Exception {
         if(!define.compareAndSet(false, true)) {
            Type type = builder.define(collector, outer);
            Scope scope = type.getScope();
            TypeState keys = list.define(collector, type, scope);
            Progress<Phase> progress = type.getProgress();
            
            try {
               collector.update(keys); // collect enum constants first
               
               for(TypePart part : parts) {
                  TypeState state = part.define(collector, type, scope);
                  collector.update(state);
               } 
               collector.update(constructor);
               collector.define(scope, type);
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