package org.ternlang.core.convert.proxy;

import java.lang.reflect.Method;

import org.ternlang.common.Predicate;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.variable.Transient;
import org.ternlang.core.variable.Value;

public class ClosureMethodMatcher implements MethodMatcher {
   
   private ObjectMethodMatcher matcher;
   private FunctionResolver resolver;
   private Invocation invocation;
   private Predicate predicate;
   private Value value;
   
   public ClosureMethodMatcher(FunctionResolver resolver, Function function, Predicate predicate) {
      this.matcher = new ObjectMethodMatcher(function);
      this.value = new Transient(function);
      this.predicate = predicate;
      this.resolver = resolver;
   }

   @Override
   public Invocation match(Object proxy, Method method, Object[] arguments) throws Throwable {
      if(predicate.accept(method)) {
         if(invocation == null) {
            invocation = resolver.resolveValue(value, arguments); // here arguments can be null!!!
         }
         return invocation;
      }
      return matcher.match(proxy, method, arguments);
   }
}
