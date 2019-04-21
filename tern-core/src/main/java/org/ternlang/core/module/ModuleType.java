package org.ternlang.core.module;

import static org.ternlang.core.ModifierType.MODULE;

import java.util.Collections;
import java.util.List;

import org.ternlang.common.CompleteProgress;
import org.ternlang.common.Progress;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.property.Property;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Phase;
import org.ternlang.core.type.Type;

public class ModuleType implements Type {
   
   private final Module module;
   
   public ModuleType(Module module) {
      this.module = module;
   }

   @Override
   public Progress<Phase> getProgress() {
      return module.getProgress();
   }

   @Override
   public List<Annotation> getAnnotations() {
      return module.getAnnotations();
   }
   
   @Override
   public List<Constraint> getGenerics() {
      return Collections.emptyList();
   }

   @Override
   public List<Property> getProperties() {
      return module.getProperties();
   }

   @Override
   public List<Function> getFunctions() {
      return module.getFunctions();
   }

   @Override
   public List<Constraint> getTypes() {
      return Collections.emptyList();
   }

   @Override
   public Module getModule() {
      return module;
   }

   @Override
   public Scope getScope() {
      return module.getScope();
   }

   @Override
   public Class getType() {
      return Module.class;
   }

   @Override
   public Type getOuter() {
      return null;
   }

   @Override
   public Type getEntry() {
      return null;
   }

   @Override
   public String getName() {
      return module.getName();
   }
   
   @Override
   public int getModifiers(){
      return MODULE.mask;
   }

   @Override
   public int getOrder() {
      return 0;
   }
   
   @Override
   public String toString() {
      return module.toString();
   }

}
