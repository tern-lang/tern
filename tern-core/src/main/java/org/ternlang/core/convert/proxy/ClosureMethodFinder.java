package org.ternlang.core.convert.proxy;

import java.lang.reflect.Method;

import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.FunctionComparator;
import org.ternlang.core.convert.Score;
import org.ternlang.core.function.ClosureFunctionFinder;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.type.TypeLoader;

public class ClosureMethodFinder {

   private final FunctionComparator comparator;
   private final ClosureFunctionFinder finder;
   
   public ClosureMethodFinder(ConstraintMatcher matcher, TypeExtractor extractor, TypeLoader loader) {
      this.comparator = new FunctionComparator(matcher);
      this.finder = new ClosureFunctionFinder(comparator, extractor, loader);
   }
   
   public Method findClosure(Function function, Class type) throws Exception {
      Function possible = finder.findFunctional(type);
      
      if(possible != null) {
         Signature signature = possible.getSignature();
         Type definition = signature.getDefinition();
         Scope scope = definition.getScope();
         Score score = comparator.compare(scope, function, possible);
         
         if(!score.isInvalid()) {
            return (Method)signature.getSource();
         }
      }
      return null;
   }
}