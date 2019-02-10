package tern.tree.define;

import java.util.List;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.ModifierType;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.constraint.Constraint;
import tern.core.constraint.DeclarationConstraint;
import tern.core.error.ErrorHandler;
import tern.core.error.InternalStateException;
import tern.core.function.Accessor;
import tern.core.function.AccessorProperty;
import tern.core.function.StaticAccessor;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.property.Property;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceTypePart;
import tern.core.type.TypeState;
import tern.core.type.TypeBody;
import tern.core.type.TypePart;
import tern.core.variable.Value;
import tern.tree.ModifierChecker;
import tern.tree.ModifierList;
import tern.tree.annotation.AnnotationList;
import tern.tree.literal.TextLiteral;

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