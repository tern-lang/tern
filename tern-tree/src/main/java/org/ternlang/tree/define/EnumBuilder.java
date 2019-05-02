package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.ENUM;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.constraint.EnumName;

public class EnumBuilder {

   private final AtomicReference<Type> reference;
   private final EnumPropertyGenerator generator;
   private final ConstantPropertyBuilder builder;
   private final TypeHierarchy hierarchy;
   private final EnumName name;
   private final List values;
   
   public EnumBuilder(EnumName name, TypeHierarchy hierarchy) {
      this.reference = new AtomicReference<Type>();
      this.generator = new EnumPropertyGenerator();
      this.builder = new ConstantPropertyBuilder();
      this.values = new ArrayList();
      this.hierarchy = hierarchy;
      this.name = name;
   }
   
   public Type create(TypeBody body, Scope outer) throws Exception {
      Module module = outer.getModule();
      String alias = name.getName(outer);
      Type enclosing = outer.getType();
      Type type = module.addType(alias, ENUM.mask);
      
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

      generator.generate(body, scope, type, values);
      hierarchy.define(scope, type); // this may throw exception if missing type
      
      return type;
   }
   
   public Type compile(TypeBody body, Scope outer) throws Exception {
      Type type = reference.get();
      
      hierarchy.compile(outer, type);
      
      return type;
   }
}