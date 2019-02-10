package tern.tree.construct;

import static tern.core.constraint.Constraint.NONE;
import static tern.core.variable.Value.NULL;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.array.ArrayBuilder;
import tern.core.constraint.Constraint;
import tern.core.error.InternalArgumentException;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceEvaluation;
import tern.core.trace.TraceInterceptor;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.tree.Argument;

public class ConstructArray implements Compilation {
   
   private final Evaluation construct;
   
   public ConstructArray(Evaluation type, Argument... arguments) {
      this.construct = new CompileResult(type, arguments);
   }  
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }
   
   private static class CompileResult extends Evaluation {
   
      private final ArrayBuilder builder;
      private final Argument[] arguments;
      private final Evaluation reference;
   
      public CompileResult(Evaluation reference, Argument... arguments) {
         this.builder = new ArrayBuilder();
         this.reference = reference;
         this.arguments = arguments;
      }      

      @Override
      public void define(Scope scope) throws Exception { 
         reference.define(scope);
         
         for(int i = 0; i < arguments.length; i++) {
            arguments[i].define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         reference.compile(scope, null);
         
         for(int i = 0; i < arguments.length; i++) {
            arguments[i].compile(scope, null);
         }  
         return NONE;
      }
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         Value value = reference.evaluate(scope, NULL);
         Type type = value.getValue();
         Class entry = type.getType();
         
         if(arguments.length > 0) {
            int[] dimensions = new int[] {0,0,0};
            
            for(int i = 0; i < arguments.length; i++){
               Argument argument = arguments[i];
               Value index = argument.evaluate(scope, left);
               int number = index.getInteger();
            
               dimensions[i] = number;
            }
            if(arguments.length == 1) {
               int size = dimensions[0];   
               Object array = builder.create(entry, size);
               
               return Value.getTransient(array);
            }
            if(arguments.length == 2) {
               int first = dimensions[0]; 
               int second = dimensions[1];
               Object array = builder.create(entry, first, second);
               
               return Value.getTransient(array);
            }
            if(arguments.length == 3) {
               int first = dimensions[0]; 
               int second = dimensions[1];
               int third = dimensions[2];
               Object array = builder.create(entry, first, second, third);
               
               return Value.getTransient(array);
            }
            throw new InternalArgumentException("Maximum of three dimensions exceeded");
         }
         Object array = builder.create(entry, 0);
         return Value.getTransient(array);
      }
   }
}