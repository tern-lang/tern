package org.ternlang.compile;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.function.Origin.DEFAULT;

import java.util.Arrays;

import junit.framework.TestCase;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.ConstraintConverter;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.FunctionComparator;
import org.ternlang.core.convert.Score;
import org.ternlang.core.function.ClosureFunctionFinder;
import org.ternlang.core.function.EmptyFunction;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionSignature;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.ContextModule;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.type.TypeLoader;

public class ClosureMatcherTest extends TestCase {

   public void testClosureMatcher() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      Path path = new Path("/");
      Module module = new ContextModule(context, null, path, "yy", "", 1);
      ConstraintMatcher matcher = context.getMatcher();
      TypeLoader loader = context.getLoader();
      TypeExtractor extractor = context.getExtractor();
      FunctionComparator comparator = new FunctionComparator(matcher);
      ClosureFunctionFinder finder = new ClosureFunctionFinder(comparator, extractor, loader);
      Parameter parameter = new Parameter("n", Constraint.STRING, 0, false);
      Signature signature = new FunctionSignature(Arrays.asList(parameter), EMPTY_LIST, module, null, DEFAULT, false);
      Type type = new EmptyFunction(signature).getHandle();
      ConstraintConverter converter = matcher.match(type);
      Function function = new InvocationFunction(signature, null, type, null, "xx");
      Score score = converter.score(function);
      
      System.err.println(score);
   }
}
