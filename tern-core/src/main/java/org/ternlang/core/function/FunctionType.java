package org.ternlang.core.function;

import static org.ternlang.core.ModifierType.FUNCTION;
import static org.ternlang.core.Reserved.METHOD_CLOSURE;

import java.util.Collections;
import java.util.List;

import org.ternlang.common.CompleteProgress;
import org.ternlang.common.Progress;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.property.Property;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Phase;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeDescription;
import org.ternlang.core.type.StaticScope;

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