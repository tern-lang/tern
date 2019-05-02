package org.ternlang.tree.define;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.constraint.ClassName;

public class ClassBuilder {   
   
   private final AtomicReference<Type> reference;
   private final ClassPropertyGenerator generator;
   private final ConstantPropertyBuilder builder;
   private final AnnotationList annotations;
   private final TypeHierarchy hierarchy;
   private final ClassName name;
   
   public ClassBuilder(AnnotationList annotations, ClassName name, TypeHierarchy hierarchy) {
      this.reference = new AtomicReference<Type>();
      this.generator = new ClassPropertyGenerator();
      this.builder = new ConstantPropertyBuilder();
      this.annotations = annotations;
      this.hierarchy = hierarchy;
      this.name = name;
   }
   
   public Type create(TypeBody body, Scope outer) throws Exception {
      Module module = outer.getModule();
      String alias = name.getName(outer);
      int modifiers = name.getModifiers(outer);
      Type enclosing = outer.getType();
      Type type = module.addType(alias, modifiers); 

      if(enclosing != null) {
         String name = type.getName();
         String prefix = enclosing.getName();
         String key = name.replace(prefix + '$', ""); // get the class name
         Constraint constraint = Constraint.getConstraint(type);
         Value value = Value.getConstant(type, constraint);
         ScopeState state = outer.getState();
         
         builder.createStaticProperty(body, key, enclosing, constraint);
         state.addValue(key, value);
      } 
      hierarchy.create(outer, type); 
      reference.set(type);
      
      return type;
   }
   
   public Type define(TypeBody body, Scope outer) throws Exception {
      Type type = reference.get();
      Scope scope = type.getScope();
      List<Constraint> generics = name.getGenerics(scope);
      List<Constraint> constraints = type.getGenerics();      
     
      constraints.addAll(generics);
      annotations.apply(scope, type);
      generator.generate(body, scope, type);
      hierarchy.define(scope, type); 
      
      return type;
   }
   
   public Type compile(TypeBody body, Scope outer) throws Exception {
      Type type = reference.get();
      
      hierarchy.compile(outer, type);
      
      return type;
   }
}