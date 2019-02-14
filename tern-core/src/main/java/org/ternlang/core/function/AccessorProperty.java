package org.ternlang.core.function;

import static java.util.Collections.EMPTY_LIST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.property.Property;
import org.ternlang.core.type.Type;

public class AccessorProperty<T> implements Property<T> {

   private final List<Annotation> annotations;
   private final Accessor<T> accessor;
   private final Constraint constraint;
   private final Type source;
   private final String alias;
   private final String name;
   private final int modifiers;
   
   public AccessorProperty(String name, String alias, Type source, Constraint constraint, Accessor<T> accessor, int modifiers){
      this.annotations = new ArrayList<Annotation>();
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.accessor = accessor;
      this.source = source;
      this.alias = alias;
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
      return alias;
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public int getModifiers() {
      return modifiers;
   }
   
   @Override
   public Object getValue(T source) {
      return accessor.getValue(source);
   }
   
   @Override
   public void setValue(T source, Object value) {
      accessor.setValue(source, value);;
   }
   
   @Override
   public String toString(){
      return name;
   }
}