package org.ternlang.core.type.index;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.common.LockProgress;
import org.ternlang.common.Progress;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.module.Module;
import org.ternlang.core.property.Property;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Phase;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeDescription;
import org.ternlang.core.type.StaticScope;

public class ScopeType implements Type {
   
   private final TypeDescription description;
   private final List<Annotation> annotations;
   private final List<Constraint> constraints;
   private final List<Property> properties;
   private final List<Function> functions;
   private final List<Constraint> types;
   private final Progress<Phase> progress;
   private final Module module;
   private final Scope scope;
   private final Type outer;
   private final String name;
   private final int modifiers;
   private final int order;
   
   public ScopeType(Module module, Type outer, String name, int modifiers, int order){
      this.description = new TypeDescription(this);
      this.annotations = new ArrayList<Annotation>();
      this.constraints = new ArrayList<Constraint>();
      this.properties = new ArrayList<Property>();
      this.functions = new ArrayList<Function>();
      this.types = new ArrayList<Constraint>();
      this.progress = new LockProgress<Phase>();
      this.scope = new StaticScope(this);
      this.modifiers = modifiers;
      this.module = module;
      this.outer = outer;
      this.order = order;
      this.name = name;
   }
   
   @Override
   public Progress<Phase> getProgress() {
      return progress;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   @Override
   public List<Constraint> getGenerics() {
      return constraints;
   }
   
   @Override
   public List<Property> getProperties() {
      return properties;
   }
   
   @Override
   public List<Function> getFunctions(){
      return functions;
   }
   
   @Override
   public List<Constraint> getTypes(){
      return types;
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
      return null;
   }
   
   @Override
   public Type getOuter(){
      return outer;
   }
   
   @Override
   public Type getEntry(){
      return null;
   }
   
   @Override
   public int getModifiers() {
      return modifiers;
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