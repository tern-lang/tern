package org.ternlang.tree.condition;

import static org.ternlang.core.ModifierType.VARIABLE;
import static org.ternlang.core.result.Result.NORMAL;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceStatement;
import org.ternlang.core.variable.Value;
import org.ternlang.core.resume.Resume;
import org.ternlang.core.resume.Yield;
import org.ternlang.tree.Declaration;
import org.ternlang.tree.SuspendStatement;
import org.ternlang.tree.collection.Iteration;
import org.ternlang.tree.collection.IterationConverter;

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