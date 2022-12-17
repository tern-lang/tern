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
import org.ternlang.tree.constraint.EnumName;

public class EnumDefinition implements Compilation {
   
   private final Statement definition;
   private final EnumBody body;
   
   public EnumDefinition(AnnotationList annotations, EnumName name, TypeHierarchy hierarchy, EnumList list, TypePart... parts) {
      this.body = new EnumBody(name, parts);
      this.definition = new CompileResult(annotations, name, hierarchy, list, body);
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
      private final EnumBody body;
      
      public CompileResult(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy, EnumList list, EnumBody body) {
         this.builder = new EnumBuilder(name, hierarchy);
         this.collector = new TypeStateCollector();
         this.constructor = new DefaultConstructor(collector, true);
         this.execution = new NoExecution(NORMAL);
         this.compile = new AtomicBoolean();
         this.define = new AtomicBoolean();
         this.create = new AtomicBoolean();
         this.body = body;
         this.list = list;
      }
      
      @Override
      public void create(Scope outer) throws Exception {
         if(create.compareAndSet(false, true)) {
            Type type = builder.create(collector, outer);
            Progress<Phase> progress = type.getProgress();
         
            progress.done(CREATE);
         }
      }
   
      @Override
      public boolean define(Scope outer) throws Exception {
         if(define.compareAndSet(false, true)) {
            Type type = builder.define(collector, outer);
            Scope scope = type.getScope();
            TypeState keys = list.define(collector, type, scope);
            Progress<Phase> progress = type.getProgress();
            TypePart[] parts = body.getParts(scope);
            
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
         if(compile.compareAndSet(false, true)) {
            Type type = builder.compile(collector, outer);
            Progress<Phase> progress = type.getProgress();
            Scope scope = type.getScope();
            Scope local = scope.getChild(); // make it temporary
            
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