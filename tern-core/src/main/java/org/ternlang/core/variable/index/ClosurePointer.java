package org.ternlang.core.variable.index;

import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.variable.Value;
import org.ternlang.core.variable.bind.VariableFinder;
import org.ternlang.core.variable.bind.VariableResult;

public class ClosurePointer implements VariablePointer<Function> {

   private final AtomicReference<VariableResult> reference;
   private final VariableFinder finder;
   private final String name;
   
   public ClosurePointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<VariableResult>();
      this.finder = finder;
      this.name = name;
   }
   
   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(Function.class);
         VariableResult match = finder.findAll(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint(left);
         }
         return null;
      }
      return result.getConstraint(left);
   }
   
   @Override
   public Value getValue(Scope scope, Function left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(Function.class);
         VariableResult match = finder.findAll(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getValue(left);
         }
         return null;
      }
      return result.getValue(left);
   }
}
