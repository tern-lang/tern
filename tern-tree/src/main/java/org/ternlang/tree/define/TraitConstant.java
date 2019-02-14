package org.ternlang.tree.define;

import java.util.List;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.ModifierType;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.DeclarationConstraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Accessor;
import org.ternlang.core.function.AccessorProperty;
import org.ternlang.core.function.StaticAccessor;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.property.Property;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceTypePart;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.ModifierChecker;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.literal.TextLiteral;

public class TraitConstant implements Compilation {
   
   private final TypePart part;
   
   public TraitConstant(AnnotationList annotations, ModifierList list, TextLiteral identifier, Evaluation value) {
      this(annotations, list, identifier, null, value);
   }
   
   public TraitConstant(AnnotationList annotations, ModifierList list, TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.part = new CompileResult(annotations, list, identifier, constraint, value);
   }
   
   @Override
   public TypePart compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getAllocate(module, path, line);
      
      return new TraceTypePart(interceptor, handler, part, trace);
   }
   
   private static class CompileResult extends TypePart {

      private final TraitConstantDeclaration declaration;
      private final AnnotationList annotations;
      private final ModifierChecker checker;
      private final TextLiteral identifier;
      private final Constraint constraint;
      
      public CompileResult(AnnotationList annotations, ModifierList list, TextLiteral identifier, Constraint constraint, Evaluation value) {
         this.declaration = new TraitConstantDeclaration(identifier, constraint, value);
         this.constraint = new DeclarationConstraint(constraint);
         this.checker = new ModifierChecker(list);
         this.annotations = annotations;
         this.identifier = identifier;
      }
   
      @Override
      public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
         TypeState declare = declaration.declare(body, type);
         List<Property> properties = type.getProperties();
         Value value = identifier.evaluate(scope, null);
         String name = value.getString();
         
         if(!checker.isConstant()) {
            throw new InternalStateException("Variable '" + name + "' for '" + type + "' must be constant");
         }
         Accessor accessor = new StaticAccessor(body, type, name, name);
         Property property = new AccessorProperty(name, name, type, constraint, accessor, ModifierType.STATIC.mask | ModifierType.CONSTANT.mask);
         
         annotations.apply(scope, property);
         properties.add(property);
   
         return declare;
      }
   }
}