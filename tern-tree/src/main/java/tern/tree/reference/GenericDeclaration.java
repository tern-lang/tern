package tern.tree.reference;

import static tern.core.constraint.Constraint.OBJECT;
import static tern.core.variable.Value.NULL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.constraint.TypeParameterConstraint;
import tern.core.error.InternalStateException;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.tree.constraint.TypeConstraint;

public class GenericDeclaration { 

   private final ConstraintCompilation compilation;
   private final GenericArgumentList generics;
   private final TypeReference reference; 
   private final Set<String> imports;
   
   public GenericDeclaration(TypeReference reference, GenericArgumentList generics, TraceInterceptor interceptor, Trace trace) {
      this.compilation = new ConstraintCompilation(reference, generics, interceptor, trace);
      this.imports = new HashSet<String>();
      this.reference = reference;
      this.generics = generics;
   }
   
   public Value declare(Scope scope) throws Exception {
      Module module = scope.getModule();
      Scope outer = module.getScope();
      String name = reference.qualify(scope, null);  
      
      if(generics != null) {
         List<String> other = generics.getImports(scope);
         
         if(other != null) {
            imports.addAll(other);
         }
      }         
      if(name != null) {
         imports.add(name);
      }
      return new ConstraintConstant(compilation, outer, imports);
   }
   
   private static class ConstraintCompilation extends Evaluation {

      private final TraceInterceptor interceptor;
      private final GenericArgumentList generics;
      private final TypeReference reference;
      private final Trace trace;

      public ConstraintCompilation(TypeReference reference, GenericArgumentList generics, TraceInterceptor interceptor, Trace trace) {
         this.interceptor = interceptor;
         this.reference = reference;
         this.generics = generics;
         this.trace = trace;
      }

      @Override
      public Constraint compile(Scope scope, Constraint left) { 
         try {
            Value value = reference.evaluate(scope, NULL);
            Object object = value.getValue();

            if(Type.class.isInstance(object)) {
               Constraint constraint = value.getConstraint();
               String name = constraint.getName(scope);
               Type type = constraint.getType(scope);

               if(generics != null) {
                  List<Constraint> arguments = generics.getGenerics(scope);    
                  List<Constraint> declare = type.getGenerics();
                  int require = declare.size();
                  int actual = arguments.size();

                  if(actual > 0) {
                     if(require != actual) {
                        throw new IllegalStateException("Generic parameter count for '" + type + "' is invalid");
                     }
                     return new TypeParameterConstraint(type, arguments, name);
                  }
               }
               return new TypeParameterConstraint(type, name);
            }
            return value.getConstraint();
         }catch(Exception cause) {
            interceptor.traceCompileError(scope, trace, cause);
         }
         return OBJECT;
      }   
   }  
   
   private static class ConstraintConstant extends Value {
      
      private final Constraint constraint;
      private final Scope scope;
      
      public ConstraintConstant(Evaluation evaluation, Scope scope, Set<String> imports) {
         this.constraint = new ConstraintEvaluation(evaluation, imports);
         this.scope = scope;        
      }
      
      @Override
      public boolean isConstant() {
         return true;
      }
      
      @Override
      public Constraint getConstraint() {
         return constraint;
      }
      
      @Override
      public <T> T getValue() {
         return (T)constraint.getType(scope);            
      }
      
      @Override
      public void setValue(Object value){
         throw new InternalStateException("Illegal modification of literal '" + value + "'");
      } 
      
      @Override
      public String toString() {
         return String.valueOf(constraint);
      }
   }   
   
   private static class ConstraintEvaluation extends Constraint {
         
      private final Constraint constraint;
      private final List<String> imports; 
      
      public ConstraintEvaluation(Evaluation evaluation, Set<String> imports) {
         this.constraint = new TypeConstraint(evaluation);
         this.imports = new ArrayList<String>(imports);      
      }     
      
      @Override
      public boolean isConstant() {
         return true;
      }
      
      @Override
      public List<String> getImports(Scope scope) {
         return imports;
      }
      
      @Override
      public String getName(Scope scope) {
         return constraint.getName(scope);
      }

      @Override
      public List<Constraint> getGenerics(Scope scope) {
         return constraint.getGenerics(scope);
      }
      
      @Override
      public Type getType(Scope scope) {
         return constraint.getType(scope);
      }  
      
      @Override
      public String toString() {
         return String.valueOf(constraint);
      }
   }
}