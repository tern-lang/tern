package tern.compile.define;

import java.util.ArrayList;
import java.util.List;

import tern.common.CompleteProgress;
import tern.common.Progress;
import tern.core.ModifierType;
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

public class MockType implements Type {

   private final TypeDescription description;
   private final List<Annotation> annotations;
   private final List<Constraint> constraints;
   private final List<Property> properties;
   private final List<Function> functions;
   private final List<Constraint> types;
   private final Progress<Phase> progress;
   private final Module module;
   private final Scope scope;
   private final Class type;
   private final Type entry;
   private final String name;

   public MockType(Module module, String name, Type entry, Class type){
      this.progress = new CompleteProgress<Phase>();
      this.description = new TypeDescription(this);
      this.annotations = new ArrayList<Annotation>();
      this.constraints = new ArrayList<Constraint>();
      this.properties = new ArrayList<Property>();
      this.functions = new ArrayList<Function>();
      this.types = new ArrayList<Constraint>();
      this.scope = new StaticScope(this);
      this.module = module;
      this.entry = entry;
      this.type = type;
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
   public Class getType() {
      return type;
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
   public String getName(){
      return name;
   }   
   
   @Override
   public int getModifiers(){
      return ModifierType.CLASS.mask;
   }
   
   @Override
   public int getOrder(){
      return 0;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}
