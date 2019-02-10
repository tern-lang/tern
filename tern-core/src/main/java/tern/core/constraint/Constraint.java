package tern.core.constraint;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;
import java.util.Map;
import java.util.Set;

import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

public abstract class Constraint {

   public static final Constraint NONE = new ClassConstraint(null);
   public static final Constraint NUMBER = new ClassConstraint(Number.class);
   public static final Constraint INTEGER = new ClassConstraint(Integer.class);
   public static final Constraint LONG = new ClassConstraint(Long.class);
   public static final Constraint FLOAT = new ClassConstraint(Float.class);
   public static final Constraint DOUBLE = new ClassConstraint(Double.class);
   public static final Constraint SHORT = new ClassConstraint(Short.class);
   public static final Constraint BYTE = new ClassConstraint(Byte.class);
   public static final Constraint STRING = new ClassConstraint(String.class);
   public static final Constraint BOOLEAN = new ClassConstraint(Boolean.class);
   public static final Constraint CHARACTER = new ClassConstraint(Character.class);
   public static final Constraint SET = new ClassConstraint(Set.class);
   public static final Constraint LIST = new ClassConstraint(List.class);
   public static final Constraint MAP = new ClassConstraint(Map.class);
   public static final Constraint ITERABLE = new ClassConstraint(Iterable.class);  
   public static final Constraint TYPE = new ClassConstraint(Type.class);
   public static final Constraint OBJECT = new ClassConstraint(Object.class);
   
   public static Constraint getConstraint(Module module) {
      return new ModuleConstraint(module);
   }

   public static Constraint getConstraint(Type type) {
      return new StaticConstraint(type);
   }
   
   public static Constraint getConstraint(Type type, int modifiers) {
      return new StaticConstraint(type, modifiers);
   }
   
   public static Constraint getConstraint(Class value) {
      return new ClassConstraint(value);
   }
   
   public static Constraint getConstraint(Class value, int modifiers) {
      return new ClassConstraint(value, modifiers);
   }

   public static Constraint getConstraint(Object value) {
      return new ObjectConstraint(value);
   }
   
   public static Constraint getConstraint(Value value) {
      return new ValueConstraint(value);
   }
   
   public boolean isVariable() {
      return true;
   }

   public boolean isPrivate() {
      return false;
   }
   
   public boolean isClass() {
      return false;
   }
   
   public boolean isModule() {
      return false;
   }   
   
   public boolean isConstant() {
      return false;
   }   
   
   public boolean isStatic() {
      return false;
   }   
   
   public List<Constraint> getGenerics(Scope scope) {
      return EMPTY_LIST;
   }
            
   public List<String> getImports(Scope  scope) {
      return EMPTY_LIST;
   }
   
   public String getName(Scope  scope) {
      return null;
   }
   
   public abstract Type getType(Scope scope);
}