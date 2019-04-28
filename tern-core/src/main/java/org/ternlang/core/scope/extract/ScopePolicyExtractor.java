package org.ternlang.core.scope.extract;

import java.util.Iterator;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.LocalScope;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.variable.Value;

public class ScopePolicyExtractor implements ScopeExtractor {
   
   private final ScopePolicy policy;

   public ScopePolicyExtractor(ScopePolicy policy) {
      this.policy = policy;
   }

   @Override
   public Scope extract(Scope scope) {
      Scope outer = scope.getParent();
      
      if(policy.isExtension()) {
         return extract(scope, outer); // can see callers scope
      }
      return extract(outer, outer); // can't see callers scope
   }

   public Scope extract(Scope original, Scope outer) {
      ScopeTable table = original.getTable();
      Iterator<Value> iterator = table.iterator();

      if(iterator.hasNext() || !policy.isCompiled()) {
         Scope capture = new LocalScope(original, outer);

         while(iterator.hasNext()) {
            Value local = iterator.next();
            String name = local.getName();
            Constraint constraint = local.getConstraint();

            if (!policy.isGlobals() || constraint.isStatic()) {
               ScopeState inner = capture.getState();
               Value value = inner.getValue(name);

               if (value == null) {
                  if (policy.isReference()) {
                     inner.addValue(name, local); // enable modification of local
                  } else {
                     Object object = local.getValue();
                     Value constant = Value.getConstant(object, constraint);

                     inner.addValue(name, constant); // local is a visible constant
                  }
               }
            }
         }
         return capture;
      }
      return original;
   }
}