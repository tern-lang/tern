package org.ternlang.core.function.index;

import java.util.List;

import org.ternlang.core.Any;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.Signature;
import org.ternlang.core.type.Type;

public class FunctionAdapter implements Any {
   
   public static FunctionAdapter wrap(Object object) {
      return new FunctionAdapter(object);
   }
   
   private final Function function;
   
   public FunctionAdapter(Object function) {
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
