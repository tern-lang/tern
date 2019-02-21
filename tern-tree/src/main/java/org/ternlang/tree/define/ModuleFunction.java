package org.ternlang.tree.define;

import static org.ternlang.core.result.Result.NORMAL;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.Execution;
import org.ternlang.core.ModifierValidator;
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
import org.ternlang.core.type.Type;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.compile.TypeScopeCompiler;
import org.ternlang.tree.constraint.FunctionName;
import org.ternlang.tree.function.ParameterList;

public class ModuleFunction implements ModulePart {
   
   private final DeclarationConstraint constraint;
   private final AnnotationList annotations;
   private final ParameterList parameters;
   private final FunctionName identifier;
   private final ModifierList modifiers;
   private final Statement statement;
   
   public ModuleFunction(AnnotationList annotations, ModifierList modifiers, FunctionName identifier, ParameterList parameters, Statement statement){
      this(annotations, modifiers, identifier, parameters, null, statement);
   }
   
   public ModuleFunction(AnnotationList annotations, ModifierList modifiers, FunctionName identifier, ParameterList parameters, Constraint constraint, Statement statement){
      this.constraint = new DeclarationConstraint(constraint);
      this.annotations = annotations;
      this.identifier = identifier;
      this.parameters = parameters;
      this.statement = statement;
      this.modifiers = modifiers;
   }  
   
   @Override
   public Statement define(ModuleBody body, Module module) throws Exception {
      Scope scope = module.getScope();
      String name = identifier.getName(scope);
      int mask = modifiers.getModifiers();
      
      return new DefineResult(identifier, body, statement, name, mask);
   }
   
   private class DefineResult extends Statement {
   
      private final AtomicReference<FunctionBody> cache;
      private final ModuleFunctionBuilder builder;
      private final ModifierValidator validator;
      private final TypeScopeCompiler compiler;
      private final Execution execution;
      private final String name;
      private final int modifiers;
      
      public DefineResult(FunctionName identifier, ModuleBody body, Statement statement, String name, int modifiers) {
         this.builder = new ModuleFunctionBuilder(body, statement);
         this.compiler = new TypeScopeCompiler(identifier);
         this.cache = new AtomicReference<FunctionBody>();
         this.execution = new NoExecution(NORMAL);
         this.validator = new ModifierValidator();
         this.modifiers = modifiers;
         this.name = name;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         Module module = scope.getModule();
         Type type = module.getType(); // ???
         Scope combined = compiler.define(scope, type);
         List<Function> functions = module.getFunctions();
         List<Constraint> generics = identifier.getGenerics(combined);
         Signature signature = parameters.create(combined, generics);
         Constraint require = constraint.getConstraint(combined, modifiers);
         FunctionBody body = builder.create(signature, module, require, name, modifiers);
         Function function = body.create(combined);

         validator.validate(module, function, modifiers);
         annotations.apply(combined, function);
         functions.add(function);
         body.define(combined); // count stack
         cache.set(body);

         return false;
      }      
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         FunctionBody body = cache.get();
         String name = identifier.getName(scope);

         if(body == null) {
            throw new InternalStateException("Function '" + name + "' was not compiled");
         }
         Module module = scope.getModule();
         Type type = module.getType(); // ???
         Function function = body.create(scope);
         Scope outer = compiler.compile(scope, type, function);

         body.compile(outer);

         return execution;
      }
   }
}