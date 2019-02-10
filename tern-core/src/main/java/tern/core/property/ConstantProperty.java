package tern.core.property;

import static java.util.Collections.EMPTY_LIST;
import static tern.core.ModifierType.CONSTANT;
import static tern.core.Reserved.TYPE_CLASS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tern.core.annotation.Annotation;
import tern.core.constraint.Constraint;
import tern.core.type.Type;
import tern.core.variable.Constant;

public class ConstantProperty implements Property<Object> {

   private final List<Annotation> annotations;
   private final Constraint constraint;
   private final Constant constant;
   private final Type source;
   private final String name;
   private final int modifiers;
   
   public ConstantProperty(String name, Type source, Constraint constraint, Object value, int modifiers){
      this.annotations = new ArrayList<Annotation>();
      this.constant = new Constant(value);
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.source = source;
      this.name = name;
   }
   
   @Override
   public List<Constraint> getGenerics() {
      return EMPTY_LIST;
   }
   
   @Override
   public List<Annotation> getAnnotations(){
      return annotations;
   }
   
   @Override
   public Constraint getConstraint() {
      return constraint;
   }

   @Override
   public Type getHandle() {
      return null;
   }
   
   @Override
   public Type getSource(){
      return source;
   }
   
   @Override
   public String getAlias() {
      return name;
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public int getModifiers() {
      return modifiers | CONSTANT.mask;
   }
   
   @Override
   public Object getValue(Object source) {
      return constant.getValue();
   }

   @Override
   public void setValue(Object source, Object value) {
      constant.setValue(value);
   }
   
   @Override
   public String toString(){
      return name;
   }
}