package org.ternlang.core.function.index;

import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.Score;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.function.ArgumentConverter;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.LocalScopeFinder;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class LocalIndexer {
   
   private final LocalFunctionIndexer indexer;
   private final LocalScopeFinder finder;
   private final ThreadStack stack;
   
   public LocalIndexer(ProxyWrapper wrapper, ThreadStack stack, FunctionIndexer indexer) {
      this.indexer = new LocalFunctionIndexer(wrapper, indexer);
      this.finder = new LocalScopeFinder();
      this.stack = stack;
   }
   
   public FunctionPointer index(Scope scope, String name, Type... types) throws Exception { // match function variable    
      Value value = finder.findFunction(scope, name);
      
      if(value != null) {
         Constraint constraint = value.getConstraint();
         Type type = constraint.getType(scope);
         
         if(type != null) {
            List<Function> functions = type.getFunctions();
            int modifiers = type.getModifiers();
            
            if(ModifierType.isFunction(modifiers) && !functions.isEmpty()) {
               Function function = functions.get(0);
               Signature signature = function.getSignature();
               ArgumentConverter match = signature.getConverter();
               Score score = match.score(types);
               
               if(score.isValid()) {
                  return new TracePointer(function, stack);
               }
            }
            return indexer.index(scope, type, types);
         }
      }
      return indexer.index(scope, name, types);
   }
   
   public FunctionPointer index(Scope scope, String name, Object... values) throws Exception { // match function variable
      Value value = finder.findFunction(scope, name);
      
      if(value != null) {
         Object object = value.getValue();

         if(object != null) {
            Constraint constraint = value.getConstraint();
            Type type = constraint.getType(scope);
            
            if(Function.class.isInstance(object)) {
               Function function = (Function)object;
               Signature signature = function.getSignature();
               ArgumentConverter match = signature.getConverter();
               Score score = match.score(values);
               
               if(score.isValid()) {
                  return new TracePointer(function, stack);
               }
            }
            return indexer.index(scope, type, values);
         }
      }
      return indexer.index(scope, name, values);
   }
}