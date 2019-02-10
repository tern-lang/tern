package tern.core.function;

import static tern.core.ModifierType.FUNCTION;

import java.util.ArrayList;
import java.util.List;

import tern.core.annotation.Annotation;
import tern.core.constraint.Constraint;
import tern.core.convert.proxy.FunctionProxy;
import tern.core.type.Type;

public class InvocationFunction implements Function {

   private final FunctionDescription description;
   private final List<Annotation> annotations;
   private final Invocation invocation;
   private final Constraint constraint;
   private final FunctionProxy proxy;
   private final Signature signature;
   private final Type source;
   private final String name;
   private final int modifiers;

   public InvocationFunction(Signature signature, Invocation invocation, Type source, Constraint constraint, String name){
      this(signature, invocation, source, constraint, name, 0);
   }
   
   public InvocationFunction(Signature signature, Invocation invocation, Type source, Constraint constraint, String name, int modifiers){
      this(signature, invocation, source, constraint, name, modifiers, 0);
   }
   
   public InvocationFunction(Signature signature, Invocation invocation, Type source, Constraint constraint, String name, int modifiers, int start){
      this.description = new FunctionDescription(signature, source, name, start);
      this.annotations = new ArrayList<Annotation>();
      this.proxy = new FunctionProxy(this);
      this.constraint = constraint;
      this.invocation = invocation;
      this.signature = signature;
      this.modifiers = modifiers;
      this.source = source;
      this.name = name;
   }
   
   @Override
   public int getModifiers(){
      return modifiers | FUNCTION.mask;
   }
   
   @Override
   public Type getSource() {
      return source;
   }
   
   @Override
   public Type getHandle() {
      return signature.getDefinition();
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
   public Constraint getConstraint() {
      return constraint;
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public Signature getSignature(){
      return signature;
   }
   
   @Override
   public List<Constraint> getGenerics() {
      return signature.getGenerics();
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   @Override
   public Invocation getInvocation(){
      return invocation;
   }
   
   @Override
   public String getDescription() {
      return description.getDescription();
   }
   
   @Override
   public String toString(){
      return description.toString();
   }
}