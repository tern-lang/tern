package tern.core.function.index;

import java.util.List;

import tern.core.Any;
import tern.core.annotation.Annotation;
import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.function.Invocation;
import tern.core.function.Signature;
import tern.core.type.Type;

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
