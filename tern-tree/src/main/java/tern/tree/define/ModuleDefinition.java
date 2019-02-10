package tern.tree.define;

import static tern.core.type.Phase.COMPILE;
import static tern.core.type.Phase.CREATE;
import static tern.core.type.Phase.DEFINE;

import java.util.concurrent.atomic.AtomicReference;

import tern.common.Progress;
import tern.core.Compilation;
import tern.core.Context;
import tern.core.Execution;
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
import tern.tree.annotation.AnnotationList;

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
         Scope local = inner.getStack();
         
         try {
            return body.compile(local, null); // must be module scope
         } finally {
            progress.done(COMPILE);
         }
      }
   }
}