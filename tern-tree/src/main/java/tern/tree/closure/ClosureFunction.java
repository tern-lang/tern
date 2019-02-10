package tern.tree.closure;

import java.util.List;

import tern.core.annotation.Annotation;
import tern.core.constraint.Constraint;
import tern.core.convert.proxy.FunctionProxy;
import tern.core.function.Function;
import tern.core.function.Invocation;
import tern.core.function.Signature;
import tern.core.type.Type;

public class ClosureFunction implements Function {
   
   private final Invocation invocation;
   private final FunctionProxy proxy;
   private final Function template;
   
   public ClosureFunction(Function template, Invocation invocation) {
      this.proxy = new FunctionProxy(this);
      this.invocation = invocation;
      this.template = template;
   }

   @Override
   public int getModifiers() {
      return template.getModifiers();
   }

   @Override
   public String getName() {
      return template.getName();
   }
   
   @Override
   public Type getSource() {
      return template.getSource();
   }
   
   @Override
   public Type getHandle() {
      return template.getHandle();
   }

   @Override
   public Signature getSignature() {
      return template.getSignature();
   }

   @Override
   public Constraint getConstraint() {
      return template.getConstraint();
   }
   
   @Override
   public List<Constraint> getGenerics() {
      return template.getGenerics();
   }

   @Override
   public List<Annotation> getAnnotations() {
      return template.getAnnotations();
   }

   @Override
   public Invocation getInvocation() {
      return invocation;
   }

   @Override
   public String getDescription() {
      return template.getDescription();
   }

   @Override
   public Object getProxy(Class type) {
      return proxy.getProxy(type);
   }

   @Override
   public Object getProxy() {
      return proxy.getProxy();
   }
   
   @Override
   public String toString(){
      return String.valueOf(template);
   }

}
