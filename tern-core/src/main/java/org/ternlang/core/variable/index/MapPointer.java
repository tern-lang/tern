package org.ternlang.core.variable.index;

import static org.ternlang.core.constraint.Constraint.NONE;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.core.variable.bind.VariableFinder;
import org.ternlang.core.variable.bind.VariableResult;

public class MapPointer implements VariablePointer<Map> {
   
   private final AtomicReference<VariableResult> reference;
   private final VariableFinder finder;
   private final String name;
   
   public MapPointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<VariableResult>();
      this.finder = finder;
      this.name = name;
   }
   
   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         Type type = left.getType(scope);
         VariableResult match = finder.findProperty(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint(left);
         }
         return NONE;
      }
      return result.getConstraint(left);
   }
   
   @Override
   public Value getValue(Scope scope, Map left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         VariableResult match = finder.findProperty(scope, left, name);
         
         if(match != null) {
            reference.set(match);
            return match.getValue(left);
         }
         return null;
      }
      return result.getValue(left);
   }
}