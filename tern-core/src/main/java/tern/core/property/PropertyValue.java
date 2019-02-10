package tern.core.property;

import tern.core.constraint.Constraint;
import tern.core.variable.Value;

public class PropertyValue extends Value {

   private final Property property;
   private final Object object;   
   private final String name;

   public PropertyValue(Property property, Object object, String name) {
      this.property = property;
      this.object = object;
      this.name = name;
   }
   
   @Override
   public boolean isProperty() {
      return true;
   }
   
   @Override
   public Constraint getConstraint() {
      return property.getConstraint();
   }
   
   @Override
   public int getModifiers() {
      return property.getModifiers();
   }

   public String getName(){
      return name;
   }
   
   @Override
   public <T> T getValue() {
      return (T)property.getValue(object);
   }

   @Override
   public void setValue(Object value) {
      property.setValue(object, value);
   }
   
   @Override
   public String toString() {
      return String.valueOf(object);
   }
}