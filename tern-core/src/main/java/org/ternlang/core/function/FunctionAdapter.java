package org.ternlang.core.function;

import java.util.List;

import org.ternlang.core.Any;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.type.Type;

public class FunctionAdapter implements Any {
   
   private final Function function;
   
   public FunctionAdapter(Function function) {
      this.function = (Function)function;
   }
   
   public int getModifiers() {
      return function.getModifiers();
   }
   
   public Type getSource() {
      return function.getSource();
   }
   
   public Type getHandle() {
      return function.getHandle();
   }
   
   public Constraint getConstraint() {
      return function.getConstraint();
   }
   
   public String getName() {
      return function.getName();
   }
   
   public Signature getSignature() {
      return function.getSignature();
   }
   
   public List<Annotation> getAnnotations() {
      return function.getAnnotations();
   }
   
   public Invocation getInvocation() {
      return function.getInvocation();
   }
   
   public String getDescription() {
      return function.getDescription();
   }
   
   @Override
   public String toString() {
      return function.toString();
   }
}
