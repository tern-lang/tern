package org.ternlang.core.function.index;

import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.FunctionComparator;
import org.ternlang.core.convert.InterfaceCollector;
import org.ternlang.core.function.ClosureFunctionFinder;
import org.ternlang.core.function.Function;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.type.TypeLoader;

public class ClosureAdapter {

   private final FunctionComparator comparator;
   private final ClosureFunctionFinder finder;
   private final InterfaceCollector collector;
   private final TypeLoader loader;
   private final ThreadStack stack;

   public ClosureAdapter(ConstraintMatcher matcher, TypeExtractor extractor, TypeLoader loader, ThreadStack stack) {
      this.comparator = new FunctionComparator(matcher);
      this.finder = new ClosureFunctionFinder(comparator, extractor, loader);
      this.collector = new InterfaceCollector();
      this.loader = loader;
      this.stack = stack;
   }

   public FunctionPointer adapt(Object object) throws Exception {
      if (object != null) {
         Class original = object.getClass();
         Class[] interfaces = original.getInterfaces();

         for(int i = 0; i < interfaces.length; i++) {
            if (interfaces[i].isAnnotationPresent(FunctionalInterface.class)) {
               Type type = loader.loadType(original);
               Function function = finder.findFunctional(type);

               return new TracePointer(stack, function);
            }
         }
      }
      return null;
   }
}