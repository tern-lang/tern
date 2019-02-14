package org.ternlang.core.property;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.ModifierType.CONSTANT;
import static org.ternlang.core.ModifierType.STATIC;
import static org.ternlang.core.Reserved.TYPE_CLASS;

import java.util.List;

import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.type.Type;

public class ClassProperty implements Property {
   
   private final Constraint constraint;
   private final Type source;
   
   public ClassProperty(Type source, Constraint constraint) {
      this.constraint = constraint;
      this.source = source;
   }
   
   @Override
   public List<Constraint> getGenerics() {
      return EMPTY_LIST;
   }
   
   @Override
   public List<Annotation> getAnnotations(){
      return EMPTY_LIST;
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
   public Type getSource() {
      return source;
   }
   
   @Override
   public String getAlias() {
      return TYPE_CLASS;
   }

   @Override
   public String getName() {
      return TYPE_CLASS;
   }

   @Override
   public int getModifiers() {
      return STATIC.mask | CONSTANT.mask;
   }

   @Override
   public Object getValue(Object object) {
      return source;
   }

   @Override
   public void setValue(Object object, Object value) {
      throw new InternalStateException("Illegal modification of constant " + TYPE_CLASS);
   }
   
   @Override
   public String toString(){
      return TYPE_CLASS;
   }

}