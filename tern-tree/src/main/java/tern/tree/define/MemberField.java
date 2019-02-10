package tern.tree.define;

import java.util.List;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.ModifierValidator;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.function.Accessor;
import tern.core.function.AccessorProperty;
import tern.core.function.ScopeAccessor;
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
import tern.tree.ModifierChecker;
import tern.tree.ModifierList;
import tern.tree.annotation.AnnotationList;

public class MemberField implements Compilation {
   
   private final TypePart part;
   
   public MemberField(AnnotationList annotations, ModifierList modifiers, MemberFieldDeclaration... declarations) {
      this.part = new CompileResult(annotations, modifiers, declarations);
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
   
      private final MemberFieldDeclaration[] declarations;
      private final TypeStateCollector collector;
      private final MemberFieldAssembler assembler;
      private final AnnotationList annotations;
      private final ModifierValidator validator;
      private final ModifierChecker checker;
   
      public CompileResult(AnnotationList annotations, ModifierList modifiers, MemberFieldDeclaration... declarations) {
         this.assembler = new MemberFieldAssembler(modifiers);
         this.checker = new ModifierChecker(modifiers);
         this.collector = new TypeStateCollector();
         this.validator = new ModifierValidator();
         this.declarations = declarations;
         this.annotations = annotations;
      }
   
      @Override
      public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
         List<Property> properties = type.getProperties();
         int modifiers = checker.getModifiers();
         
         for(MemberFieldDeclaration declaration : declarations) {
            MemberFieldData data = declaration.create(scope, modifiers);
            String name = data.getName();
            String alias = data.getAlias();
            Constraint constraint = data.getConstraint();
            TypeState declare = assembler.assemble(data);
            
            if (checker.isStatic()) {
               Accessor accessor = new StaticAccessor(body, type, name, alias);
               Property property = new AccessorProperty(name, alias, type, constraint, accessor, modifiers);
               
               validator.validate(type, property, modifiers);
               annotations.apply(scope, property);
               properties.add(property);
            } else {
               Accessor accessor = new ScopeAccessor(name, alias);
               Property property = new AccessorProperty(name, alias, type, constraint, accessor, modifiers); // is this the correct type!!??               
               
               validator.validate(type, property, modifiers);
               annotations.apply(scope, property);
               properties.add(property);
            }
            collector.update(declare);
         }
         return collector;
      }
   }
}