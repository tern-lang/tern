package org.ternlang.tree.define;

import static org.ternlang.core.type.Phase.COMPILE;
import static org.ternlang.core.type.Phase.CREATE;
import static org.ternlang.core.type.Phase.DEFINE;

import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.common.Progress;
import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Execution;
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
import org.ternlang.tree.annotation.AnnotationList;

public class ModuleDefinition implements Compilation { 
   
   private final Statement definition;

   public ModuleDefinition(AnnotationList annotations, ModuleName module, ModulePart... parts) {
      this.definition = new CompileResult(annotations, module, parts);
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
   
      private final AtomicReference<Module> reference;
      private final ModuleBuilder builder;
      private final Statement body;
      
      public CompileResult(AnnotationList annotations, ModuleName module, ModulePart... parts) {
         this.builder = new ModuleBuilder(annotations, module);
         this.reference = new AtomicReference<Module>();
         this.body = new ModuleBody(parts);
      }
      
      @Override
      public void create(Scope scope) throws Exception {
         Module module = builder.create(scope);
         Progress<Phase> progress = module.getProgress();
         Scope inner = module.getScope();
         
         try {
            reference.set(module);
            body.create(inner);
         } finally {
            progress.done(CREATE);
         }
      }
   
      @Override
      public boolean define(Scope scope) throws Exception {
         Module module = reference.get();
         Progress<Phase> progress = module.getProgress();
         Scope inner = module.getScope();
         
         try {
            body.define(inner); // must be module scope
         } finally {
            progress.done(DEFINE);
         }         
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Module module = reference.get();
         Progress<Phase> progress = module.getProgress();
         Scope inner = module.getScope();
         Scope local = inner.getChild();
         
         try {
            return body.compile(local, null); // must be module scope
         } finally {
            progress.done(COMPILE);
         }
      }
   }
}