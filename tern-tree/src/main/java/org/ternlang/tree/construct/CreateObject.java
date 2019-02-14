package org.ternlang.tree.construct;

import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;
import static org.ternlang.core.variable.Value.NULL;

import java.util.List;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.CompileConstraint;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.AliasResolver;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.link.ImplicitImportLoader;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.ArgumentList;
import org.ternlang.tree.constraint.ConstructorName;
import org.ternlang.tree.constraint.GenericList;
import org.ternlang.tree.constraint.GenericParameterExtractor;

public class CreateObject extends Evaluation {   
   
   private final GenericParameterExtractor extractor;
   private final ConstructArgumentList arguments;
   private final ImplicitImportLoader loader;
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final AliasResolver alias;
   private final GenericList generics;
   private final Constraint constraint;
   private final int violation; // what modifiers are illegal

   public CreateObject(FunctionResolver resolver, ErrorHandler handler, Constraint constraint, ArgumentList arguments, int violation) {
      this.generics = new ConstructorName(constraint);
      this.extractor = new GenericParameterExtractor(generics);
      this.arguments = new ConstructArgumentList(arguments);
      this.constraint = new CompileConstraint(constraint);
      this.loader = new ImplicitImportLoader();
      this.alias = new AliasResolver();
      this.violation = violation;
      this.resolver = resolver;
      this.handler = handler;
   }      

   @Override
   public void define(Scope scope) throws Exception {
      List<String> names = constraint.getImports(scope);
      int count = names.size();
      
      if(count > 0) {
         loader.loadImports(scope, names);
      }
      arguments.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Type type = constraint.getType(scope);
      Type actual = alias.resolve(type);
      Type[] list = arguments.compile(scope, actual);
      FunctionCall call = resolver.resolveStatic(scope, actual, TYPE_CONSTRUCTOR, list);
      Scope inner = extractor.extract(scope);
      int modifiers = actual.getModifiers();

      if((violation & modifiers) != 0) {
         handler.failCompileConstruction(inner, actual);
      }
      if(call == null) {
         handler.failCompileInvocation(inner, actual, TYPE_CONSTRUCTOR, list);
      }
      return call.check(inner, constraint, list);
   }   
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception { 
      Type type = constraint.getType(scope);
      Type actual = alias.resolve(type);
      Object[] list = arguments.create(scope, actual);
      FunctionCall call = resolver.resolveStatic(scope, actual, TYPE_CONSTRUCTOR, list);
      Scope inner = extractor.extract(scope);
      
      if(call == null){
         handler.failRuntimeInvocation(inner, actual, TYPE_CONSTRUCTOR, list);
      }
      Object result = call.invoke(inner, null, list);
      
      if(result != null) {
         return Value.getTransient(result);
      }
      return NULL;
   }
}