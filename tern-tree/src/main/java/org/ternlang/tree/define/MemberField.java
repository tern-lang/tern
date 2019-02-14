package org.ternlang.tree.define;

import java.util.List;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.ModifierValidator;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.Accessor;
import org.ternlang.core.function.AccessorProperty;
import org.ternlang.core.function.ScopeAccessor;
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
import org.ternlang.tree.ModifierChecker;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.annotation.AnnotationList;

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