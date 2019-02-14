package org.ternlang.tree.closure;

import static org.ternlang.core.constraint.Constraint.NONE;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.ModifierChecker;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.compile.ClosureScopeCompiler;
import org.ternlang.tree.constraint.GenericList;

public class Closure implements Compilation {
   
   private final ClosureParameterList parameters;
   private final ClosureStatement statement;
   private final ModifierList modifiers;
   private final GenericList generics;
   
   public Closure(ModifierList modifiers, GenericList generics, ClosureParameterList parameters, Statement statement){
      this(modifiers, generics, parameters, statement, null);
   }  
   
   public Closure(ModifierList modifiers, GenericList generics, ClosureParameterList parameters, Evaluation expression){
      this(modifiers, generics, parameters, null, expression);
   }
   
   public Closure(ModifierList modifiers, GenericList generics, ClosureParameterList parameters, Statement statement, Evaluation expression){
      this.statement = new ClosureStatement(modifiers, statement, expression);
      this.parameters = parameters;
      this.modifiers = modifiers;
      this.generics = generics;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Statement closure = statement.compile(module, path, line);

      return new CompileResult(modifiers, generics, parameters, closure, module);
   }
  
   private static class CompileResult extends Evaluation {
   
      private final AtomicReference<FunctionBody> reference;
      private final ClosureParameterList parameters;
      private final ClosureScopeCompiler compiler;
      private final ClosureBuilder builder;
      private final ModifierChecker checker;
      private final GenericList generics;

      public CompileResult(ModifierList modifiers, GenericList generics, ClosureParameterList parameters, Statement closure, Module module){
         this.reference = new AtomicReference<FunctionBody>();
         this.builder = new ClosureBuilder(closure, module);
         this.compiler = new ClosureScopeCompiler(generics);
         this.checker = new ModifierChecker(modifiers);
         this.parameters = parameters;
         this.generics = generics;
      }
      
      @Override
      public void define(Scope scope) throws Exception {
         int modifiers = checker.getModifiers();
         Scope capture = compiler.define(scope, null);
         List<Constraint> constraints = generics.getGenerics(capture);
         Signature signature = parameters.create(capture, constraints);
         FunctionBody body = builder.create(signature, capture, modifiers); // creating new function each time
         
         body.define(capture);
         reference.set(body);
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         Type type = scope.getType();
         FunctionBody body = reference.get();
         Function function = body.create(scope);   
         Scope combined = compiler.compile(scope, type, function);
         
         body.compile(combined);
        
         return NONE;
      }
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         FunctionBody handle = reference.get();
         Scope capture = compiler.extract(scope, null);
         Function function = handle.create(capture);
         
         return Value.getTransient(function);
      }
   }
}