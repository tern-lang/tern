package org.ternlang.core.convert.proxy;

import java.lang.reflect.Method;

import org.ternlang.common.Predicate;
import org.ternlang.core.Context;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.type.TypeLoader;

public class ClosurePredicateBuilder {
   
   private final ClosureMethodResolver resolver;
   
   public ClosurePredicateBuilder(Context context) {
      this.resolver = new ClosureMethodResolver(context);
   }
   
   public Predicate create(Function function, Class type) {
      if(type != null) {
         try {
            Method method = resolver.resolve(function, type);
            
            if(method != null) {
               Class[] types = method.getParameterTypes();
               String name = method.getName();
               
               return new MethodPredicate(name, types);
            }
         } catch(Exception e) {
            throw new InternalStateException("Could not match '" + function + "' with " + type, e);
         }
      }
      return new MethodPredicate(null);
   }
   
   private static class ClosureMethodResolver {
      
      private ClosureMethodFinder finder;
      private Context context;
      
      public ClosureMethodResolver(Context context) {
         this.context = context;
      }
      
      public Method resolve(Function function, Class type) throws Exception {
         if(finder == null) {
            ConstraintMatcher matcher = context.getMatcher();
            TypeExtractor extractor = context.getExtractor();
            TypeLoader loader = context.getLoader();
            
            finder = new ClosureMethodFinder(matcher, extractor, loader);
         }
         return finder.findClosure(function, type);
      }
   }
}
