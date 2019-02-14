package org.ternlang.tree.script;

import static org.ternlang.core.result.Result.NORMAL;
import static org.ternlang.core.scope.extract.ScopePolicy.COMPILE_SCRIPT;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.Execution;
import org.ternlang.core.NoExecution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.DeclarationConstraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.tree.ModifierChecker;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.compile.FunctionScopeCompiler;
import org.ternlang.tree.constraint.FunctionName;
import org.ternlang.tree.function.FunctionBuilder;
import org.ternlang.tree.function.ParameterList;

public class ScriptFunction extends Statement {
   
   private final AtomicReference<FunctionBody> reference;
   private final FunctionScopeCompiler compiler;
   private final ParameterList parameters;
   private final FunctionBuilder builder;
   private final FunctionName identifier;
   private final ModifierChecker checker;
   private final Constraint constraint;
   private final Execution execution;
   
   public ScriptFunction(ModifierList list, FunctionName identifier, ParameterList parameters, Statement body){
      this(list, identifier, parameters, null, body);
   }
   
   public ScriptFunction(ModifierList list, FunctionName identifier, ParameterList parameters, Constraint constraint, Statement body){
      this.reference = new AtomicReference<FunctionBody>();
      this.constraint = new DeclarationConstraint(constraint);
      this.compiler = new FunctionScopeCompiler(identifier, COMPILE_SCRIPT);
      this.builder = new ScriptFunctionBuilder(body);
      this.checker = new ModifierChecker(list);
      this.execution = new NoExecution(NORMAL);
      this.identifier = identifier;
      this.parameters = parameters;
   }  
   
   @Override
   public boolean define(Scope scope) throws Exception {
      int modifiers = checker.getModifiers();
      Module module = scope.getModule();
      String name = identifier.getName(scope);
      Scope combined = compiler.define(scope, null);
      List<Function> functions = module.getFunctions();
      List<Constraint> generics = identifier.getGenerics(combined);
      Signature signature = parameters.create(combined, generics);
      FunctionBody body = builder.create(signature, module, constraint, name, modifiers);
      Function function = body.create(combined);
      
      functions.add(function);
      body.define(combined); // count stack
      reference.set(body);
      
      return false;
   }
   
   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      FunctionBody body = reference.get();
      String name = identifier.getName(scope);      
      
      if(body == null) {
         throw new InternalStateException("Function '" + name + "' was not compiled");
      }
      Function function = body.create(scope);
      Scope combined = compiler.compile(scope, null, function);
      
      body.compile(combined);
      
      return execution;
   }
}