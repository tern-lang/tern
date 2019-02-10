package tern.tree.condition;

import static tern.core.ModifierType.VARIABLE;
import static tern.core.result.Result.NORMAL;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.Execution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.scope.index.Address;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceStatement;
import tern.core.variable.Value;
import tern.core.resume.Resume;
import tern.core.resume.Yield;
import tern.tree.Declaration;
import tern.tree.SuspendStatement;
import tern.tree.collection.Iteration;
import tern.tree.collection.IterationConverter;

public class ForInStatement implements Compilation {
   
   private final Statement loop;
   
   public ForInStatement(Declaration declaration, Evaluation collection, Statement body) {
      this.loop = new CompileResult(declaration, collection, body);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, loop, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final AtomicReference<Address> location;
      private final Declaration declaration;
      private final Evaluation collection;
      private final Statement body;
   
      public CompileResult(Declaration declaration, Evaluation collection, Statement body) {
         this.location = new AtomicReference<Address>();
         this.declaration = declaration;
         this.collection = collection;
         this.body = body;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception { 
         ScopeIndex index = scope.getIndex();
         int size = index.size();
         
         try {   
            Address address = declaration.define(scope, VARIABLE.mask);
            
            collection.define(scope);            
            body.define(scope);
            location.set(address);
         } finally {
            index.reset(size);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception { 
         ScopeIndex index = scope.getIndex();
         int size = index.size();
         
         try {  
            Value variable = declaration.compile(scope, VARIABLE.mask);
            Execution execution = body.compile(scope, returns);
            Address address = location.get();
            
            collection.compile(scope, null);
            
            return new CompileExecution(declaration, collection, execution, address);
         } finally {
            index.reset(size);
         }
      }
   }
   
   private static class CompileExecution extends SuspendStatement<Iterator> {
      
      private final IterationConverter converter;
      private final Declaration declaration;
      private final Evaluation collection;
      private final Execution body;
      private final Address address;
   
      public CompileExecution(Declaration declaration, Evaluation collection, Execution body, Address address) {
         this.converter = new IterationConverter();
         this.declaration = declaration;
         this.collection = collection;      
         this.address = address;
         this.body = body;
      }
   
      @Override
      public Result execute(Scope scope) throws Exception { 
         Value list = collection.evaluate(scope, null);
         Value value = declaration.declare(scope, VARIABLE.mask);
         Object object = list.getValue();
         Iteration iteration = converter.convert(scope, object);
         Iterable iterable = iteration.getIterable(scope);
         Iterator iterator = iterable.iterator();
         
         return resume(scope, iterator);
      }

      @Override
      public Result resume(Scope scope, Iterator iterator) throws Exception {
         ScopeTable table = scope.getTable();
         Value local = table.getValue(address);
         
         while (iterator.hasNext()) {
            Object entry = iterator.next();

            local.setValue(entry);
            
            Result result = body.execute(scope);   
   
            if(result.isYield()) {
               return suspend(scope, result, this, iterator);
            }
            if (result.isReturn()) {
               return result;
            }
            if (result.isBreak()) {
               return NORMAL;
            }
         }    
         return NORMAL;
      }

      @Override
      public Resume suspend(Result result, Resume resume, Iterator value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new ForInResume(child, resume, value);
      }
   }
   
}