package org.ternlang.core.function;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.ModifierType.ABSTRACT;
import static org.ternlang.core.ModifierType.FUNCTION;
import static org.ternlang.core.Reserved.METHOD_CLOSURE;
import static org.ternlang.core.constraint.Constraint.NONE;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.type.Type;

public class EmptyFunction implements Function {

   private final List<Annotation> annotations;
   private final FunctionAdapter adapter;
   private final Signature signature;
   private final String name;
   private final int modifiers;

   public EmptyFunction(Signature signature){
      this(signature, METHOD_CLOSURE);
   }
   
   public EmptyFunction(Signature signature, String name){
      this(signature, name, ABSTRACT.mask);
   }

   public EmptyFunction(Signature signature, String name, int modifiers){
      this.annotations = new ArrayList<Annotation>();
      this.adapter = new FunctionAdapter(this);
      this.signature = signature;
      this.modifiers = modifiers;
      this.name = name;
   }
   
   @Override
   public int getModifiers(){
      return modifiers & FUNCTION.mask;
   }
   
   @Override
   public Type getHandle() {
      return null;
   }
   
   @Override
   public Constraint getConstraint() {
      return NONE;
   }
   
   @Override
   public Type getSource() {
      return null;
   }
   
   @Override
   public Object getProxy() {
      return null;
   }
   
   @Override
   public Object getProxy(Class require) {
      return null;
   }
   
   @Override
   public Object getAdapter() {
      return adapter;
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
      return EMPTY_LIST;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   @Override
   public Invocation getInvocation(){
      return null;
   }
   
   @Override
   public String getDescription() {
      return name + signature;
   }
   
   @Override
   public String toString(){
      return name + signature;
   }
}