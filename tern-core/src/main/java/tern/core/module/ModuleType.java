package tern.core.module;

import static tern.core.ModifierType.MODULE;

import java.util.Collections;
import java.util.List;

import tern.common.CompleteProgress;
import tern.common.Progress;
import tern.core.annotation.Annotation;
import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.property.Property;
import tern.core.scope.Scope;
import tern.core.type.Phase;
import tern.core.type.Type;

public class ModuleType implements Type {
   
   private final Progress<Phase> progress;
   private final Module module;
   
   public ModuleType(Module module) {
      this.progress = new CompleteProgress<Phase>();
      this.module = module;
   }

   @Override
   public Progress<Phase> getProgress() {
      return progress;
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
