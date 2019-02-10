package tern.core.type.index;

import static tern.core.ModifierType.ARRAY;

import java.util.Collections;
import java.util.List;

import tern.common.CompleteProgress;
import tern.common.Progress;
import tern.core.annotation.Annotation;
import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.module.Module;
import tern.core.property.Property;
import tern.core.scope.Scope;
import tern.core.type.Phase;
import tern.core.type.Type;
import tern.core.type.TypeDescription;
import tern.core.type.StaticScope;

public class ScopeArrayType implements Type {
   
   private static final Class[] ARRAYS = {
      Object.class,
      Object[].class,
      Object[][].class,
      Object[][][].class,
   };
   
   private final TypeDescription description;
   private final Progress<Phase> progress;
   private final Module module;
   private final Scope scope;
   private final Type entry;
   private final String name;
   private final int order;
   private final int size;
   
   public ScopeArrayType(Module module, String name, Type entry, int size, int order){
      this.progress = new CompleteProgress<Phase>();
      this.description = new TypeDescription(this);
      this.scope = new StaticScope(this);
      this.module = module;
      this.order = order;
      this.entry = entry;
      this.name = name;
      this.size = size;
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
   public List<Function> getFunctions(){
      return Collections.emptyList();
   }
   
   @Override
   public List<Constraint> getTypes(){
      return Collections.emptyList();
   }
   
   @Override
   public Module getModule(){
      return module;
   }
   
   @Override
   public Scope getScope(){
      return scope;
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public Class getType() {
      return ARRAYS[size];
   }
   
   @Override
   public Type getOuter(){
      return null;
   }
   
   @Override
   public Type getEntry(){
      return entry;
   }
   
   @Override
   public int getModifiers() {
      return ARRAY.mask;
   }
   
   @Override
   public int getOrder() {
      return order;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}