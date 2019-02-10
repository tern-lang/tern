package tern.core.function;

import static tern.core.ModifierType.FUNCTION;
import static tern.core.Reserved.METHOD_CLOSURE;

import java.util.Collections;
import java.util.List;

import tern.common.CompleteProgress;
import tern.common.Progress;
import tern.core.annotation.Annotation;
import tern.core.constraint.Constraint;
import tern.core.module.Module;
import tern.core.property.Property;
import tern.core.scope.Scope;
import tern.core.type.Phase;
import tern.core.type.Type;
import tern.core.type.TypeDescription;
import tern.core.type.StaticScope;

public class FunctionType implements Type {
   
   private final TypeDescription description;
   private final Progress<Phase> progress;
   private final Function function;
   private final Module module;
   private final Scope scope;
   private final String name;
   
   public FunctionType(Signature signature, Module module) {
      this(signature, module, METHOD_CLOSURE); // poor name for hash?
   }
   
   public FunctionType(Signature signature, Module module, String name) {
      this.function = new EmptyFunction(signature, METHOD_CLOSURE);
      this.progress = new CompleteProgress<Phase>();
      this.description = new TypeDescription(this);
      this.scope = new StaticScope(this);
      this.module = module;
      this.name = name;
   }
   
   @Override
   public Progress<Phase> getProgress() {
      return progress;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return Collections.emptyList();
   }
   
   @Override
   public List<Constraint> getGenerics() {
      return Collections.emptyList();
   }

   @Override
   public List<Property> getProperties() {
      return Collections.emptyList();
   }

   @Override
   public List<Function> getFunctions() {
      return Collections.singletonList(function);
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
   public Scope getScope(){
      return scope;
   }

   @Override
   public Class getType() {
      return null;
   }
   
   @Override
   public Type getOuter(){
      return null;
   }

   @Override
   public Type getEntry() {
      return null;
   }

   @Override
   public String getName() {
      return name; 
   }
   
   @Override
   public int getModifiers(){
      return FUNCTION.mask;
   }
   
   @Override
   public int getOrder() {
      return 0;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}