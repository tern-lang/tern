package tern.tree.reference;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.variable.Value;

public class GenericReference implements Compilation {
   
   private final GenericArgumentList list;
   private final TypeReference type;
   
   public GenericReference(TypeReference type) {
      this(type, null);
   }
   
   public GenericReference(TypeReference type, GenericArgumentList list) {
      this.type = type;
      this.list = list;
   }

   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new CompileResult(type, list, interceptor, trace);
   }
   
   private static class CompileResult extends ConstraintReference { 

      private final GenericDeclaration declaration;
      
      public CompileResult(TypeReference type, GenericArgumentList list, TraceInterceptor interceptor, Trace trace) {
         this.declaration = new GenericDeclaration(type, list, interceptor, trace);               
      }

      @Override
      protected ConstraintValue create(Scope scope) throws Exception {        
         Value value = declaration.declare(scope);
         Constraint constraint = value.getConstraint();
         Module module = scope.getModule();
      
         return new ConstraintValue(constraint, value, module);
      }      
   }
}